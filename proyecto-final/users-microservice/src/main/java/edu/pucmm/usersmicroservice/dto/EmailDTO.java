package edu.pucmm.usersmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private String[] toUser;
    private String subject;
    private String message;
}
