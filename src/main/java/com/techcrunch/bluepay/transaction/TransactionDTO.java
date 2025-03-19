package com.techcrunch.bluepay.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techcrunch.bluepay.invoice.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @TransactionReferenceUnique
    private String reference;

    @NotNull
    @Size(max = 255)
    @TransactionExternalReferenceUnique
    private String externalReference;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "92.08")
    private BigDecimal amount;

    @NotNull
    @Size(max = 255)
    private String beneficiaryAccount;

    @NotNull
    @Size(max = 255)
    @TransactionSourceAccountUnique
    private String sourceAccount;

    @NotNull
    private Status status;

    @NotNull
    private PaymentType paymentType;

}
