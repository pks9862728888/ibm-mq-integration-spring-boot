package com.demo.jmsintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class IbmMqIntegrationDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(IbmMqIntegrationDemoApp.class, args);
    }

}
