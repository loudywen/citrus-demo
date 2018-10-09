package com.devon.demo.citrusdemo;

import org.springframework.core.io.ResourceLoader;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.runner.AbstractTestBehavior;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.jms.endpoint.JmsEndpoint;
import com.ibm.mq.jms.MQConnectionFactory;

public class ReceiveMsgFromQueueBehavior extends AbstractTestBehavior {

    private TestRunner  runner;

    private JmsEndpoint exitQueueEndpoint;

    private TestContext context;

    public ReceiveMsgFromQueueBehavior(TestRunner runner, JmsEndpoint exitQueueEndpoint, TestContext context) {
        this.runner = runner;
        this.exitQueueEndpoint = exitQueueEndpoint;
        this.context = context;
    }

    @Override
    public void apply() {

        String queueName = this.exitQueueEndpoint.getEndpointConfiguration().getDestinationName();
        MQConnectionFactory cf = (MQConnectionFactory) this.exitQueueEndpoint.getEndpointConfiguration()
                .getConnectionFactory();
        String hostName = cf.getHostName();
        int port = cf.getPort();

        this.runner.echo(String.format("walmart:mqBrokerConnection(%s,%s,%s)", queueName, port, hostName));

        this.runner.receive(smb -> {
            smb.endpoint(this.exitQueueEndpoint).name("result").payload(
                    "citrus:readFile('file:C:\\Toolbox\\sai_critus\\Test_Interfaces_details-Sample Inputs\\Test_Interfaces_details-Sample Inputs\\SH_TEST_04_output.xml')")
                    .timeout(10000);

        });

        this.runner.echo("citrus:message(result)");
        // this.testContext.setVariable("whatever", "test");
        this.runner.echo("received message from exit queue");
        this.runner.purgeEndpoints(action -> {
            action.endpoint(this.exitQueueEndpoint);
        });

    }

}