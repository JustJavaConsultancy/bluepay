package com.techcrunch.bluepay.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/loginUser/{field}")
    @ResponseBody
    public String loginUser(@PathVariable String field) {
        String fieldValue = (String) authenticationManager.get(field);
        return fieldValue; // Return plain text instead of an HTML badge
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "/";
    }
}
