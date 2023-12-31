package edu.pucmm.packmicroservice;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pack")
@RequiredArgsConstructor
public class PackController {
    private final PackService packService;


    // Endpoint para leer un paquete especifico, primero se verifica si existe un paquete con ese id en la base de datos
    @GetMapping("/read/{id}")
    public Pack readById(@PathVariable Integer id){
        Optional<Pack> pack = packService.findById(id);
        if(pack.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe un paquete con este id");
        }
        return pack.get();
    }

    // Endpoint una lista de todos los paquetes de la base de datos
    @GetMapping("/read")
    public List<Pack> readAll(){
        try{
            return packService.getAll();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }

    // Endpoint para crear paquete, primero se hace que el id sea 0, para que no se pueda editar en este endpoint y luego
    // se intenta salvar en la base de datos, tenemos 2 catch por si se dejan campos vacios o si ocurre un error inesperado
    @PostMapping("/create")
    public Pack createPack(@RequestBody Pack pack){
        pack.setId(0);
        try {
            return packService.savePack(pack);
        }catch (ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se permiten campos nulos o vacios");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }

    // Endpoint para editar paquete, primero se verifica si existe un paquete con el id y luego se intenta salvar en la base de datos
    // tenemos 2 catch por si se dejan campos vacios o si ocurre un error inesperado
    @PutMapping("/update")
    public Pack updatePack(@RequestBody Pack pack){
        Optional<Pack> pack1 = packService.findById(pack.getId());
        if(pack1.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe un paquete con este id");
        }else{
            try{
                return packService.savePack(pack);
            }catch (TransactionSystemException e){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "No se permiten campos nulos o vacios");
            }catch (Exception e){
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
            }
        }
    }

    // Endpoint para eliminar paquete, primero se verifica si existe un paquete con el id y luego se intenta eliminar en la base de datos
    // tenemos 1 catch por si ocurre un error inesperado
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id){
        if(!packService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe un paquete con este id");
        }else{
            try{
                packService.deletePack(id);
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
            }
        }
    }
}
