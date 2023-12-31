package edu.pucmm.clientemicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackDto {
    private int id;
    private String nombre;
    private Float precio;
}
