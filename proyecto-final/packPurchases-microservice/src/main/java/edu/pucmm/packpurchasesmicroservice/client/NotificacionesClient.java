package edu.pucmm.packpurchasesmicroservice.client;

import edu.pucmm.packpurchasesmicroservice.dto.EmailDTO;
import edu.pucmm.packpurchasesmicroservice.dto.EmailFileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "NOTIFICACIONES-MICROSERVICE")
public interface NotificacionesClient {

    @PostMapping("/notificaciones/sendMessage")
    ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO);

    @PostMapping("/notificaciones/sendMessageFile")
    ResponseEntity<?> receiveRequestEmailWithFile(@RequestBody EmailFileDTO emailFileDTO);
}
