package edu.pucmm.clientemicroservice.controladores;

import edu.pucmm.clientemicroservice.clientes.ClienteFeign;
import edu.pucmm.clientemicroservice.dto.AuthenticationRequest;
import edu.pucmm.clientemicroservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final ClienteFeign clienteFeign;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex(Authentication authentication) {
        UserDto userDto = clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), ""));
        if(userDto.getRole().equalsIgnoreCase("admin")){
            return new ModelAndView("redirect:/admin/user");
        }else if(userDto.getRole().equalsIgnoreCase("empleado")){
            return new ModelAndView("redirect:/empleado/free");
        }
        return new ModelAndView("redirect:/cliente/pack");
    }

}

