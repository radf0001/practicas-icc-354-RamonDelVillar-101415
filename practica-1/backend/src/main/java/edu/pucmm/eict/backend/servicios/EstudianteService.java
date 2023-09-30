package edu.pucmm.eict.backend.servicios;

import edu.pucmm.eict.backend.dtos.EstudianteDto;
import edu.pucmm.eict.backend.entidades.Estudiante;
import edu.pucmm.eict.backend.exceptions.AppException;
import edu.pucmm.eict.backend.mappers.EstudianteMapper;
import edu.pucmm.eict.backend.repositorios.EstudianteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepositorio estudianteRepositorio;
    private final EstudianteMapper estudianteMapper;
    public List<EstudianteDto> allEstudiantes(){
        List<Estudiante> all = estudianteRepositorio.findAll();
        return estudianteMapper.toEstudianteDtos(all);
    }

    public EstudianteDto getEstudiante(int matricula) {
        Estudiante estudiante = estudianteRepositorio.findById(matricula)
                .orElseThrow(() -> new AppException("Estudiante no encontrado", HttpStatus.NOT_FOUND));
        return estudianteMapper.toEstudianteDto(estudiante);
    }

    public EstudianteDto crearEstudiante(EstudianteDto estudianteDto) {
        Estudiante estudiante = estudianteMapper.toEstudiante(estudianteDto);
        Estudiante estudianteCreado = null;
        if(estudianteRepositorio.existsById(estudiante.getMatricula())){
           throw new AppException("Matricula ya existe", HttpStatus.CONFLICT);
        }else{
            estudianteCreado = estudianteRepositorio.save(estudiante);
        }
        return estudianteMapper.toEstudianteDto(estudianteCreado);
    }

    public EstudianteDto eliminarEstudiante(int matricula) {
        Estudiante estudiante = estudianteRepositorio.findById(matricula)
                .orElseThrow(() -> new AppException("Estudiante no encontrado", HttpStatus.NOT_FOUND));

        estudianteRepositorio.deleteById(matricula);

        return estudianteMapper.toEstudianteDto(estudiante);
    }

    public EstudianteDto actualizarEstudiante(int matricula, EstudianteDto estudianteDto) {
        Estudiante estudiante = estudianteRepositorio.findById(matricula)
                .orElseThrow(() -> new AppException("Estudiante no encontrado", HttpStatus.NOT_FOUND));

        estudianteMapper.updateEstudiante(estudiante, estudianteMapper.toEstudiante(estudianteDto));

        Estudiante estudianteActualizado = estudianteRepositorio.save(estudiante);

        return estudianteMapper.toEstudianteDto(estudianteActualizado);
    }
}
