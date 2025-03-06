package com.techcrunch.bluepay;

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
    @GetMapping("/")
    public String index(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        System.out.println(" The Principal sent=="+oAuth2AuthenticationToken.getPrincipal().getAttributes());
        return "home/index";
    }

}
