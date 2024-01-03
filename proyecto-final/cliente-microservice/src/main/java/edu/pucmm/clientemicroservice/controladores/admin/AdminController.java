package edu.pucmm.clientemicroservice.controladores.admin;

import edu.pucmm.clientemicroservice.clientes.ClienteFeign;
import edu.pucmm.clientemicroservice.dto.AuthenticationRequest;
import edu.pucmm.clientemicroservice.dto.PackDto;
import edu.pucmm.clientemicroservice.dto.UserDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@RestController
@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ClienteFeign clienteFeign;

//  USER
    @GetMapping(value="/user")
    public ModelAndView indexAdmin(Authentication authentication) {
        List<UserDto> usuarios = clienteFeign.readAllUser();

        HashMap<String, Object> params = new HashMap<>();
        params.put("usuarios", usuarios);
        params.put("logged", clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), "")));

        return new ModelAndView("users", params);
    }

    @GetMapping("/user/create-update")
    public ModelAndView crud(Authentication authentication, @RequestParam Optional<Integer> id, RedirectAttributes redirectAttributes)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("logged", clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), "")));
        if(id.isEmpty()){
            return new ModelAndView("crearUsuario", params);
        }else{
            try{
                UserDto usuario = clienteFeign.readByIdUser(id.get());
                if(usuario != null){
                    params.put("datos", usuario);
                    String rol = usuario.getRole();
                    params.put("rol", rol);
                    return new ModelAndView("crearUsuario", params);
                }
            } catch (FeignException e) {
                String errorMessage = e.getMessage();
                int startIndex = errorMessage.indexOf("\"message\":\"");
                startIndex += "\"message\":\"".length();
                int endIndex = errorMessage.indexOf("\"", startIndex);
                String errorMessageValue = errorMessage.substring(startIndex, endIndex);
                redirectAttributes.addFlashAttribute("msg", errorMessageValue);
            }
        }
        return new ModelAndView("redirect:/admin/user");
    }

    @PostMapping("/user/create-update")
    public ModelAndView crearUsuarioPost(@ModelAttribute UserDto usuario,Authentication authentication, RedirectAttributes redirectAttributes) {
        try{
            if(usuario.getId() == 0){
                usuario = clienteFeign.createUser(usuario);
                redirectAttributes.addFlashAttribute("msgSuccess", "Usuario creado exitosamente :)");
            }else{
                usuario = clienteFeign.updateUser(usuario);
                if(usuario.getEmail().equalsIgnoreCase(authentication.getName())){
                    return new ModelAndView("redirect:/logout");
                }
                redirectAttributes.addFlashAttribute("msgSuccess", "Usuario editado exitosamente :)");
            }
            return new ModelAndView("redirect:/admin/user/create-update?id="+usuario.getId());
        }catch (FeignException e) {
            String errorMessage = e.getMessage();
            int startIndex = errorMessage.indexOf("\"message\":\"");
            startIndex += "\"message\":\"".length();
            int endIndex = errorMessage.indexOf("\"", startIndex);
            String errorMessageValue = errorMessage.substring(startIndex, endIndex);
            redirectAttributes.addFlashAttribute("msg", errorMessageValue);
        }
        if(usuario.getId() == 0){
            return new ModelAndView("redirect:/admin/user/create-update");
        }else{
            return new ModelAndView("redirect:/admin/user/create-update?id="+usuario.getId());
        }
    }

    @GetMapping(value = "/user/delete/{id}")
    public ModelAndView borrar(Authentication authentication, @PathVariable int id, RedirectAttributes redirectAttributes) {
        try{
            UserDto userDto = clienteFeign.readByIdUser(id);
            if(!authentication.getName().equalsIgnoreCase(userDto.getEmail())){
                clienteFeign.deleteUser(id);
                redirectAttributes.addFlashAttribute("msgSuccess", "Usuario eliminado exitosamente :)");
            }else{
                redirectAttributes.addFlashAttribute("msg", "No puede eliminar su Usuario :(");
            }
        }catch (FeignException e) {
            String errorMessage = e.getMessage();
            int startIndex = errorMessage.indexOf("\"message\":\"");
            startIndex += "\"message\":\"".length();
            int endIndex = errorMessage.indexOf("\"", startIndex);
            String errorMessageValue = errorMessage.substring(startIndex, endIndex);
            redirectAttributes.addFlashAttribute("msg", errorMessageValue);
        }
        return new ModelAndView("redirect:/admin/user");
    }

