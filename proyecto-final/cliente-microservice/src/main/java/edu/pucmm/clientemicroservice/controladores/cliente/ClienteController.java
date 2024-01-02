package edu.pucmm.clientemicroservice.controladores.cliente;

import edu.pucmm.clientemicroservice.clientes.ClienteFeign;
import edu.pucmm.clientemicroservice.dto.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAuthority('cliente')")
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteFeign clienteFeign;

    @GetMapping(value="/pack")
    public ModelAndView indexClientePack(Authentication authentication) {

        List<PackDto> paquetes = clienteFeign.readAllPack();

        HashMap<String, Object> params = new HashMap<>();
        params.put("paquetes", paquetes);
        params.put("logged", clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), "")));

        return new ModelAndView("clientePacks", params);
    }

    @GetMapping("/purchase")
    public ModelAndView readAllMyPurchase(Authentication authentication)
    {
        HashMap<String, Object> params = new HashMap<>();
        UserDto userDto = clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), ""));
        params.put("logged", userDto);
        params.put("compras", clienteFeign.readByUserPurchaseId(userDto.getId()));
        return new ModelAndView("purchases", params);
    }

    @GetMapping("/purchase/{id}")
    public ModelAndView crearCompraPaqueteGet(Authentication authentication, @PathVariable int id, RedirectAttributes redirectAttributes)
    {
        try{
            HashMap<String, Object> params = new HashMap<>();
            UserDto userDto = clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), ""));
            params.put("logged", userDto);
            params.put("datos", clienteFeign.readByIdPack(id));
            return new ModelAndView("crearCompra", params);
        } catch (FeignException e) {
            String errorMessage = e.getMessage();
            int startIndex = errorMessage.indexOf("\"message\":\"");
            startIndex += "\"message\":\"".length();
            int endIndex = errorMessage.indexOf("\"", startIndex);
            String errorMessageValue = errorMessage.substring(startIndex, endIndex);
            redirectAttributes.addFlashAttribute("msg", errorMessageValue);
        }
        return new ModelAndView("redirect:/cliente/pack");
    }

    @GetMapping("/procesarCompraPaypal")
    public ModelAndView crearCompraPaquetePost(Model model, @RequestParam Map<String,String> params, Authentication authentication, RedirectAttributes redirectAttributes)
    {
        params.forEach((k,v) -> {
            System.out.printf("[%s] = %s%n", k, v);
        });
        try{
            UserDto userDto = clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), ""));
            PurchaseDto purchase = new PurchaseDto();
            purchase.setIdCliente(userDto.getId());
            purchase.setIdPaquete(Integer.parseInt(params.get("invoice")));
            purchase.setFechaEvento(params.get("custom"));
            ReporteDTO reporteDTO = clienteFeign.createPackPurchase(purchase);
            String base64 = reporteDTO.getRawFile();
            String fileName = reporteDTO.getNombreArchivo();

            redirectAttributes.addFlashAttribute("pdfBase64", base64);
            redirectAttributes.addFlashAttribute("fileName", fileName);
//            ReporteDTO reporteDTO = clienteFeign.createPackPurchase(purchase);
//            byte[] bytes = Base64.decodeBase64(reporteDTO.getRawFile());
//
//            String mimeType = "application/pdf";
//            response.setContentType(mimeType);
//            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", reporteDTO.getNombreArchivo()));
//            response.setContentLength(bytes.length);
//
//            try (OutputStream outputStream = response.getOutputStream()) {
//                outputStream.write(bytes);
//                outputStream.flush();
//            }
            redirectAttributes.addFlashAttribute("msgSuccess", "Paquete creado exitosamente :)");
        } catch (FeignException e) {
            String errorMessage = e.getMessage();
            int startIndex = errorMessage.indexOf("\"message\":\"");
            startIndex += "\"message\":\"".length();
            int endIndex = errorMessage.indexOf("\"", startIndex);
            String errorMessageValue = errorMessage.substring(startIndex, endIndex);
            redirectAttributes.addFlashAttribute("msg", errorMessageValue);
        }
        return new ModelAndView("redirect:/cliente/purchase");
    }
}
