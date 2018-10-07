//package com.devon.demo.citruscucumber;
//
//import com.consol.citrus.annotations.CitrusResource;
//import com.consol.citrus.dsl.design.TestDesigner;
//import com.consol.citrus.message.MessageType;
//
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//
//public class EchoSteps {
//    @CitrusResource
//    protected TestDesigner designer;
//
//    @Given("^My name is (.*)$")
//    public void my_name_is(String name) {
//        designer.variable("username", name);
//    }
//
//    @When("^I say hello.*$")
//    public void say_hello() {
//        designer.send("echoEndpoint").messageType(MessageType.PLAINTEXT).payload("Hello, my name is ${username}!");
//    }
//
//    @When("^I say goodbye.*$")
//    public void say_goodbye() {
//        designer.send("echoEndpoint").messageType(MessageType.PLAINTEXT).payload("Goodbye from ${username}!");
//    }
//
//    @Then("^the service should return: \"([^\"]*)\"$")
//    public void verify_return(final String body) {
//        designer.receive("echoEndpoint").messageType(MessageType.PLAINTEXT).payload("You just said: " + body);
//    }
//}
