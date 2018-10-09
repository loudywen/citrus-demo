package com.devon.demo.citrusdemo;

import org.springframework.core.io.ResourceLoader;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.runner.AbstractTestBehavior;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.jms.endpoint.JmsEndpoint;
import com.ibm.mq.jms.MQConnectionFactory;

public class SendMsgToQueueBehavior extends AbstractTestBehavior {

    private TestRunner  runner;

    private JmsEndpoint entryQueueEndpoint;

    private TestContext testContext;

    public SendMsgToQueueBehavior(TestRunner runner, JmsEndpoint entryQueueEndpoint, TestContext testContext) {
        this.runner = runner;
        this.entryQueueEndpoint = entryQueueEndpoint;
        this.testContext = testContext;
    }

    @Override
    public void apply() {

        String queueName = this.entryQueueEndpoint.getEndpointConfiguration().getDestinationName();
        MQConnectionFactory cf = (MQConnectionFactory) this.entryQueueEndpoint.getEndpointConfiguration()
                .getConnectionFactory();
        String hostName = cf.getHostName();
        int port = cf.getPort();

        this.runner.echo(String.format("Sending msg to (%s,%s,%s)", queueName, port, hostName));

   // @formatter:off
        
        this.runner.send(smb ->{
            smb.endpoint(this.entryQueueEndpoint)
            .header("SCENARIONAME",this.testContext.getVariable("SCENARIONAME"))
            .header("COUNTRYCODE",this.testContext.getVariable("COUNTRYCODE"))
            .header("INSTANCEID",this.testContext.getVariable("INSTANCEID"))
            .payload("citrus:readFile('file:C:\\Toolbox\\sai_critus\\Test_Interfaces_details-Sample Inputs\\Test_Interfaces_details-Sample Inputs\\SH_TEST_04.xml')");
        });
        
   // @formatter:on

        // this.testContext.setVariable("whatever", "test");
        this.runner.echo("Sent message to entry queue");
        
        
    }

}