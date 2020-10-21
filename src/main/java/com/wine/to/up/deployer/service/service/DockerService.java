package com.wine.to.up.deployer.service.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.*;
import com.wine.to.up.deployer.service.request.ServiceRequest;
import com.wine.to.up.deployer.service.vo.ServiceVO;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@SuppressWarnings("All")
public class DockerService {

    private final DockerClient dockerClient;

    public List<ServiceVO> getServices() {
        List<Service> serviceList = dockerClient.listServicesCmd().exec();
        List<ServiceVO> serviceVOList = new ArrayList<>();

        for (Service service : serviceList) {

            String serviceName = service.getSpec().getTaskTemplate().getContainerSpec().getImage();

            ServiceVO serviceVO = new ServiceVO(
                    service.getSpec().getName(),
                    service.getSpec().getMode().getReplicated().getReplicas(),
                    serviceName/*.substring(serviceName.indexOf(":") + 1, serviceName.indexOf("@"))*/,
                    serviceName/*.substring(0, serviceName.indexOf(":"))*/);

            List<Task> taskList = dockerClient.listTasksCmd().withNameFilter(service.getSpec().getName()).exec();
            int allReplicas = taskList.size(),
                    runningReplicas = 0;
            for (Task task : taskList) {
                if (task.getStatus().getState().getValue().equals("running")) {
                    runningReplicas++;
                }
            }

            if (runningReplicas == 0) {
                serviceVO.setStatus("shutdown");
            } else {
                serviceVO.setStatus("running");
            }
            serviceVO.setCurrentReplicas(Long.valueOf(runningReplicas));
            serviceVOList.add(serviceVO);
        }
        return serviceVOList;
    }

    public String createService(ServiceRequest serviceRequest) {
        return dockerClient.createServiceCmd(new ServiceSpec()
                .withName(serviceRequest.getName())
                .withTaskTemplate(new TaskSpec()
                        .withContainerSpec(new ContainerSpec()
                                .withImage(serviceRequest.getImageName())))
                .withMode(new ServiceModeConfig()
                        .withReplicated(new ServiceReplicatedModeOptions()
                                .withReplicas(serviceRequest.getReplicas()))))
                .exec().getId();
    }
}