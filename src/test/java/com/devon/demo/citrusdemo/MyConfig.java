package com.devon.demo.citrusdemo;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.functions.FunctionLibrary;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.jms.endpoint.JmsEndpoint;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
@PropertySource(value = { "classpath:my-config.properties" })
public class MyConfig {

    // Entry
    @Value("${jms.entry.broker.url}")
    String hostNameEntry;

    @Value("${jms.entry.broker.port}")
    String portEntry;

    @Value("${jms.entry.broker.qm}")
    String queueManagerEntry;

    @Value("${jms.entry.broker.channel}")
    String channelEntry;

    @Value("${jms.entry.broker.entryqueue}")
    String entryqueue;

    // Exit

    @Value("${jms.exit.broker.url}")
    String hostNameExit;

    @Value("${jms.exit.broker.port}")
    String portExit;

    @Value("${jms.exit.broker.qm}")
    String queueManagerExit;

    @Value("${jms.exit.broker.channel}")
    String channelExit;

    @Value("${jms.exit.broker.exitqueue}")
    String exitqueue;

    @Bean
    public ConnectionFactory entryConnectionFactory() throws JMSException {
        MQConnectionFactory factory = new MQConnectionFactory();

        factory.setHostName(hostNameEntry);
        factory.setPort(Integer.parseInt(portEntry));
        factory.setQueueManager(queueManagerEntry);
        factory.setChannel(channelEntry);
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        // this is important
        // factory.setSSLCipherSuite("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384");
        return factory;
    }

    @Bean
    public ConnectionFactory exitConnectionFactory() throws JMSException {
        MQConnectionFactory factory = new MQConnectionFactory();

        factory.setHostName(hostNameExit);
        factory.setPort(Integer.parseInt(portExit));
        factory.setQueueManager(queueManagerExit);
        factory.setChannel(channelExit);
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        // this is important
        // factory.setSSLCipherSuite("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384");
        return factory;
    }

    @Bean
    public JmsEndpoint entryQueueEndpoint() throws JMSException {

        return CitrusEndpoints.jms().asynchronous().connectionFactory(entryConnectionFactory()).destination(entryqueue)
                .build();
    }

    @Bean
    public JmsEndpoint exitQueueEndpoint() throws JMSException {

        return CitrusEndpoints.jms().asynchronous().connectionFactory(exitConnectionFactory()).destination(exitqueue)
                .build();
    }

    @Bean
    public FunctionLibrary functionLib() {
        // After that you should be able to call the function with diwen:myTestFunction().

        FunctionLibrary functionLibrary = new FunctionLibrary();

        functionLibrary.setPrefix("diwen:");
        functionLibrary.setName("myTestFunctionLibrary");

        functionLibrary.getMembers().put("myTestFunction", new MyTestFunction());
        return functionLibrary;

    }

}
