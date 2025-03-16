package com.techcrunch.bluepay.flowableservices;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("initVariablesService")
public class InitVariablesService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {

        execution.setVariable("tokenValue", "YNSvRx1u.g6rh0CM2OJ5aa1U9kPFtyrGkSeqn3kMxQAOH");
        execution.setVariable("baseUrl", "https://api.sandbox.youverify.co");

    }
}
