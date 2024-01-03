package edu.pucmm.usersmicroservice;

import edu.pucmm.usersmicroservice.client.NotificacionesClient;
import edu.pucmm.usersmicroservice.dto.EmailDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NotificacionesClient notificacionesClient;

    // Endpoint devuelve un usuario por id y si no existe en la base de datos devuelve un error 404 con un mensaje de que no existe el usuario
    @GetMapping("/read/{id}")
    public User readById(@PathVariable int id){
        Optional<User> user = userService.findById(id);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe un usuario con este id");
        }
        return user.get();
    }

    // Endpoint devuelve un usuario por email y si no existe en la base de datos devuelve un error 404 con un mensaje de que no existe el usuario
    @GetMapping("/read/email")
    public User readByEmail(@RequestBody AuthenticationRequest authenticationRequest){
        Optional<User> user = userService.findByEmail(authenticationRequest.getEmail());
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe un usuario con este id");
        }
        return user.get();
    }

    // Endpoint una lista de todos los usuarios den la base de datos
    @GetMapping("/read")
    public List<User> readAll(){
        try{
            return userService.getAll();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }

    // Endpoint una lista de todos los usuarios den la base de datos
    @GetMapping("/readByRole/{role}")
    public String[] readAllByRole(@PathVariable String role){
        try{
            return userService.findAllEmailsByRole(role);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }

    // Endpoint para crear usuario, se pone en el id en 0 para que no se pueda editar en este endpoint,
    // se verifica si existe un usuario con ese email (id) y luego se intenta salvar el usario en la base de
    // de datos, tenemos 2 catch por si se dejan campos vacios o si ocurre un error inesperado
    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        user.setId(0);
        user.setEmail(user.getEmail().toLowerCase());
        if (!userService.existsByEmail(user.getEmail())) {
            try {
                User userCreate =  userService.saveUser(user);
                notificacionesClient.receiveRequestEmail(new EmailDTO(new String[]{userCreate.getEmail()}, "Su Usuario y Clave", String.format("Usuario: %s\n Clave: %s\n", userCreate.getEmail(), userCreate.getPassword())));
                return userCreate;
            }catch (ConstraintViolationException e){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "No se permiten campos nulos o vacios");
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con este email");
        }
    }

    // Endpoint para editar usuario, primero se verifica si existe un usuario con ese email (id), luego se verifica que no exista
    // otro usuario con el mismo email y si existe que sea el mismo usuario luego se intenta salvar el usario en la base de datos,
    // tenemos 2 catch por si se dejan campos vacios o si ocurre un error inesperado
    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        user.setEmail(user.getEmail().toLowerCase());
        Optional<User> user1 = userService.findById(user.getId());
        if(user1.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe un usuario con este id");
        }else{
            if(user1.get().getEmail().equalsIgnoreCase(user.getEmail()) || !userService.existsByEmail(user.getEmail())){
                try{
                    return userService.saveUser(user);
                }catch (TransactionSystemException e){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "No se permiten campos nulos o vacios");
                }catch (Exception e){
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
                }
            }else{
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya Existe un usuario con este email");
            }
        }
    }

    // Endpoint para eliminar usuario, primero se verifica si existe un usuario con ese email (id) y luego se intenta eliminar el usario en la base de
    // de datos, tenemos 1 catch por si ocurre un error inesperado
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id){
        if(!userService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe un usuario con este id");
        }else{
            try{
                userService.deleteUser(id);
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
            }
        }
    }
}
