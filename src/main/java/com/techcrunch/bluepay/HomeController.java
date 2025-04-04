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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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
    public String index(HttpServletRequest request,RedirectAttributes redirectAttributes) {
        String loginUser= (String) authenticationManager.get("sub");

        System.out.println(" The login user here..."+loginUser);
        String page="home/index";
        String status= (String) merchantService
                .getMerchantStatus(loginUser).get("status");
        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMerchantStatus(loginUser).get("variables");

        variables.put("status",status);

        System.out.println("the status now=="+status+
                " ---The Variables i'm redirecting is====="+variables);
        redirectAttributes.addFlashAttribute("merchantDetails",variables);

        if(authenticationManager.isMerchant()){
            page="redirect:merchant/"+status.toLowerCase();
            request.getSession(true).setAttribute("chatGroup",loginUser);
        }
        if(authenticationManager.isComplianceOfficer()){
            page="redirect:compliance/complianceOfficer";
            request.getSession(true).setAttribute("chatGroup","compliance");
        }
        request.getSession(true).setAttribute("status",status.toLowerCase());
        request.getSession(true).setAttribute("isMerchant",authenticationManager.isMerchant());
        return page;
    }
@GetMapping("/overview")
    public String overview(){
        return "/home/index";
}

@GetMapping("/bluepay-site")
    public String bluepaySite(){
        return "customer-support/index";
}
    @GetMapping("/support")
    public String support(){
        return "customer-support/Support/support";
    }
}

