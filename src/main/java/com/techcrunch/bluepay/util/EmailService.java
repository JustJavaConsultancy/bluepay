package com.techcrunch.bluepay.util;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("emailService")
public class EmailService {

    public void sendMail(DelegateExecution execution){
        System.out.println("" +
                " Sending mail......"+execution);
    }
}
