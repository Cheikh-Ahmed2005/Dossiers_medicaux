package com.medical.dossier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceDTO {
    private Long id;

    @NotBlank
    private String nomService;

    private String description;
}
