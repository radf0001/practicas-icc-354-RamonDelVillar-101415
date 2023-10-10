package edu.pucmm.eict.practica3.servicios;

import edu.pucmm.eict.practica3.dao.EstudianteRepositorio;
import edu.pucmm.eict.practica3.entidades.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private EstudianteRepositorio estudianteRepository;

    @Autowired
    public EstudianteServiceImpl(EstudianteRepositorio estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public List<Estudiante> findAll() {
        return estudianteRepository.findAllByOrderByMatriculaAsc();
    }

    @Override
    public boolean existsByMatricula(int theId){
        return estudianteRepository.existsByMatricula(theId);
    };

    @Override
    public Estudiante findById(int theId) {
        Optional<Estudiante> result = estudianteRepository.findById(theId);

        Estudiante theEstudiante = null;

        if (result.isPresent()) {
            theEstudiante = result.get();
        }

        return theEstudiante;
    }

    @Override
    public Estudiante save(Estudiante theEstudiante) {
        return estudianteRepository.save(theEstudiante);
    }

    @Override
    public void deleteById(int theId) {
        estudianteRepository.deleteById(theId);
    }

}
