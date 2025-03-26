package com.techcrunch.bluepay.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationResponse implements Serializable {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

}
