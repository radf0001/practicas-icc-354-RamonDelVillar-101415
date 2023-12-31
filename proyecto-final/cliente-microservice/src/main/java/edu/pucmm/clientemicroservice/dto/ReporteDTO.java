package edu.pucmm.clientemicroservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private String nombreArchivo;

    private String mimeType;

    private String rawFile;
}


