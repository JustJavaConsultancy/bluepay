package com.techcrunch.bluepay;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.merchant.MerchantService;
import jakarta.servlet.http.HttpServletRequest;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MerchantService merchantService;

    @GetMapping("/")
    public String index() {
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
