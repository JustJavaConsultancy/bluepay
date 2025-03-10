package com.techcrunch.bluepay.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
