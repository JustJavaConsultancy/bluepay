package com.techcrunch.bluepay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;

@SpringBootApplication
public class BluepayApplication {
    private final static Logger LOG = LoggerFactory.getLogger(BluepayApplication.class);
    public static void main(final String[] args) {

        // Define your client ID and secret key
/*        String clientId = "IKIA3B827951EA3EC2E193C51DA1D22988F055FD27DE";
        String secretKey = "ajkdpGiF6PHVrwK";

        // Concatenate with a colon
        String concatenatedString = clientId + ":" + secretKey;

        // Encode to Base64
        String encodedString = Base64.getEncoder().encodeToString(concatenatedString.getBytes());

        System.out.println(" The base64 for interswitch===============================\n" +
                "=================================================================\n" +
                "=================================================================\n" +
                "=================================="+encodedString);*/

        SpringApplication.run(BluepayApplication.class, args);
    }

}
