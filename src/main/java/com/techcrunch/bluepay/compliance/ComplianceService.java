package com.techcrunch.bluepay.compliance;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.processes.CustomProcessService;
import com.techcrunch.bluepay.tasks.TaskDTO;
import com.techcrunch.bluepay.tasks.TaskRepository;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplianceService {

    private final TaskRepository taskRepository;


    private final AuthenticationManager authenticationManager;

    private final CustomProcessService customProcessService;

    public ComplianceService(TaskRepository taskRepository, AuthenticationManager authenticationManager, RuntimeService runtimeService, CustomProcessService customProcessService) {
        this.taskRepository = taskRepository;
        this.authenticationManager = authenticationManager;
        this.customProcessService = customProcessService;
    }

    public List<TaskDTO> getComplianceTasks(){
        List<Task> tasks = taskRepository.getTaskByAssignee("compliance");
        List<TaskDTO> taskDTOS = new ArrayList<>();
        tasks.forEach(task -> {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setTaskName(task.getName());
            taskDTO.setTaskId(task.getId());
            taskDTO.setCreatedDate(task.getCreateTime());
            taskDTO.setVariables(customProcessService.getProcessInstanceVariables(task.getProcessInstanceId()));
            taskDTOS.add(taskDTO);
        });
        return taskDTOS;
    }

    public TaskDTO getSingleTask(String id){
        Task task = taskRepository.getTaskById(id);
        return TaskDTO.builder()
                .taskId(task.getId())
                .taskName(task.getName())
                .createdDate(task.getCreateTime())
                .variables(customProcessService.getProcessInstanceVariables(task.getProcessInstanceId()))
                .build();
    }

}
