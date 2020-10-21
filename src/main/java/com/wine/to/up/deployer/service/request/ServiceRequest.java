package com.wine.to.up.deployer.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceRequest {
    private String imageName;
    private String name;
    private Integer replicas;
}
