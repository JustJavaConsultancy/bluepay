package com.techcrunch.bluepay.tasks;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

@Component("customTaskListener")
public class CustomTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String businessName = (String) delegateTask.getVariable("businessName");
        String businessType = (String) delegateTask.getVariable("businessType");

        System.out.println(" The variable set here businessType=="+businessType +
                " and businessName=="+businessName);
        // Set task-local variables
        delegateTask.setVariableLocal("businessName", businessName);
        delegateTask.setVariableLocal("businessType", businessType);
    }
}
