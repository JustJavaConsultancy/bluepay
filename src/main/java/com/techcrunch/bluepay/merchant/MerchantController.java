package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.compliance.ComplianceService;
import jakarta.servlet.http.HttpServletRequest;
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
    ComplianceService complianceService;
    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/new")
    public String getCompliance(Model model){
        String loginUser= (String) authenticationManager.get("sub");

        Map<String,Object> variables = new HashMap<>();

        variables.put("country","NG");
        variables.put("businessType","Unregistered/Starterbusiness");
        variables.put("businessName",authenticationManager.get("businessName"));

        System.out.println(" ---The Variables i'm redirecting is====="+variables);
        model.addAttribute("merchantDetails",variables);
        return "/compliance/compliance";
    }

    @GetMapping("/dashboard")
    public String getDashboard(){
        String loginUser= (String) authenticationManager.get("sub");

        return "merchant/dashboard";
    }

    @GetMapping("/transactions")
    public String getTransaction(){
        return "merchant/transactions";
    }

    @GetMapping("/balance")
    public String getBalance(){
        return "merchant/balance";
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

        return "merchant/merchantPending";
    }
    @GetMapping("/approved")
    public String getApprovedStatus(){
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
        return "merchant/merchantFailed";
    }

    @GetMapping("/orders")
    public String getOrders(){
        return "order/viewOrders";
    }

    @PostMapping("/newMerchant/{step}")
    //@ResponseBody
    public String saveNewMerchant(@PathVariable Integer step, HttpServletRequest request, @RequestParam Map<String, Object> formData,
                                  Model model){
        Map<String,Object> merchantDetails= (Map<String, Object>)
                request.getSession(true).getAttribute("merchantDetails");
        if(merchantDetails==null){
            merchantDetails = new HashMap<>();
        }
        String nextFragment=null;
        System.out.println(" The switching here=="+step);

        switch  (step) {
            case 1:
                nextFragment="compliance/contact :: form2(data=null,complianceButton=null})";
                break;
            case 2:
                nextFragment="compliance/businessOwner :: form3(data=${test},complianceButton=${test})";
                break;
            case 3:
                nextFragment="compliance/bankAccount :: form4(data=${test},complianceButton=${test})";
                break;
            case 4:
                nextFragment="compliance/serviceAgreement :: form5(data=${test})";
                break;
            case 5:
                nextFragment="compliance/summary :: form6(showclass='',backButton='null',hideButton='null',data=null)";
        }
        merchantDetails.putAll(formData);
        request.getSession(true).setAttribute("merchantDetails",merchantDetails);
        System.out.println(" The data sent now inside saveNewMerchant ==="+formData);

        System.out.println(" The Fragment Here.."+nextFragment);
        return nextFragment;
    }
}
