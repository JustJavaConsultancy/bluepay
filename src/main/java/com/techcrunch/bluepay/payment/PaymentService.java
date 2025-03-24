package com.techcrunch.bluepay.payment;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("paymentService")
public class PaymentService {

    public FraudResponse checkFraud(PaymentDTO paymentDTO){
        FraudResponse fraudResponse = new FraudResponse();
        fraudResponse.setCode("00");
        fraudResponse.setMessage("Successful");
        return fraudResponse;
    }

    public FraudResponse checkAml(PaymentDTO paymentDTO){
        FraudResponse fraudResponse = new FraudResponse();
        fraudResponse.setCode("00");
        fraudResponse.setMessage("Successful");
        return fraudResponse;
    }
    public AuthorizationResponse authorize(PaymentDTO paymentDTO){
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
}
