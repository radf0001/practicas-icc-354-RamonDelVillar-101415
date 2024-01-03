package edu.pucmm.usersmicroservice.client;

import edu.pucmm.usersmicroservice.dto.EmailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICACIONES-MICROSERVICE")
public interface NotificacionesClient {

    @PostMapping("/notificaciones/sendMessage")
    ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO);
}
