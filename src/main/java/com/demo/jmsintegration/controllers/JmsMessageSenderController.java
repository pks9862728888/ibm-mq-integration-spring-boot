package com.demo.jmsintegration.controllers;

import com.demo.jmsintegration.models.MyMessage;
import com.demo.jmsintegration.sender.JmsMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/v1")
@Slf4j
public class JmsMessageSenderController {

    @Autowired
    private JmsMessageSender jmsMessageSender;

    @PostMapping("/send-string-message")
    public ResponseEntity<String> sendMessageObject(@RequestBody MyMessage message) {
        try {
            message.setTimestamp(ZonedDateTime.now());
            jmsMessageSender.sendObjectMessage(message);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (JmsException e) {
            log.error("Exception occurred while publishing string message: {} Exception: {}",
                    message, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
