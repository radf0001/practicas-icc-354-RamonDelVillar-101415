package edu.pucmm.packpurchasesmicroservice.utils;


import edu.pucmm.packpurchasesmicroservice.PackPurchases;
import edu.pucmm.packpurchasesmicroservice.dto.PackDto;
import edu.pucmm.packpurchasesmicroservice.dto.ReporteDTO;
import edu.pucmm.packpurchasesmicroservice.dto.UserDto;
import net.sf.jasperreports.engine.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Utilidades {
    public static ReporteDTO generarPdf(PackPurchases packPurchases, UserDto userDto, PackDto packDto) {
        try{
            String filePath = "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "templates" + File.separator +
                    "report" + File.separator +
                    "Report.jrxml";


            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            Map<String, Object> parameters= new HashMap<>();
            parameters.put("voucher_id", packPurchases.getId());
            parameters.put("current_date", formatter.format(localDateTime));
            parameters.put("AmountPaid", String.valueOf(packDto.getPrecio()));
            parameters.put("payment_method", "PayPal");
            parameters.put("pack_name", packDto.getNombre());
            parameters.put("client_email", userDto.getEmail());
            parameters.put("imageDir", "classpath:/static/images/");

            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            File archivoPdf;
            FileOutputStream fos;

            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            archivoPdf = File.createTempFile("Comprobante", ".pdf");
            fos = new FileOutputStream(archivoPdf);
            fos.write(bytes); // Escribe el arreglo de bytes en el archivo
            fos.close();

            byte[] fileContent = Files.readAllBytes(archivoPdf.toPath());
            return new ReporteDTO("Comprobante", "application/pdf", Base64.getEncoder().encodeToString(fileContent));
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }
}