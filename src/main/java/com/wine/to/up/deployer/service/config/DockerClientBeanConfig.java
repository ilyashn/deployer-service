package com.wine.to.up.deployer.service.config;

import com.github.dockerjava.api.DockerClient;

import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientBeanConfig {

    @Bean
    public DockerClient getDockerClient() {
        return  DockerClientBuilder.getInstance().build();
    }
}