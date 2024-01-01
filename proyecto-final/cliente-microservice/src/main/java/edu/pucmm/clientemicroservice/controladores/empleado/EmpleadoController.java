package edu.pucmm.clientemicroservice.controladores.empleado;

import edu.pucmm.clientemicroservice.clientes.ClienteFeign;
import edu.pucmm.clientemicroservice.dto.AuthenticationRequest;
import edu.pucmm.clientemicroservice.dto.PackDto;
import edu.pucmm.clientemicroservice.dto.PurchaseDto;
import edu.pucmm.clientemicroservice.dto.UserDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('empleado')")
@RequestMapping("/empleado")
@RequiredArgsConstructor
public class EmpleadoController {
    private final ClienteFeign clienteFeign;

    @GetMapping(value="/free")
    public ModelAndView indexEmpleadoPack(Authentication authentication) {

        UserDto userDto = clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), ""));
        List<PurchaseDto> compras = clienteFeign.readAllFalse();

        HashMap<String, Object> params = new HashMap<>();
        params.put("compras", compras);
        params.put("logged", userDto);

        return new ModelAndView("empleadoTrabajosDisponibles", params);
    }

    @GetMapping(value = "/assign/{id}")
    public ModelAndView asignarTrabajo(@PathVariable int id, RedirectAttributes redirectAttributes, Authentication authentication){
        try{
            UserDto userDto = clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), ""));
            clienteFeign.updatePackPurchaseEmployee(id, userDto.getId());
            redirectAttributes.addFlashAttribute("msgSuccess", "Trabajo asignado correctamente :)");
        } catch (FeignException e) {
            String errorMessage = e.getMessage();
            int startIndex = errorMessage.indexOf("\"message\":\"");
            startIndex += "\"message\":\"".length();
            int endIndex = errorMessage.indexOf("\"", startIndex);
            String errorMessageValue = errorMessage.substring(startIndex, endIndex);
            redirectAttributes.addFlashAttribute("msg", errorMessageValue);
        }
        return new ModelAndView("redirect:/empleado/free");
    }

    @GetMapping(value="/mine")
    public ModelAndView indexEmpleadoMisTrabajos(Authentication authentication) {

        UserDto userDto = clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), ""));
        List<PurchaseDto> compras = clienteFeign.readByUserPurchaseId(userDto.getId());

        HashMap<String, Object> params = new HashMap<>();
        params.put("compras", compras);
        params.put("logged", userDto);

        return new ModelAndView("empleadoVerMisTrabajos", params);
    }
}
