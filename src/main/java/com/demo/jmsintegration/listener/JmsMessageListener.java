package com.demo.jmsintegration.listener;

import com.demo.jmsintegration.models.MyMessage;
import com.ibm.jms.JMSObjectMessage;
import com.ibm.msg.client.jms.JmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
@Slf4j
public class JmsMessageListener {

    @JmsListener(destination = "DEV.QUEUE.1", containerFactory = "jmsListenerContainerFactory")
    public void listen(JMSObjectMessage message) {
        try {
            log.info("Jms messageID: {} messageData: {} jmsObjectMessage: {}",
                    message.getJMSMessageID(), message.getObject(), message);
        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }
}
