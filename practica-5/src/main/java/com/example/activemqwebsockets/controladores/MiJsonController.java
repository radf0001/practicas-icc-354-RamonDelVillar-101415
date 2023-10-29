package com.example.activemqwebsockets.controladores;

import com.example.activemqwebsockets.entidades.MiJson;
import com.example.activemqwebsockets.servicios.MiJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MiJsonController {

    private MiJsonService miJsonService;

    @Autowired
    public MiJsonController(MiJsonService miJsonService) {
        this.miJsonService = miJsonService;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/notificacion_sensores")
    public MiJson greet(int idDispositivo) {
        MiJson miJson = new MiJson(idDispositivo);
        miJsonService.save(miJson);
        return miJson;
    }
}

