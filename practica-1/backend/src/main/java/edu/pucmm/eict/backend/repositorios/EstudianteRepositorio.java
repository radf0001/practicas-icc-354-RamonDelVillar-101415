package edu.pucmm.eict.backend.repositorios;

import edu.pucmm.eict.backend.entidades.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Integer> {

}
