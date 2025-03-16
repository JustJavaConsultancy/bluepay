package com.techcrunch.bluepay.merchant;

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

    @GetMapping("/new")
    public String getCompliance(){
        return "/compliance/compliance";
    }
    @PostMapping("/submitted")
    public String submitDetails(@RequestParam Map<String,Object> formData){

        System.out.println(" The Submitted Data==="+formData);
        merchantService.submitMyDetail(formData);
        return "home/index";
    }
    @GetMapping("/successful")
    public String merchantStatus(Model model) {


        Map merchantDetails= new HashMap();
        merchantDetails.put("businessType","Partnership");
        merchantDetails.put("businessName","Just Java");
        model.addAttribute("merchantDetails",merchantDetails);

        return "merchant/merchantStatus";
    }
    @GetMapping("/failed")
    public String merchantFailed(Model model) {
        Map merchantDetails= new HashMap();
        merchantDetails.put("businessType","Partnership");
        merchantDetails.put("businessName","Just Java");
        merchantDetails.put("country","Nigeria");
        model.addAttribute("merchantDetails",merchantDetails);

        return "merchant/merchantFailed";
    }

}
