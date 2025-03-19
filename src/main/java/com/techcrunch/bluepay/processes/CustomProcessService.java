package com.techcrunch.bluepay.processes;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomProcessService {

    private final RuntimeService runtimeService;

    public CustomProcessService(RuntimeService runtimeService, JdbcTemplate jdbcTemplate) {
        this.runtimeService = runtimeService;
    }

    public ProcessInstance startProcess(String procesKey,String businessKey,
                                        Map<String,Object> variables){

        ProcessInstance processInstance=getProcessInstanceByBusinessKey(businessKey);
        if(processInstance!=null)
            return processInstance;

        return runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(procesKey)
                .businessKey(businessKey)
                .variables(variables)
                .start();
    }
    public ProcessInstance getProcessInstanceByBusinessKey(String businessKey){

        return runtimeService
                .createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .processDefinitionKey("merchantOnboardingProcess")
                .singleResult();
    }
    public Map getProcessInstanceVariables(String processInstanceId){
        return runtimeService.getVariables(processInstanceId);
    }

    public ProcessInstance starSimpletProcess(String procesKey){
        return runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(procesKey)
                .start();
    }
}
