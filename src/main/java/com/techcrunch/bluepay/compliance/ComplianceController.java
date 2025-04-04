package com.techcrunch.bluepay.compliance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.techcrunch.bluepay.processes.CustomProcessService;
import com.techcrunch.bluepay.tasks.TaskDTO;
import com.techcrunch.bluepay.tasks.TaskRepository;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/compliance")
public class ComplianceController {

    @Autowired
    CustomProcessService customProcessService;

    @Autowired
    ComplianceService complianceService;

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping("/compliance")
    public String getCompliance(){

        return "/compliance/compliance";
    }
    @PostMapping("/accept")
    public ResponseEntity<Void> rejectionDetails(@RequestParam Map<String, Object> formData) {
        System.out.println("The Submitted Data === " + formData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/compliance/complianceOfficer");

        complianceService.accept((String) formData.get("id"));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    @PostMapping("/rejected")
    public ResponseEntity<Void> acceptedDetails(@RequestParam Map<String, Object> formData) {
        System.out.println("The Submitted Data === " + formData);

        // Return HX-Redirect to navigate to the compliance officer dashboard
        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/compliance/complianceOfficer");

        complianceService.decline((String) formData.get("id"), (String) formData.get("rejectinReason"));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    @GetMapping("/complianceOfficer")
    public String getComplianceOfficer(Model model){

        List<Map<String, Object>> pending = new ArrayList<>();
        List<TaskDTO> tasks = complianceService.getComplianceTasks();
        tasks.forEach(
                task -> {
                    System.out.println("Task Name=="+task.getTaskName()
                            + " task creation date=="+task.getCreatedDate()
                            + " task id==" + task.getTaskId());
                    Map<String,Object> variables= task.getVariables();
                    variables.put("taskId",task.getTaskId());
                    pending.add(variables);
                }
        );
        List<Map<String, Object>> successful = new ArrayList<>();
        List<TaskDTO> successfulTasks = complianceService.getAllApprovedKYC();
        List<TaskDTO> failedTasks = complianceService.getAllDeclinedKYC();

        System.out.println(" The failed task count=="+failedTasks.size());
        failedTasks.forEach(failed->{
            System.out.println(" The failed task id ==="+failed.getTaskId());
        });

        model.addAttribute("pending",pending.size());
        model.addAttribute("approveCount",successfulTasks.size());
        model.addAttribute("failedTaskCount",failedTasks.size());

        model.addAttribute("employees", pending);

        return "/complianceOfficer/officerDashboard";
    }
    @GetMapping("/pendingRequest")
    public String getpendingRequest(Model model){
        List<Map<String, Object>> employees = new ArrayList<>();

        List<TaskDTO> tasks = complianceService.getComplianceTasks();
        tasks.forEach(taskDTO -> {
            employees.add(taskDTO.getVariables());
        });
        model.addAttribute("employees", employees);

        return "/complianceOfficer/pendingRequest";
    }
    @GetMapping("/succesfulCompliance")
    public String getsuccesfulCompliance(Model model){

        List<Map<String, Object>> employees = new ArrayList<>();

        List<TaskDTO> tasks = complianceService.getAllApprovedKYC();
        tasks.forEach(taskDTO -> {
            employees.add(taskDTO.getVariables());
        });

        model.addAttribute("employees", employees);

        return "/complianceOfficer/sucessfulCompliance";
    }
    @GetMapping("/rejectedCompliance")
    public String getrejectedCompliance(Model model){

        List<Map<String, Object>> employees = new ArrayList<>();

        List<TaskDTO> tasks = complianceService.getAllDeclinedKYC();
        tasks.forEach(taskDTO -> {
            employees.add(taskDTO.getVariables());
        });
        model.addAttribute("employees", employees);
        return "/complianceOfficer/failedCompliance";
    }
    @GetMapping("/complianceDetail/{taskId}")
    public String getcomplianceDetail(@PathVariable String taskId, Model model){

        TaskDTO taskDTO = taskRepository.getSingleTask(taskId);
        System.out.println(" taskDTO==="+taskDTO.getVariables());
        ObjectNode verify= (ObjectNode) taskDTO.getVariables().get("bvnResponse");
        System.out.println(" Verifying Here...."+verify);
/*        Map<String, Object> verifyResponse = null;
        try {
            verifyResponse = objectMapper.readValue(verify, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
        model.addAttribute("verify", verify);
        model.addAttribute("merchantDetails",taskDTO.getVariables());
        model.addAttribute("addressVerification",false);
        model.addAttribute("bvnVerification",false);
        return "/complianceOfficer/complianceDetails";
    }
}
