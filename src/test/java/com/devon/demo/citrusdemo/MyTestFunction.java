package com.devon.demo.citrusdemo;

import java.util.List;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.functions.Function;

public class MyTestFunction implements Function {

    @Override
    public String execute(List<String> parameterList, TestContext context) {
        System.out.println("-------------------- from MyTestFunction------------------------");
        return "from MyTestFunction";
    }

}
