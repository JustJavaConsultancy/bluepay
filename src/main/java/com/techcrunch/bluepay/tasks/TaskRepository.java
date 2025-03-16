package com.techcrunch.bluepay.tasks;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private final TaskService taskService;

    public TaskRepository(JdbcTemplate jdbcTemplate, TaskService taskService) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskService = taskService;
    }

    public List<Map<String, Object>> getMyPendingTasksWithVariables(String assignee) {
        String sql = """
            SELECT 
                t.ID_ AS taskId,
                t.NAME_ AS taskName,
                t.PROC_INST_ID_ AS processInstanceId,
                e.BUSINESS_KEY_ AS businessKey,
                v.NAME_ AS variableName,
                COALESCE(v.TEXT_, v.DOUBLE_, v.LONG_) AS variableValue
            FROM ACT_RU_TASK t
            JOIN ACT_RU_EXECUTION e ON t.PROC_INST_ID_ = e.ID_
            LEFT JOIN ACT_RU_VARIABLE v ON t.PROC_INST_ID_ = v.PROC_INST_ID_
            WHERE t.ASSIGNEE_ = ?
            AND t.SUSPENSION_STATE_ = 1
            ORDER BY t.CREATE_TIME_ DESC
        """;

        return jdbcTemplate.queryForList(sql, assignee);
    }
    public List<Map<String, Object>> fetchSpecificVariablesForAssignee(List<String> variableNames, String assignee) {
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
        JOIN 
            ACT_RU_TASK t ON e.PROC_INST_ID_ = t.PROC_INST_ID_ -- Join with tasks
        WHERE 
            e.IS_ACTIVE_ = true -- Filter for active process instances
            AND v.NAME_ IN (""" + variables + ") " + // Filter for specific variables
                "AND t.ASSIGNEE_ = ?"; // Filter by assignee

        // Execute the query with the assignee parameter
        return jdbcTemplate.queryForList(sql, assignee);
    }

    public List<Task> getTaskByAssignee(String assignee){
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        return tasks;
    }

    public Task getTaskById(String id){
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        return task;
    }
}