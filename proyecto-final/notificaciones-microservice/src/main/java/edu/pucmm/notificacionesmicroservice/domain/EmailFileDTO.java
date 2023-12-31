package edu.pucmm.notificacionesmicroservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailFileDTO {

    private String[] toUser;
    private String subject;
    private String message;
    private ReporteDTO file;
}
