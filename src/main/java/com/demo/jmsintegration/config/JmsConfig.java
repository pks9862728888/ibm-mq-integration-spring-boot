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
    public JmsTemplate jmsTemplate() throws JMSException {
        return new JmsTemplate(getUserCredentialsConnectionFactoryAdapter());
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
    public UserCredentialsConnectionFactoryAdapter getUserCredentialsConnectionFactoryAdapter() {
        UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername(jmsConfigProps.getUser());
        adapter.setPassword(jmsConfigProps.getPassword());
        adapter.setTargetConnectionFactory(connectionFactory());
        return adapter;
    }

    @Bean(name = "jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setSessionTransacted(true);
        factory.setConcurrency("5");
        return factory;
    }
}
