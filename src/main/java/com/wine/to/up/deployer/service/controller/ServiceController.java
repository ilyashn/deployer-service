package com.wine.to.up.deployer.service.controller;

import com.wine.to.up.deployer.service.service.DockerService;
import com.wine.to.up.deployer.service.request.ServiceRequest;
import com.wine.to.up.deployer.service.vo.ServiceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ServiceController {
    private final DockerService dockerService;

    @GetMapping("/services")
    public List<ServiceVO> getServices() {
        return dockerService.getServices();
    }

    @PostMapping("/create/service")
    public String createService(@RequestBody ServiceRequest serviceRequest) {
        return dockerService.createService(serviceRequest);
    }
}