//  PACK
    @GetMapping(value="/pack")
    public ModelAndView indexAdminPack(Authentication authentication) {

        List<PackDto> paquetes = clienteFeign.readAllPack();

        HashMap<String, Object> params = new HashMap<>();
        params.put("paquetes", paquetes);
        params.put("logged", clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), "")));

        return new ModelAndView("packs", params);
    }

    @GetMapping("/pack/create-update")
    public ModelAndView crudPack(Authentication authentication, @RequestParam Optional<Integer> id, RedirectAttributes redirectAttributes)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("logged", clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), "")));
        if(id.isEmpty()){
            return new ModelAndView("crearPaquete", params);
        }else{
            try{
                PackDto pack = clienteFeign.readByIdPack(id.get());
                if(pack != null){
                    params.put("datos", pack);
                    return new ModelAndView("crearPaquete", params);
                }
            } catch (FeignException e) {
                String errorMessage = e.getMessage();
                int startIndex = errorMessage.indexOf("\"message\":\"");
                startIndex += "\"message\":\"".length();
                int endIndex = errorMessage.indexOf("\"", startIndex);
                String errorMessageValue = errorMessage.substring(startIndex, endIndex);
                redirectAttributes.addFlashAttribute("msg", errorMessageValue);
            }
        }
        return new ModelAndView("redirect:/admin/pack");
    }

    @PostMapping("/pack/create-update")
    public ModelAndView crearPaquetePost(@ModelAttribute PackDto pack, RedirectAttributes redirectAttributes)
    {
        try{
            if(pack.getId() == 0){
                pack = clienteFeign.createPack(pack);
                redirectAttributes.addFlashAttribute("msgSuccess", "Paquete creado exitosamente :)");
            }else{
                pack = clienteFeign.updatePack(pack);
                redirectAttributes.addFlashAttribute("msgSuccess", "Paquete editado exitosamente :)");
            }
            return new ModelAndView("redirect:/admin/pack/create-update?id="+pack.getId());
        } catch (FeignException e) {
            String errorMessage = e.getMessage();
            int startIndex = errorMessage.indexOf("\"message\":\"");
            startIndex += "\"message\":\"".length();
            int endIndex = errorMessage.indexOf("\"", startIndex);
            String errorMessageValue = errorMessage.substring(startIndex, endIndex);
            redirectAttributes.addFlashAttribute("msg", errorMessageValue);
        }
        if(pack.getId() == 0){
            return new ModelAndView("redirect:/admin/pack/create-update");
        }else{
            return new ModelAndView("redirect:/admin/pack/create-update?id="+pack.getId());
        }
    }

    @GetMapping(value = "/pack/delete/{id}")
    public ModelAndView borrarPack(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            clienteFeign.deletePack(id);
            redirectAttributes.addFlashAttribute("msgSuccess", "Paquete eliminado exitosamente :)");
        } catch (FeignException e) {
            String errorMessage = e.getMessage();
            int startIndex = errorMessage.indexOf("\"message\":\"");
            startIndex += "\"message\":\"".length();
            int endIndex = errorMessage.indexOf("\"", startIndex);
            String errorMessageValue = errorMessage.substring(startIndex, endIndex);
            redirectAttributes.addFlashAttribute("msg", errorMessageValue);
        }
        return new ModelAndView("redirect:/admin/pack");
    }

//  PURCHASE
    @GetMapping("/purchase")
    public ModelAndView readAllPurchase(Authentication authentication, @RequestParam Optional<Integer> id)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("logged", clienteFeign.readByEmailUser(new AuthenticationRequest(authentication.getName(), "")));
        if(id.isEmpty()){
            params.put("compras", clienteFeign.readAllPurchase());
        }else{
            params.put("compras", clienteFeign.readByUserPurchaseId(id.get()));
        }
        return new ModelAndView("adminPurchases", params);
    }
}
