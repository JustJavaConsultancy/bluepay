package com.techcrunch.bluepay.flowableservices;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("firstService")
public class FirstService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {

/*        System.out.println(" The activity here==="+
                execution.getCurrentActivityName()
        +" and the varaibles so far=="+execution.getVariables());*/
    }
}
