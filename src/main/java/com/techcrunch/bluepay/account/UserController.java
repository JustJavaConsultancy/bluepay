package com.techcrunch.bluepay.account;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping("/loginUser")
    public String loginUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        return "account/loginUser";
    }
}
