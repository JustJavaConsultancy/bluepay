package com.techcrunch.bluepay.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/loginUser/{field}")
    public String loginUser(@PathVariable String field){
        String fieldValue = (String) authenticationManager.get(field);
        return "<span class=\"position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger\">" + fieldValue + "</span>";
    }
}
