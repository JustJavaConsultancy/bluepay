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
    private final JdbcTemplate jdbcTemplate;

    public CustomProcessService(RuntimeService runtimeService, JdbcTemplate jdbcTemplate) {
        this.runtimeService = runtimeService;
        this.jdbcTemplate = jdbcTemplate;
/*        System.out.println(" jdbcTemplate=========================" +
                "================================" +
                "======================================="+jdbcTemplate);*/
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
    public List<Map<String, Object>> fetchSpecificVariablesForAllActiveProcessInstances(List<String> variableNames) {
        // Convert the list of variable names to a comma-separated string for the SQL IN clause
        String variables = variableNames.stream()
                .map(name -> "'" + name + "'")
                .collect(Collectors.joining(", "));

        String sql = """
                SELECT
                        v.NAME_ AS variable_name,
                       CASE
                           WHEN v.TYPE_ = 'string' THEN v.TEXT_
                           WHEN v.TYPE_ = 'long' THEN CAST(v.LONG_ AS VARCHAR)
                           WHEN v.TYPE_ = 'double' THEN CAST(v.DOUBLE_ AS VARCHAR)
                           WHEN v.TYPE_ = 'boolean' THEN CAST(v.LONG_ AS VARCHAR)
                           ELSE NULL
                       END AS variable_value
                FROM 
                    ACT_RU_EXECUTION e
                JOIN 
                    ACT_RU_VARIABLE v ON e.PROC_INST_ID_ = v.PROC_INST_ID_
                WHERE 
                    e.IS_ACTIVE_ = true
                    AND e.PROC_INST_ID_ = 'dec834a1-0196-11f0-b342-00155d278845'
                    AND v.NAME_ IN (
                    """ + variables + ");";
        // Execute the query and return the results
        return jdbcTemplate.queryForList(sql);
    }
    private static class TaskVariableRowMapper implements RowMapper<Map<String, Object>> {
        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.println(metaData.getColumnName(i) + " - " + metaData.getColumnTypeName(i));
            }
            Map<String, Object> row = new HashMap<>();

            row.put(rs.getString("variable_name"),rs.getString("variable_value") );
            return row;
        }
    }
}
