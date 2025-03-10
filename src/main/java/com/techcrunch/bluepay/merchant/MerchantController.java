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
    @PostMapping("/submitDetails")
    public String submitDetails(@RequestParam Map<String,Object> formData){

        System.out.println(" The Submitted Data==="+formData);
        merchantService.submitMyDetail(formData);
        return "home/index";
    }
    @GetMapping("/status")
    public String merchantStatus(Model model) {


        Map merchantDetails= new HashMap();
        merchantDetails.put("businessType","Partnership");
        merchantDetails.put("businessName","Just Java");
        model.addAttribute("merchantDetails",merchantDetails);

        return "merchant/merchantStatus";
    }
}
