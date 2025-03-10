package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.processes.CustomProcessService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MerchantService {
    private final AuthenticationManager authenticationManager;
    private final RuntimeService runtimeService;
    private final CustomProcessService processService;
    public MerchantService(AuthenticationManager authenticationManager, RuntimeService runtimeService, CustomProcessService processService) {
        this.authenticationManager = authenticationManager;
        this.runtimeService = runtimeService;
        this.processService = processService;
    }
    public String getMerchantStatus(String merchantId){
        String result="NEW";
        // Check for active process instance
        ProcessInstance activeInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(merchantId)
                .singleResult();

        System.out.println();
        if(activeInstance==null){
            return result;
        }
        return "UNDER_REVIEW";
    }
    public ProcessInstance submitMyDetail(Map<String,Object> data){
        String loginUser= (String) authenticationManager.get("sub");
        return processService.startProcess("merchantOnboardingProcess",loginUser,data);
    }
}
