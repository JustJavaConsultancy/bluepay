package com.techcrunch.bluepay.merchant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.processes.CustomProcessService;
import com.techcrunch.bluepay.tasks.services.JsonFileReaderService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MerchantService {
    private final AuthenticationManager authenticationManager;
    private final RuntimeService runtimeService;
    private final CustomProcessService processService;

    @Autowired
    JsonFileReaderService jsonFileReaderService;
    public MerchantService(AuthenticationManager authenticationManager,
                           RuntimeService runtimeService,
                           CustomProcessService processService) {
        this.authenticationManager = authenticationManager;
        this.runtimeService = runtimeService;
        this.processService = processService;
    }
    public String getMerchantStatus(String merchantId){
        String result="NEW";
        // Check for active process instance

        ProcessInstance activeProcessInstance=processService.getProcessInstanceByBusinessKey(merchantId);
        if(activeProcessInstance==null)
            return result;
        else {
            Map<String,Object> processVariables=processService
                    .getProcessInstanceVariables(activeProcessInstance.getProcessInstanceId());
            System.out.println(" activeProcessInstance.getProcessVariables()=="+
                    processVariables);
            try {
                System.out.println("JSON Representation===="+jsonFileReaderService.returnObjectAsJSONString(processVariables));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            result= "SUCCESSFUL";//(String) processVariables.get("onboardStatus");
        }
        return result;
    }
    public ProcessInstance submitMyDetail(Map<String,Object> data){

        data.put("onboardStatus","SUBMITTED");
        String loginUser= (String) authenticationManager.get("sub");
        return processService
                .startProcess("merchantOnboardingProcess",loginUser,data);
    }
}
