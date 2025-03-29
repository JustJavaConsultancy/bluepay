package com.techcrunch.bluepay.merchant;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Merchant}
 */
@Value
public class MerchantDto implements Serializable {
    Long id;
    String businessIdentity;
    String businessName;
    String businessAddress;
}