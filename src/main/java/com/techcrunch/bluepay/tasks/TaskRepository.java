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

    public List<Task> getTaskByAssignee(String assignee){
        List<Task> tasks = taskService
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();
        return tasks;
    }
    public List<Task> getTaskByAssigneeAndProcessDefinitionKey
            (String assignee,String processDefinitionKey){
        List<Task> tasks = taskService
                .createTaskQuery()
                .taskAssignee(assignee)
                .processDefinitionKey(processDefinitionKey)
                .list();
        return tasks;
    }

    public Task getTaskById(String id){
        Task task = taskService
                    .createTaskQuery()
                    .taskId(id)
                    .singleResult();
        return task;
    }

    public void completeTask(String taskId,Map<String,Object> variables){
        //System.out.println(" Variable inside the complete task=="+variables);
        taskService.complete(taskId,variables);
    }
}