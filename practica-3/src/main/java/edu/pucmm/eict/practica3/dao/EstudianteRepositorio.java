package edu.pucmm.eict.practica3.dao;

import edu.pucmm.eict.practica3.entidades.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Integer> {

    // that's it

    // add method to sort by last name
    public Estudiante findByMatricula(int id);

    public List<Estudiante> findAllByOrderByMatriculaAsc();

    public boolean existsByMatricula(int matricula);
}
