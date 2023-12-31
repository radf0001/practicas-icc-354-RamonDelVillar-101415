package edu.pucmm.clientemicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String role;
}
