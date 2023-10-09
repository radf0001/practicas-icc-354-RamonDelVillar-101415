package edu.pucmm.eict.practica2.repositorio.seguridad;

import edu.pucmm.eict.practica2.entidades.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,String> {

    //Cualquier metodo que necesite incluir.
}
