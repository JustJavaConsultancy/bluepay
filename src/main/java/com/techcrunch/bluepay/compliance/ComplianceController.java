package com.techcrunch.bluepay.compliance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/compliance")
public class ComplianceController {
    @GetMapping("/compliance")
    public String getCompliance(){

        return "/compliance/compliance";
    }
    @GetMapping("/complianceOfficer")
    public String getComplianceOfficer(){

        return "/complianceOfficer/officerDashboard";
    }
    @GetMapping("/pendingRequest")
    public String getpendingRequest(){

        return "/complianceOfficer/pendingRequest";
    }
    @GetMapping("/succesfulCompliance")
    public String getsuccesfulCompliance(){

        return "/complianceOfficer/sucessfulCompliance";
    }
    @GetMapping("/rejectedCompliance")
    public String getrejectedCompliance(){

        return "/complianceOfficer/failedCompliance";
    }
}
