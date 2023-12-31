package edu.pucmm.packpurchasesmicroservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private String[] toUser;
    private String subject;
    private String message;
}
