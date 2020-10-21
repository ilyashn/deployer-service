package com.wine.to.up.deployer.service.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceVO {
    private String serviceName;
    private String imageName;
    private String status;
    private Long maximumReplicas;
    private String version;
    private Long currentReplicas;

    public ServiceVO(String serviceName, Long maximumReplicas, String imageName, String version) {
        this.serviceName = serviceName;
        this.imageName = imageName;
        this.maximumReplicas = maximumReplicas;
        this.version = version;
    }
}
