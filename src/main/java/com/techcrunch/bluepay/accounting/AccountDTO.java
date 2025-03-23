package com.techcrunch.bluepay.accounting;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Account}
 */
@Value
public class AccountDTO implements Serializable {
    String id;
    String name;
    String code;
    String type;
    String currency;
    BigDecimal balance;
}