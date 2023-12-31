package edu.pucmm.packpurchasesmicroservice.dto;

import lombok.Data;

@Data
public class UserDto {

    private int id;

    private String nombre;

    private String apellido;

    private String email;

    private String password;

    private String role;
}
