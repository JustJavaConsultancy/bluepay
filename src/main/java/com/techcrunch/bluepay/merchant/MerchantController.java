package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.compliance.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    @Autowired
    ComplianceService complianceService;

    @GetMapping("/new")
    public String getCompliance(Model model){

        return "/compliance/compliance";
    }
    @PostMapping("/submit")
    public String submitDetails(@RequestParam Map<String,Object> formData){
        merchantService.submitMyDetail(formData);
        return "home/index";
    }
    @PostMapping("/resubmit")
    public String reSubmitDetails(@RequestParam Map<String,Object> formData){
        merchantService.completeDocumentResubmittion(formData);
        return "home/index";
    }
    @GetMapping("/submitted")
    public String getSubmittedStatus(){
        return "merchant/merchantStatus";
    }
    @GetMapping("/approved")
    public String getApprovedStatus(){

        return "merchant/merchantStatus";
    }
    @GetMapping("/successful")
    public String merchantStatus(Model model) {


/*
        Map merchantDetails= new HashMap();
        merchantDetails.put("businessType","Partnership");
        merchantDetails.put("businessName","Just Java");
        model.addAttribute("merchantDetails",merchantDetails);
*/

        return "merchant/merchantStatus";
    }
    @GetMapping("/declined")
    public String merchantFailed(Model model) {
        return "merchant/merchantFailed";
    }

}
