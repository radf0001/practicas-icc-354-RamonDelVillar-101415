package edu.pucmm.eict.practica4.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage(Authentication authentication) {
        if(authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"))){
            return "redirect:/admin/";
        }else if(authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_USER"))){
            return "redirect:/user/";
        }
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error, HttpSession session) {
        HashMap<String, Object> params = new HashMap<>();
        if(error.isPresent()){
            params.put("error", error);
        }
        String idSesion = session.getId();
        params.put("port", String.format("%s - ID Sesion: %s%n", environment.getProperty("local.server.port"), idSesion));

        return new ModelAndView("login", params);
    }

}
