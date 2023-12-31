package edu.pucmm.clientemicroservice.controladores.cliente;

import edu.pucmm.clientemicroservice.clientes.ClienteFeign;
import edu.pucmm.clientemicroservice.dto.AuthenticationRequest;
import edu.pucmm.clientemicroservice.dto.PackDto;
import edu.pucmm.clientemicroservice.dto.PurchaseDto;
import edu.pucmm.clientemicroservice.dto.UserDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

        return new ModelAndView("packs", params);
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

    @PostMapping("/purchase/create-update/")
    public ModelAndView crearCompraPaquetePost(@ModelAttribute PurchaseDto purchase, RedirectAttributes redirectAttributes)
    {
        try{
            clienteFeign.createPackPurchase(purchase);
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
