package com.techcrunch.bluepay.payment;
import com.techcrunch.bluepay.processes.CustomProcessService;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("paymentService")
public class PaymentService {

    private final CustomProcessService processService;

    public PaymentService(CustomProcessService processService) {
        this.processService = processService;
    }

    public FraudResponse checkFraud(DelegateExecution execution){
        System.out.println("checkFraud execution====="+execution.getVariables());
        FraudResponse fraudResponse = new FraudResponse();
        fraudResponse.setCode("00");
        fraudResponse.setMessage("Successful");
        //execution.setVariable("fraudResponse",fraudResponse);
        return fraudResponse;
    }

    public FraudResponse checkAml(DelegateExecution execution){
        FraudResponse fraudResponse = new FraudResponse();
        fraudResponse.setCode("00");
        fraudResponse.setMessage("Successful");
        return fraudResponse;
    }
    public AuthorizationResponse authorize(DelegateExecution execution){

        System.out.println(" I'm authorizing here......"+execution.getVariables());
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setCode("00");

        authorizationResponse.setMessage("Successful");
        return authorizationResponse;
    }

    public void settlement(DelegateExecution execution){
        System.out.println("The execution while settlement=="+execution);
    }
    public void reconciliation(DelegateExecution execution){
        System.out.println("The execution while reconciliation=="+execution);
    }
    public void startPaymentProcess(Map<String,Object> variables,String merchantId){
        processService.startProcessByMessageStartEvent(merchantId,
                "processPayment",variables);
    }
}
