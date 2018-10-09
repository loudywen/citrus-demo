package com.devon.demo.citrusdemo;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.junit.JUnit4CitrusTest;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.jms.endpoint.JmsEndpoint;

@ContextConfiguration(classes = { MyConfig.class })
public class CitrusTestDemo extends JUnit4CitrusTest {
    static {
        // for mq ssl channel
        // Security.setProperty("crypto.policy", "unlimited");

        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        // System.setProperty("javax.net.debug", "ssl");
        // System.setProperty("javax.net.ssl.trustStoreType", "jks");
        // // Please change your path
        // System.setProperty("javax.net.ssl.trustStore", "C:\\Toolbox\\intermediateStore.jks");
        // System.setProperty("javax.net.ssl.keyStore", "C:\\Toolbox\\walmart_cert\\sample.jks");
        // System.setProperty("javax.net.ssl.trustStorePassword .", "test");
        // System.setProperty("javax.net.ssl.trustStore", "C:\\Toolbox\\keystore.jks");

        // MAASConfig.setCondition(true);
    }

    @Autowired
    @Qualifier("entryQueueEndpoint")
    private JmsEndpoint entryQueueEndpoint;

    @Autowired
    @Qualifier("exitQueueEndpoint")
    private JmsEndpoint exitQueueEndpoint;

    @Value("${test.1.scenarioName}")
    private String      test_1_ScenarioName;

    @Value("${test.1.countryCode}")
    private String      test_1_CountryCode;

    @Value("${test.1.instanceId}")
    private String      test_1_InstanceId;

    @Value("${test.1.sendPayload}")
    private String      test_1_SendPayload;

    @Value("${test.1.expectedPayload}")
    private String      test_1_ExpectedReceive;

    @Test
    @CitrusTest(name = "Validate SH_TEST4 output")
    public void myFirstTest(@CitrusResource TestRunner runner, @CitrusResource TestContext context) {

        runner.description("My first citrus mq test");
        runner.author("Devon");

        Map<String, Object> variables = new HashMap<>();
        variables.put("SCENARIONAME", test_1_ScenarioName);
        variables.put("COUNTRYCODE", test_1_CountryCode);
        variables.put("INSTANCEID", test_1_InstanceId);
        variables.put("fileToSend", test_1_SendPayload);
        variables.put("expectedReceive", test_1_ExpectedReceive);

        context.setVariables(variables);
        
        runner.echo("diwen:myTestFunction()");

        runner.applyBehavior(new SendMsgToQueueBehavior(runner, this.entryQueueEndpoint, context));

        runner.applyBehavior(new ReceiveMsgFromQueueBehavior(runner, this.exitQueueEndpoint, context));

    }

}