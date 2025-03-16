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
import java.util.List;
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
@Controller
@RequestMapping("/api/menu")
class MenuController {

    private final List<Map<String, String>> menuItems = List.of(
            createMenuItem("Inbox Items","/start-form/acceptBusinessSchema"),
            createMenuItem("Merchant Details","/start-form/businessRegistrationSchema"),
            createMenuItem("Registered Merchants","/start-form/table"),
            createMenuItem("Compliance", "/compliance/compliance"),
            createMenuItem("MerchantStatus", "/merchant/status"),
            createMenuItem("Merchant failed", "/merchant/failed"),
            createMenuItem("Compliance Officer", "/compliance/complianceOfficer")
    );

    @GetMapping("/count")
    @ResponseBody
    public String getMenuCount() {
        return "<span class=\"position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger\">" + menuItems.size() + "</span>";
    }

    @GetMapping("/items")
    public String getMenuItems(Model model) {
        model.addAttribute("count", menuItems.size());
        model.addAttribute("menuItems", menuItems);
        return "fragments/menu-items"; // Thymeleaf fragment
    }

    private Map<String, String> createMenuItem(String name, String url) {
        return Map.of("name", name, "url", url);
    }
}
