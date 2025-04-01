package com.techcrunch.bluepay.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    @ProductCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    private String name;

    private String description;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "75.08")
    private BigDecimal price;

    private OffsetDateTime dateCreated;

    private Long quantityInStock;

    private Boolean containsPhysicalGoods;

    private List<String> media = new ArrayList<>();
    private BigDecimal quantitySold;
    private String merchantId;

    public String getPrimiaryMedia() {
        if (media.isEmpty()) {
            return "";
        }
        return media.get(0);
    }
}
