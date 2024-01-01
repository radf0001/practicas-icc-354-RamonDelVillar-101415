package edu.pucmm.packpurchasesmicroservice;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackPurchasesRepository extends JpaRepository<PackPurchases, Integer> {

    List<PackPurchases> findAllByIdClienteOrIdEmpleado(int idCliente, int idEmpleado);

    List<PackPurchases> findAllByEmpleadoIs(@NotNull boolean empleado);

}


