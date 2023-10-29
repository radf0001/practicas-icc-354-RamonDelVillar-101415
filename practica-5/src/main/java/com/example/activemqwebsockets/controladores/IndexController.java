package com.example.activemqwebsockets.controladores;

import com.example.activemqwebsockets.servicios.MiJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private MiJsonService miJsonService;

    @Autowired
    public IndexController(MiJsonService miJsonService) {
        this.miJsonService = miJsonService;
    }

    // add mapping for "/list"
    @GetMapping("/")
    public String index(Model model) {

        // add to the spring model
        model.addAttribute("cliente1", miJsonService.findAllByIdDispositivo(1));
        model.addAttribute("cliente2", miJsonService.findAllByIdDispositivo(2));

        return "/index";
    }


}
