package edu.pucmm.eict.practica3.servicios;

import edu.pucmm.eict.practica3.entidades.Estudiante;

import java.util.List;

public interface EstudianteService {

    public List<Estudiante> findAll();

    public Estudiante findById(int theId);

    public boolean existsByMatricula(int theId);

    public Estudiante save(Estudiante theEstudiante);

    public void deleteById(int theId);

}
