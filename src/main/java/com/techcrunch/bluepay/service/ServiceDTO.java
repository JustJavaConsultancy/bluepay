package com.techcrunch.bluepay.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @ServiceCodeUnique
    private String code;

    @NotNull
    @Size(max = 255)
    private String name;

}
