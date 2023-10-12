package edu.pucmm.eict.practica3.controladores;

import edu.pucmm.eict.practica3.entidades.Estudiante;
import edu.pucmm.eict.practica3.servicios.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiantes")
public class EstudiantesControlador {

    private EstudianteService estudianteService;

    @Autowired
    public EstudiantesControlador(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // add mapping for "/list"
    @GetMapping("/list")
    public String estudianteList(Model model) {

        // get employees from data base
        List<Estudiante> estudiantes = estudianteService.findAll();

        // add to the spring model
        model.addAttribute("estudiantes", estudiantes);

        return "estudiantes/list-estudiantes";
    }

    // add mapping for "/add" to add new employees
    @GetMapping("/add")
    public String addEstudiante(Model model, @RequestParam Optional<Integer> id, RedirectAttributes redirectAttributes) {
        if(id.isEmpty()){
            return "estudiantes/estudiante-form";
        }else{
            Estudiante estudiante = estudianteService.findById(id.get());
            if(estudiante != null){
                model.addAttribute("datos", estudiante);
                return "estudiantes/estudiante-form";
            }
        }
        redirectAttributes.addFlashAttribute("msg", "Estudiante no existe :(");
        return "redirect:/estudiantes/list";
    }

    @PostMapping("/add")
    public String addEstudiantePost(Model model, @ModelAttribute Estudiante estudiante, @RequestParam Optional<Integer> id, RedirectAttributes redirectAttributes)
    {
        boolean f=estudianteService.existsByMatricula(estudiante.getMatricula());

        if (id.isEmpty()){
            if(f)
            {
                redirectAttributes.addFlashAttribute("msg", "Matricula ya existe :(");
            }else{
                Estudiante student = estudianteService.save(estudiante);
                if(student != null){
                    redirectAttributes.addFlashAttribute("msg", "Estudiante creado exitosamente :)");
                }else{
                    redirectAttributes.addFlashAttribute("msg", "No se pudo crear debido un error inesperado en el servidor :(");
                }
            }
            return "redirect:/estudiantes/add";
        }else{
            Estudiante temp = estudianteService.findById(id.get());
            if(temp != null) {
                if (f && !(temp.getMatricula() == estudiante.getMatricula())) {
                    redirectAttributes.addFlashAttribute("msg", "Matricula ya existe :(");
                } else {
                    temp.setNombre(estudiante.getNombre());
                    temp.setApellido(estudiante.getApellido());
                    temp.setTelefono(estudiante.getTelefono());
                    Estudiante student = estudianteService.save(temp);
                    if (student != null) {
                        redirectAttributes.addFlashAttribute("msg", "Estudiante editado exitosamente :)");
                    } else {
                        redirectAttributes.addFlashAttribute("msg", "No se pudo editar debido un error inesperado en el servidor :(");
                    }
                }
            }
            return "redirect:/estudiantes/add?id="+id.get();
        }
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {

        if(estudianteService.existsByMatricula(id)){
            estudianteService.deleteById(id);
            redirectAttributes.addFlashAttribute("msg", "Estudiante eliminado correctamente");
        }else{
            redirectAttributes.addFlashAttribute("msg", "Estudiante no existe :(");
        }

        // return to list
        return "redirect:/estudiantes/list";
    }
}
