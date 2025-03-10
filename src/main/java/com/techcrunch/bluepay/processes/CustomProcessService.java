package com.techcrunch.bluepay.processes;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomProcessService {

    private final RuntimeService runtimeService;

    public CustomProcessService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public ProcessInstance startProcess(String procesKey,String businessKey,Map<String,Object> variables){
        return runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(procesKey)
                .businessKey(businessKey)
                .variables(variables)
                .start();
    }
}
