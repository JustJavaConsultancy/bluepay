package com.techcrunch.bluepay.tasks.services;

import com.techcrunch.bluepay.tasks.TaskInfo;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JustJavaTaskService {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;
    @Autowired
    JsonFileReaderService jsonFileReaderService;


    public TaskInfo getTaskInfo(String processInstanceId,String taskId) throws IOException {

        System.out.println(" The sent task id==="+taskId);

        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list()
                .stream()
                .filter(task -> taskId.equalsIgnoreCase(task.getFormKey()))
                .collect(Collectors.toList());

        tasks.forEach(task -> System.out.println(" The task full info here getExecutionId=="+
                task.getExecutionId() +" name=="+task.getName() + "  getFormKey===="+task.getFormKey()));

        System.out.println(" the name of the retrieved task =="+tasks.get(0).getName());
        Task flowableTask = taskService.createTaskQuery().taskId(tasks.get(0).getId()).singleResult();

        Map variables=runtimeService.getVariables(flowableTask.getProcessInstanceId());
        TaskInfo task = TaskInfo
                .builder()
                .schema(jsonFileReaderService.readJsonFile("forms/"+taskId+".json"))
                .data(jsonFileReaderService.returnObjectAsJSONString(variables))
                .build();

        return task;
    }

    public List<Task> getAssignedTaskToUser(String assignee){
        return taskService.createTaskQuery().taskAssignee(assignee).active().list();
    }

    public TaskInfo getProcessStartForm(String formKey) throws IOException {
        return   TaskInfo
                .builder()
                .schema(jsonFileReaderService.readJsonFile("forms/"+formKey+".json"))
                .data(null)
                .build();
    }
}
