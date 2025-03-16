package com.techcrunch.bluepay.tasks;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techcrunch.bluepay.tasks.services.JsonFileReaderService;
import com.techcrunch.bluepay.tasks.services.JustJavaTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TaskFormController {

    @Autowired
    JustJavaTaskService justJavaTaskService;

    @GetMapping("/test-form/{taskid}")
    public String testForm(@PathVariable String taskid,final Model model) {

        return "test";
    }
    @GetMapping("/task-form/{taskid}")
    public String taskForm(@PathVariable String taskid,
                           @RequestParam String processInstanceId, final Model model) {

        try {

            //System.out.println(" processInstanceId == "+processInstanceId);

            TaskInfo taskInfo = justJavaTaskService.getTaskInfo(processInstanceId,taskid);
            //System.out.println(" The String loaded schema here==="+ taskInfo.getSchema());
            //System.out.println(" The String loaded data here==="+ taskInfo.getData());
            model.addAttribute("schema",taskInfo.getSchema());
            model.addAttribute("data",taskInfo.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "tasks/task-form";
    }

    @GetMapping("/tasks/{assignee}")
    public String getTaskAssignedToUser(@PathVariable String assignee, final Model model) {
        model.addAttribute("tasks",justJavaTaskService.getAssignedTaskToUser(assignee));
        return "tasks/task-form";
    }
    @PostMapping("/submit-form/{taskid}")
    public String submitTaskForm(@PathVariable String taskid,
                                 @RequestBody Map<String, Object> formData,
                                 final Model model) {

        //System.out.println(" Here is the submitted data=="+formData);
        return "tasks/task-form";
    }
    @GetMapping("/start-form/{formKey}")
    public String startForm(@PathVariable String formKey, final Model model) {

        try {
            TaskInfo taskInfo = justJavaTaskService.getProcessStartForm(formKey);
            //System.out.println(" The String loaded schema here==="+ taskInfo.getSchema());
            //System.out.println(" The String loaded data here==="+ taskInfo.getData());
            model.addAttribute("schema",taskInfo.getSchema());
            model.addAttribute("data",taskInfo.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "tasks/task-form";
    }
}
