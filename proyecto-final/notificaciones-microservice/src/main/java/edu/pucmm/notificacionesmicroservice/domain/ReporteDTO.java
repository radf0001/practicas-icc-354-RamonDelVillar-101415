package edu.pucmm.notificacionesmicroservice.domain;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private String nombreArchivo;

    private String mimeType;

    private String rawFile;
}


