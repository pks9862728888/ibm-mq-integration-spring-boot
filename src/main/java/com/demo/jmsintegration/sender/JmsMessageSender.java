package com.demo.jmsintegration.sender;

import com.demo.jmsintegration.config.JmsConfigProps;
import com.demo.jmsintegration.models.MyMessage;
import com.ibm.jms.JMSObjectMessage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsMessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsConfigProps jmsConfigProps;

    public void sendObjectMessage(@NonNull MyMessage message) throws JmsException {
        jmsTemplate.send(jmsConfigProps.getSendQueue(), mc -> mc.createObjectMessage(message));
    }
}
