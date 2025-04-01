package com.techcrunch.bluepay.invoice;

import com.techcrunch.bluepay.product.ProductDTO;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * DTO for {@link Invoice}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceDTO implements Serializable {

    private Long id;
    private String description;
    private String customerEmail;
    private String customerName;
    private String merchantId;
    private String customerPhoneNumber;
    private LocalDate issueDate;
    private LocalDate dueDate;
    BigDecimal amount;
    Status status;
    OffsetDateTime dateCreated;
    OffsetDateTime lastUpdated;
    private ProductDTO product;
}