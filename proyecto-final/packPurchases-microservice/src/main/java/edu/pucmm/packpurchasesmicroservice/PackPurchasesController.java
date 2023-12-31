package edu.pucmm.packpurchasesmicroservice;

import edu.pucmm.packpurchasesmicroservice.client.NotificacionesClient;
import edu.pucmm.packpurchasesmicroservice.client.PackClient;
import edu.pucmm.packpurchasesmicroservice.client.UserClient;
import edu.pucmm.packpurchasesmicroservice.dto.*;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import edu.pucmm.packpurchasesmicroservice.utils.Utilidades;

@RestController
@RequestMapping(value= "/packPurchase", produces="application/json")
@RequiredArgsConstructor
public class PackPurchasesController {

    private final PackPurchasesService packPurchasesService;
    private final UserClient userClient;
    private final PackClient packClient;
    private final NotificacionesClient notificacionesClient;

    // Endpoint devuelve una compra de paquete por id y si no existe en la base de datos devuelve un error 404 con un mensaje de que no existe la compra
    @GetMapping("/read/{id}")
    public PackPurchases readById(@PathVariable Integer id){
        Optional<PackPurchases> pack = packPurchasesService.findById(id);
        if(pack.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe una compra de paquete con este id");
        }
        return pack.get();
    }

    // Endpoint una lista de todos las compras de paquetes por usuario de la base de datos
    @GetMapping("/read/user/{id}")
    public List<PackPurchases> readByUserId(@PathVariable Integer id){
        try{
            return packPurchasesService.findAllByIdClienteOrIdEmpleado(id);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }

    // Endpoint una lista de todas las compras de paquetes de la base de datos
    @GetMapping("/read")
    public List<PackPurchases> readAll(){
        try{
            return packPurchasesService.getAll();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }

    // Endpoint para crear compra, primero se hace que el id sea 0, para que no se pueda editar en este endpoint y luego se intenta salvar en la base de
    // de datos, tenemos 2 catch por si se dejan campos vacios o si ocurre un error inesperado
    @PostMapping("/create")
    public ReporteDTO createPackPurchase(@RequestBody PackPurchases packPurchases){
        packPurchases.setId(0);
        try {
            packPurchasesService.savePack(packPurchases);
            UserDto userDto = userClient.findUserById(packPurchases.getIdCliente());
            PackDto packDto = packClient.findPackById(packPurchases.getIdPaquete());
            ReporteDTO reporteDTO = Utilidades.generarPdf(packPurchases, userDto, packDto);
            String[] empleados = userClient.readAllByRole("empleado");
            notificacionesClient.receiveRequestEmail(new EmailDTO(empleados, "Trabajo Disponible", String.format("Descripcion de Trabajo:\n Cliente: %s\n Paquete: %s\n Fecha: %s\n Precio: %s\n", userDto.getEmail(), packDto.getNombre(), packPurchases.getFechaEvento(), packDto.getPrecio())));
            notificacionesClient.receiveRequestEmailWithFile(new EmailFileDTO(new String[]{userDto.getEmail()}, "Comprobante de Pago", "PDF de Comprobante", reporteDTO));
            return reporteDTO;
        }catch (ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se permiten campos nulos o vacios");
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
        }
    }

    // Endpoint para editar compra, primero se verifica si existe una compra con ese id y luego se intenta salvar la compra en la base de
    // de datos, tenemos 2 catch por si se dejan campos vacios o si ocurre un error inesperado
    @PutMapping("/update")
    public PackPurchases updatePackPurchase(@RequestBody PackPurchases packPurchases){
        Optional<PackPurchases> packPurchases1 = packPurchasesService.findById(packPurchases.getId());
        if(packPurchases1.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe una compra de paquete con este id");
        }else{
            try{
                return packPurchasesService.savePack(packPurchases);
            }catch (TransactionSystemException e){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "No se permiten campos nulos o vacios");
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
            }
        }
    }

    // Endpoint para editar compra, primero se verifica si existe una compra con ese id y luego se intenta salvar la compra en la base de
    // de datos, tenemos 2 catch por si se dejan campos vacios o si ocurre un error inesperado
    @PutMapping("/update/{packId}/{employeeId}")
    public PackPurchases updatePackPurchaseEmployee(@PathVariable Integer packId, @PathVariable Integer employeeId){
        Optional<PackPurchases> packPurchases = packPurchasesService.findById(packId);
        if(packPurchases.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe una compra de paquete con este id");
        }else{
            try{
                packPurchases.get().setIdEmpleado(employeeId);
                packPurchases.get().setEmpleado(true);
                return packPurchasesService.savePack(packPurchases.get());
            }catch (TransactionSystemException e){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "No se permiten campos nulos o vacios");
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
            }
        }
    }
}
