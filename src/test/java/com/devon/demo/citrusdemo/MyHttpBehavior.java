package com.devon.demo.citrusdemo;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.runner.AbstractTestBehavior;
import com.consol.citrus.dsl.runner.TestRunner;

public class MyHttpBehavior extends AbstractTestBehavior {

    private TestRunner  runner;

    private TestContext context;

    public MyHttpBehavior(TestRunner runner, TestContext testContext) {
        this.runner = runner;
        this.context = testContext;
    }

    @Override
    public void apply() {
        // TODO Auto-generated method stub

    }

}
