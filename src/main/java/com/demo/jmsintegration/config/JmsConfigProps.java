package com.demo.jmsintegration.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ibm.mq")
@Getter
@Setter
public class JmsConfigProps {

    private String hostName;
    private Integer port;
    private String queueManager;
    private String channel;
    private String connName;
    private String user;
    private String password;
    private String sendQueue;
    private String receiveQueue;

}
