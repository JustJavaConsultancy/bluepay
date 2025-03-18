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
import java.util.Map;

@Service
public class ComplianceService {

    private final TaskRepository taskRepository;


    private final AuthenticationManager authenticationManager;

    public ComplianceService(TaskRepository taskRepository, AuthenticationManager authenticationManager, RuntimeService runtimeService, CustomProcessService customProcessService) {
        this.taskRepository = taskRepository;
        this.authenticationManager = authenticationManager;
    }

    public List<TaskDTO> getComplianceTasks(){
        List<Task> tasks = taskRepository.getTaskByAssignee("compliance");
        List<TaskDTO> taskDTOS = new ArrayList<>();
        tasks.forEach(task -> {
            taskDTOS.add(taskRepository.getSingleTask(task.getId()));
        });
        return taskDTOS;
    }

    public List<TaskDTO> getAllApprovedKYC(){
        return taskRepository.getCompletedTaskByAssigneeAndVariable("compliance",
                "onboardStatus","APPROVED");
    }
    public List<TaskDTO> getAllDeclinedKYC(){
        return taskRepository
                .getCompletedTaskByAssigneeAndVariable("compliance","onboardStatus","DECLINED");
    }

    public Boolean accept(String taskId){
        TaskDTO taskDTO = taskRepository.getSingleTask(taskId);
        Map<String,Object> variables=taskDTO.getVariables();
        variables.replace("onboardStatus","APPROVED");

        //System.out.println(" Variable before complete the task=="+variables);
        taskRepository.completeTask(taskId,variables);
        return true;
    }
    public Boolean decline(String taskId, String rejectReason){
        TaskDTO taskDTO = taskRepository.getSingleTask(taskId);
        Map<String,Object> variables=taskDTO.getVariables();
        variables.replace("onboardStatus","DECLINED");
        variables.put("rejectReason",rejectReason);
        taskRepository.completeTask(taskId,variables);
        return true;
    }
}
