package edu.pucmm.packpurchasesmicroservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailFileDTO {

    private String[] toUser;
    private String subject;
    private String message;
    private ReporteDTO file;
}
