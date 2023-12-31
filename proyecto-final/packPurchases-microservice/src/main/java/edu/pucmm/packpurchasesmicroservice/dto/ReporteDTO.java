package edu.pucmm.packpurchasesmicroservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    @NotBlank
    private String nombreArchivo;

    @NotBlank
    private String mimeType;

    @NotBlank
    private String rawFile;
}


