package com.demo.jmsintegration.config;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;

@Configuration
public class JmsConfig {

    @Autowired
    private JmsConfigProps jmsConfigProps;

    @Bean
    @DependsOn(value = {"mQConnectionFactory"})
    public JmsTemplate jmsTemplate(MQConnectionFactory mqConnectionFactory) {
        return new JmsTemplate(mqConnectionFactory);
    }

    @Bean(name = "mQConnectionFactory")
    public MQConnectionFactory connectionFactory() {
        MQConnectionFactory conn = new MQConnectionFactory();

        try {
            conn.setHostName(jmsConfigProps.getHostName());
            conn.setPort(jmsConfigProps.getPort());
            conn.setQueueManager(jmsConfigProps.getQueueManager());
            conn.setChannel(jmsConfigProps.getChannel());
            conn.setTransportType(WMQConstants.WMQ_CM_CLIENT);

            // Auth properties
            conn.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, false);
            conn.setStringProperty(WMQConstants.USERID, jmsConfigProps.getUser());
            conn.setStringProperty(WMQConstants.PASSWORD, jmsConfigProps.getPassword());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    @Bean(name = "userCredentialsConnectionFactoryAdapter")
    @DependsOn(value = {"mQConnectionFactory"})
    public UserCredentialsConnectionFactoryAdapter getUserCredentialsConnectionFactoryAdapter(
            MQConnectionFactory connectionFactory) {
        UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername(jmsConfigProps.getUser());
        adapter.setPassword(jmsConfigProps.getPassword());
        adapter.setTargetConnectionFactory(connectionFactory);
        return adapter;
    }

    @Bean(name = "jmsListenerContainerFactory")
    @DependsOn(value = {"mQConnectionFactory"})
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(MQConnectionFactory mqConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(mqConnectionFactory);
        factory.setSessionTransacted(true);
        factory.setConcurrency("5");
        return factory;
    }
}
