package edu.pucmm.packpurchasesmicroservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PackPurchases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int idCliente;

    @NotNull
    private int idPaquete;

    private int idEmpleado;

    @NotNull
    private String fechaEvento;

    @NotNull
    private boolean empleado;
}
