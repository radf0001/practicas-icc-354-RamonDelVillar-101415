package edu.pucmm.eict.backend.mappers;

import edu.pucmm.eict.backend.dtos.EstudianteDto;
import edu.pucmm.eict.backend.entidades.Estudiante;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {
    Estudiante toEstudiante(EstudianteDto estudianteDto);

    EstudianteDto toEstudianteDto(Estudiante estudiante);

    List<EstudianteDto> toEstudianteDtos(List<Estudiante> estudiantes);

    void updateEstudiante(@MappingTarget Estudiante target, Estudiante source);
}
