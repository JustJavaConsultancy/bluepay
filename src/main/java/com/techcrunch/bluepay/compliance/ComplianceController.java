package com.techcrunch.bluepay.compliance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/compliance")
public class ComplianceController {
    @GetMapping("/compliance")
    public String getCompliance(){

        return "/compliance/compliance";
    }
    @PostMapping("/accept")
    public String acceptedDetails(@RequestParam Map<String,Object> formData){

        System.out.println(" The Submitted Data==="+formData);
        return "/complianceOfficer/officerDashboard";
    }
    public String rejectionDetails(@RequestParam Map<String,Object> formData){

        System.out.println(" The Submitted Data==="+formData);
        return "/complianceOfficer/officerDashboard";
    }
    @GetMapping("/complianceOfficer")
    public String getComplianceOfficer(Model model){
        List<Map<String, Object>> employees = new ArrayList<>();

        Map<String, Object> emp1 = new HashMap<>();
        emp1.put("id", 1);
        emp1.put("businessName", "Justjava");
        emp1.put("country", "Nigeria");
        emp1.put("email", "justjava@gmail.com");
        emp1.put("businessType", "Status");
        emp1.put("date", "6 july,2025");
        emp1.put("time", "10:20:15");
        employees.add(emp1);

        Map<String, Object> emp2 = new HashMap<>();
        emp2.put("id", 2);
        emp2.put("businessName", "PI Ventures");
        emp2.put("country", "Ghana");
        emp2.put("email", "piventures@gmail.com");
        emp2.put("businessType", "Private");
        emp2.put("date", "10 july,2025");
        emp2.put("time", "10:50:15");
        employees.add(emp2);

        model.addAttribute("employees", employees);

        return "/complianceOfficer/officerDashboard";
    }
    @GetMapping("/pendingRequest")
    public String getpendingRequest(Model model){
        List<Map<String, Object>> employees = new ArrayList<>();

        Map<String, Object> emp1 = new HashMap<>();
        emp1.put("id", 1);
        emp1.put("businessName", "Justjava");
        emp1.put("country", "Nigeria");
        emp1.put("email", "justjava@gmail.com");
        emp1.put("businessType", "Status");
        emp1.put("date", "6 july,2025");
        emp1.put("time", "10:20:15");
        employees.add(emp1);

        Map<String, Object> emp2 = new HashMap<>();
        emp2.put("id", 2);
        emp2.put("businessName", "PI Ventures");
        emp2.put("country", "Ghana");
        emp2.put("email", "piventures@gmail.com");
        emp2.put("businessType", "Private");
        emp2.put("date", "10 july,2025");
        emp2.put("time", "10:50:15");
        employees.add(emp2);

        model.addAttribute("employees", employees);

        return "/complianceOfficer/pendingRequest";
    }
    @GetMapping("/succesfulCompliance")
    public String getsuccesfulCompliance(Model model){

        List<Map<String, Object>> employees = new ArrayList<>();

        Map<String, Object> emp1 = new HashMap<>();
        emp1.put("id", 1);
        emp1.put("businessName", "Justjava");
        emp1.put("country", "Nigeria");
        emp1.put("email", "justjava@gmail.com");
        emp1.put("businessType", "Status");
        emp1.put("date", "6 july,2025");
        emp1.put("time", "10:20:15");
        employees.add(emp1);

        Map<String, Object> emp2 = new HashMap<>();
        emp2.put("id", 2);
        emp2.put("businessName", "PI Ventures");
        emp2.put("country", "Ghana");
        emp2.put("email", "piventures@gmail.com");
        emp2.put("businessType", "Private");
        emp2.put("date", "10 july,2025");
        emp2.put("time", "10:50:15");
        employees.add(emp2);

        model.addAttribute("employees", employees);
        return "/complianceOfficer/sucessfulCompliance";
    }
    @GetMapping("/rejectedCompliance")
    public String getrejectedCompliance(Model model){

        List<Map<String, Object>> employees = new ArrayList<>();

        Map<String, Object> emp1 = new HashMap<>();
        emp1.put("id", 1);
        emp1.put("businessName", "Justjava");
        emp1.put("country", "Nigeria");
        emp1.put("email", "justjava@gmail.com");
        emp1.put("businessType", "Status");
        emp1.put("date", "6 july,2025");
        emp1.put("time", "10:20:15");
        employees.add(emp1);

        Map<String, Object> emp2 = new HashMap<>();
        emp2.put("id", 2);
        emp2.put("businessName", "PI Ventures");
        emp2.put("country", "Ghana");
        emp2.put("email", "piventures@gmail.com");
        emp2.put("businessType", "Private");
        emp2.put("date", "10 july,2025");
        emp2.put("time", "10:50:15");
        employees.add(emp2);

        model.addAttribute("employees", employees);
        return "/complianceOfficer/failedCompliance";
    }
    @GetMapping("/complianceDetail")
    public String getcomplianceDetail(){

        return "/complianceOfficer/complianceDetails";
    }
}
