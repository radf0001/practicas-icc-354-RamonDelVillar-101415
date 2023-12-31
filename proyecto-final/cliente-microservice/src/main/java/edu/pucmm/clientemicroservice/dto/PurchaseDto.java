package edu.pucmm.clientemicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
    private int id;
    private int idCliente;
    private int idPaquete;
    private int idEmpleado;
    private String fechaEvento;
    private boolean empleado;
}
