package com.techcrunch.bluepay.flowableservices;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("firstService")
public class FirstService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {

        System.out.println(" All the variables in this process==="+execution.getVariables());
        // Retrieve a process variable (optional)
        String myVariable = (String) execution.getVariable("myVariable");
        // Business logic
        // Set a new process variable
        execution.setVariable("myVariable", "Processed: " + myVariable);
    }
}
