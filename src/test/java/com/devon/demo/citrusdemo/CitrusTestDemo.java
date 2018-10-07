package com.devon.demo.citrusdemo;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.junit.JUnit4CitrusTest;
import com.consol.citrus.dsl.runner.TestRunner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CitrusTestDemo extends JUnit4CitrusTest {


    private static final Logger log = LoggerFactory.getLogger(CitrusTestDemo.class);

    static {
        System.setProperty("citrus.spring.java.config", MyConfig.class.getName());

    }

    @Test
    @CitrusTest(name = "MyFirstTest")
    public void myFirstTest(@CitrusResource TestRunner runner, @CitrusResource TestContext context) {

        runner.description("say some thing");
        runner.author("Devon");
        context.setVariable("myVariable", "some value");
        runner.echo("Before loggingService call");


        log.info(" I am here ");
        runner.applyBehavior(new FooBehavior());

//        designer.send("helloServiceEndpoint").name("forsave")
//                .payload("<TestMessage>" +
//                        "<Text>Hello!</Text>" +
//                        "</TestMessage>")
//                .header("Operation", "sayHello");

        System.out.println("---------------------------- " + context.getVariable("myVariable"));

        runner.echo("After loggingService call " + "${myVariable}");

    }
}