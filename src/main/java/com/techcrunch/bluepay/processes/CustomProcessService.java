package com.techcrunch.bluepay.processes;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomProcessService {

    private final RuntimeService runtimeService;
    private final JdbcTemplate jdbcTemplate;

    public CustomProcessService(RuntimeService runtimeService, JdbcTemplate jdbcTemplate) {
        this.runtimeService = runtimeService;
        this.jdbcTemplate = jdbcTemplate;
        System.out.println(" jdbcTemplate=========================" +
                "================================" +
                "======================================="+jdbcTemplate);
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

        return runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
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
    public List<Map<String, Object>> fetchSpecificVariablesForAllActiveProcessInstances(List<String> variableNames) {
        // Convert the list of variable names to a comma-separated string for the SQL IN clause
        String variables = variableNames.stream()
                .map(name -> "'" + name + "'")
                .collect(Collectors.joining(", "));

        String sql = """
            SELECT 
                e.PROC_INST_ID_ AS process_instance_id,
                e.BUSINESS_KEY_ AS business_key, -- Include business key
                v.NAME_ AS variable_name,
                v.TEXT_ AS variable_value
            FROM 
                ACT_RU_EXECUTION e
            JOIN 
                ACT_RU_VARIABLE v ON e.PROC_INST_ID_ = v.PROC_INST_ID_
            WHERE 
                e.IS_ACTIVE_ = 1
                AND v.NAME_ IN (
                """ + variables + ");";
        // Execute the query and return the results
        return jdbcTemplate.queryForList(sql);
    }
}
