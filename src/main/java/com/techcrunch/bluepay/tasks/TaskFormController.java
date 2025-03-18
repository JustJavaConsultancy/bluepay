package com.techcrunch.bluepay.tasks;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.compliance.ComplianceService;
import com.techcrunch.bluepay.merchant.MerchantService;
import com.techcrunch.bluepay.tasks.services.JsonFileReaderService;
import com.techcrunch.bluepay.tasks.services.JustJavaTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TaskFormController {

    @Autowired
    JustJavaTaskService justJavaTaskService;
    @Autowired
    ComplianceService complianceService;

    @GetMapping("/resubmit/{taskId}")
    public String merchantFailedWithoutTaskId(@PathVariable String taskId, Model model) {
        model.addAttribute("merchantDetails",
                complianceService.getSingleTask(taskId).getVariables());
        return "merchant/merchantFailed";
    }
    @GetMapping("/complianceVerification/{taskId}")
    public String getcomplianceDetail(@PathVariable String taskId, Model model){

        TaskDTO taskDTO = complianceService.getSingleTask(taskId);
        model.addAttribute("merchantDetails",taskDTO.getVariables());
        return "/complianceOfficer/complianceDetails";
    }
    @GetMapping("/test-form/{taskid}")
    public String testForm(@PathVariable String taskid,final Model model) {

        return "test";
    }
    @GetMapping("/task-form/{taskid}")
    public String taskForm(@PathVariable String taskid,
                           @RequestParam String processInstanceId, final Model model) {

        try {

            System.out.println(" processInstanceId == "+processInstanceId);

            TaskInfo taskInfo = justJavaTaskService.getTaskInfo(processInstanceId,taskid);
            System.out.println(" The String loaded schema here==="+ taskInfo.getSchema());
            System.out.println(" The String loaded data here==="+ taskInfo.getData());
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

        System.out.println(" Here is the submitted data=="+formData);
        return "tasks/task-form";
    }
    @GetMapping("/start-form/{formKey}")
    public String startForm(@PathVariable String formKey, final Model model) {

        try {
            TaskInfo taskInfo = justJavaTaskService.getProcessStartForm(formKey);
            System.out.println(" The String loaded schema here==="+ taskInfo.getSchema());
            System.out.println(" The String loaded data here==="+ taskInfo.getData());
            model.addAttribute("schema",taskInfo.getSchema());
            model.addAttribute("data",taskInfo.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "tasks/task-form";
    }
}
@Controller
@RequestMapping("/api/menu")
class MenuController {
    @Autowired
    MerchantService merchantService;

    @Autowired
    ComplianceService complianceService;

    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/count")
    @ResponseBody
    public String getMenuCount() {

        List<TaskDTO> taskDTOS = merchantService.getMyTasks();
        if(authenticationManager.isComplianceOfficer()){
            taskDTOS = complianceService.getComplianceTasks();
        }

        return "<span class=\"position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger\">" + taskDTOS.size() + "</span>";
    }

    @GetMapping("/items")
    public String getMenuItems(Model model) {


        List<Map<String, Object>> menuItems = new ArrayList<>();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        if(authenticationManager.isMerchant()){
            taskDTOS = merchantService.getMyTasks();
        }
        if(authenticationManager.isComplianceOfficer()){
            taskDTOS = complianceService.getComplianceTasks();
        }
        System.out.println(" My Task here size=="+taskDTOS.size());
        taskDTOS.forEach(taskDTO -> {
            Map<String,Object> menuItem = new HashMap<>();
            menuItem.put("name",taskDTO.getTaskName());
            menuItem.put("url","/"+taskDTO.getFormKey()+"/"+taskDTO.getTaskId());
            menuItems.add(menuItem);
        });

        model.addAttribute("count", menuItems.size());
        model.addAttribute("menuItems", menuItems);
        return "fragments/menu-items"; // Thymeleaf fragment
    }

    private Map<String, String> createMenuItem(String name, String url) {
        return Map.of("name", name, "url", url);
    }
}
