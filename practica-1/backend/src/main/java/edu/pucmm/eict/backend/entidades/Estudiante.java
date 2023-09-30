package edu.pucmm.eict.backend.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Estudiante {

    @Id
    private int matricula;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String telefono;
}
