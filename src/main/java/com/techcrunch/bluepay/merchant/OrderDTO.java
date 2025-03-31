package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.invoice.InvoiceDTO;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link Order}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDTO implements Serializable {
    Long id;
    String merchantId;
    InvoiceDTO invoice;
    OffsetDateTime dateCreated;
    OffsetDateTime lastUpdated;
}