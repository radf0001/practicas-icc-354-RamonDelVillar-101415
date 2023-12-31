package edu.pucmm.notificacionesmicroservice.controller;
import edu.pucmm.notificacionesmicroservice.domain.EmailDTO;
import edu.pucmm.notificacionesmicroservice.domain.EmailFileDTO;
import edu.pucmm.notificacionesmicroservice.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
public class MailController {

    private final IEmailService emailService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO){

        System.out.println("Mensaje Recibido " + emailDTO);

        emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@RequestBody EmailFileDTO emailFileDTO){

        try {
            // Decodificar base64 a bytes
            byte[] bytes = Base64.decodeBase64(emailFileDTO.getFile().getRawFile());
            File archivoTemporal = File.createTempFile(emailFileDTO.getFile().getNombreArchivo(), ".pdf");
            try (FileOutputStream fos = new FileOutputStream(archivoTemporal)) {
                fos.write(bytes);
            }

            emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), archivoTemporal);


            Map<String, String> response = new HashMap<>();
            response.put("estado", "Enviado");
            response.put("archivo", emailFileDTO.getFile().getNombreArchivo());

            return ResponseEntity.ok(response);

        } catch (Exception e){
            throw new RuntimeException("Error al enviar el Email con el archivo. " + e.getMessage());
        }
    }
}
