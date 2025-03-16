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
    public String index(RedirectAttributes redirectAttributes) {


        String loginUser= (String) authenticationManager.get("sub");
 /*       List<String> variableNames = List.of("initiator", "businessType");
        List<Map<String, Object>> taskList=
                merchantService.getSpecificDataMyVariables(variableNames);
        taskList.forEach(task-> System.out.println(" The task are===="+task));
*/


        //System.out.println(" loging user here==="+loginUser);
        String page="home/index";
        String status= (String) merchantService
                .getMerchantStatus(loginUser).get("status");
        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMerchantStatus(loginUser).get("variables");


        //System.out.println("the status now=="+status+" ---The Variables i'm redirecting is====="+variables);
        redirectAttributes.addFlashAttribute("merchantDetails",variables);
        if(authenticationManager.isMerchant()){
            page="redirect:merchant/"+status.toLowerCase();
        }
        if(authenticationManager.isComplianceOfficer()){
            page="redirect:compliance/complianceOfficer";
        }
        return page;
    }

}
