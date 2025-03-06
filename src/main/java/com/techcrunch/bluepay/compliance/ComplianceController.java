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
}
