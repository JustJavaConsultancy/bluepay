package com.techcrunch.bluepay;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.merchant.MerchantService;
import com.techcrunch.bluepay.processes.CustomProcessService;
import jakarta.servlet.http.HttpServletRequest;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;


@Controller
public class HomeController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomProcessService processService;

    @Autowired
    MerchantService merchantService;

    @GetMapping("/")
    public String index() {


        System.out.println(" Starting to debug");
        processService.startProcess("verificationProcess",
                "akinkunmikinrinde", Map.of("test","Inside Out"));
        System.out.println(" Ending the debug..");

        String loginUser= (String) authenticationManager.get("sub");
        String page="home/index";
        if(authenticationManager.isMerchant()){
            if("NEW".equalsIgnoreCase(merchantService.getMerchantStatus(loginUser))){
                page="redirect:compliance/compliance";
            }
        }

        return page;
    }

}
