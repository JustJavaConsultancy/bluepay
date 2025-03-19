package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.compliance.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    AuthenticationManager authenticationManager;

    @Autowired
    ComplianceService complianceService;

    @GetMapping("/new")
    public String getCompliance(Model model){
        Map<String,Object> variables=new HashMap<>();

        System.out.println(" I'm going to new endpoint....");
        variables.put("country","NG");
        variables.put("businessName",authenticationManager.get("businessName"));
        variables.put("businessType","Unregistered/Starterbusiness");
        model.addAttribute("merchantDetails",variables);
        return "/compliance/compliance";
    }
    @PostMapping("/submit")
    public ResponseEntity<Void> submitDetails(@RequestParam Map<String,Object> formData){
        merchantService.submitMyDetail(formData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/overview");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    @PostMapping("/resubmit")
    public ResponseEntity<Void> reSubmitDetails(@RequestParam Map<String,Object> formData){

        System.out.println(" The variable I'm throwing in here===\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"+formData);
        merchantService.completeDocumentResubmittion(formData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/overview");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    @GetMapping("/submitted")
    public String getSubmittedStatus(Model model){
        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMyRegistrationStatus()
                        .get("variables");


/*        System.out.println("the status now=="+status+
                " ---The Variables i'm redirecting is====="+variables);*/
        model.addAttribute("merchantDetails",variables);

        return "merchant/merchantPending";
    }
    @GetMapping("/approved")
    public String getApprovedStatus(Model model){


        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMyRegistrationStatus()
                        .get("variables");


/*        System.out.println("the status now=="+status+
                " ---The Variables i'm redirecting is====="+variables);*/
        model.addAttribute("merchantDetails",variables);
        return "merchant/merchantStatus";
    }
    @GetMapping("/successful")
    public String merchantStatus(Model model) {


        Map merchantDetails= new HashMap();
        merchantDetails.put("businessType","Partnership");
        merchantDetails.put("businessName","Just Java");
        model.addAttribute("merchantDetails",merchantDetails);

        return "merchant/merchantStatus";
    }
    @GetMapping("/declined")
    public String merchantFailed(Model model) {
        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMyRegistrationStatus()
                        .get("variables");


/*        System.out.println("the status now=="+status+
                " ---The Variables i'm redirecting is====="+variables);*/
        model.addAttribute("merchantDetails",variables);
        return "merchant/merchantFailed";
    }

}
