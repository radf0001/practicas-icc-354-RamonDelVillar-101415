package edu.pucmm.eict.backend.controladores;

import edu.pucmm.eict.backend.dtos.EstudianteDto;
import edu.pucmm.eict.backend.servicios.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class EstudiantesControlador {

    private final EstudianteService estudianteService;

    @GetMapping("/estudiantes")
    public ResponseEntity<List<EstudianteDto>> allEstudiantes(){
        return ResponseEntity.ok(estudianteService.allEstudiantes());
    }

    @GetMapping("/estudiantes/{matricula}")
    public ResponseEntity<EstudianteDto> getEstudiante(@PathVariable int matricula){
        return ResponseEntity.ok(estudianteService.getEstudiante(matricula));
    }

    @PostMapping("/estudiantes")
    public ResponseEntity<EstudianteDto> crearEstudiante(@Valid @RequestBody EstudianteDto estudianteDto){
        EstudianteDto estudianteCreado = estudianteService.crearEstudiante(estudianteDto);
        return ResponseEntity.created(URI.create("/estudiantes/" + estudianteCreado.getMatricula())).body(estudianteCreado);
    }

    @DeleteMapping("/estudiantes/{matricula}")
    public ResponseEntity<EstudianteDto> eliminarEstudiante(@PathVariable int matricula){
        return ResponseEntity.ok(estudianteService.eliminarEstudiante(matricula));
    }

    @PutMapping("/estudiantes/{matricula}")
    public ResponseEntity<EstudianteDto> actualizarEstudiante(@PathVariable int matricula, @Valid @RequestBody EstudianteDto estudianteDto){
        return ResponseEntity.ok(estudianteService.actualizarEstudiante(matricula, estudianteDto));
    }

}
