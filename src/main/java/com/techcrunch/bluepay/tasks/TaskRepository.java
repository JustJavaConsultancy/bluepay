package com.techcrunch.bluepay.tasks;

import com.techcrunch.bluepay.processes.CustomProcessService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private final TaskService taskService;

    private final HistoryService historyService;
    private final CustomProcessService customProcessService;

    public TaskRepository(JdbcTemplate jdbcTemplate, TaskService taskService, HistoryService historyService, CustomProcessService customProcessService) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskService = taskService;
        this.historyService = historyService;
        this.customProcessService = customProcessService;
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
    public HistoricTaskInstance getHistoricTaskById(String id){
        HistoricTaskInstance task = historyService
                .createHistoricTaskInstanceQuery()
                .taskId(id)
                .singleResult();
        return task;
    }

    public void completeTask(String taskId,Map<String,Object> variables){
        //System.out.println(" Variable inside the complete task=="+variables);
        taskService.complete(taskId,variables);
    }

    public List<TaskDTO> getCompletedTaskByAssigneeAndVariable(String assignee,
                                                               String variableName,String variableValue){
        List<TaskDTO> tasks=new ArrayList<>();
        List<HistoricTaskInstance> historicTasks = historyService
                .createHistoricTaskInstanceQuery()
                .taskAssignee(assignee)
                .processVariableValueEquals(variableName, variableValue)
                .finished()
                .list();
        historicTasks.forEach(historicTask->{
            //System.out.println( " The Historical ID==="+historicTask.getId());
            tasks.add(getSingleHistoricTask(historicTask.getId()));
        });
        return tasks;
    }
    public TaskDTO getSingleTask(String id){
        Task task = getTaskById(id);
        return TaskDTO.builder()
                .taskId(task.getId())
                .taskName(task.getName())
                .formKey(task.getFormKey())
                .createdDate(task.getCreateTime())
                .variables(customProcessService.getProcessInstanceVariables(task.getProcessInstanceId()))
                .build();
    }
    public TaskDTO getSingleHistoricTask(String id){
        HistoricTaskInstance task = getHistoricTaskById(id);
        return TaskDTO.builder()
                .taskId(task.getId())
                .taskName(task.getName())
                .formKey(task.getFormKey())
                .createdDate(task.getCreateTime())
                .variables(customProcessService.getProcessInstanceVariables(task.getProcessInstanceId()))
                .build();
    }
}