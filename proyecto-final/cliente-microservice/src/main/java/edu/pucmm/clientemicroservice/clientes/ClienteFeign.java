package edu.pucmm.clientemicroservice.clientes;

import edu.pucmm.clientemicroservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "SERVIDOR-PERIMETRAL")
public interface ClienteFeign {
//    USER-MICROSERVICE
    @GetMapping("/user/read/{id}")
    UserDto readByIdUser(@PathVariable int id);
    @GetMapping("/user/read/email")
    UserDto readByEmailUser(@RequestBody AuthenticationRequest authenticationRequest);
    @GetMapping("/user/read")
    List<UserDto> readAllUser();
    @GetMapping("/user/readByRole/{role}")
    String[] readAllByRoleUser(@PathVariable String role);
    @PostMapping("/user/create")
    UserDto createUser(@RequestBody UserDto user);
    @PutMapping("/user/update")
    UserDto updateUser(@RequestBody UserDto user);
    @DeleteMapping("/user/delete/{id}")
    void deleteUser(@PathVariable int id);

//  PAQUETES
    @GetMapping("/pack/read/{id}")
    PackDto readByIdPack(@PathVariable Integer id);

    @GetMapping("/pack/read")
    List<PackDto> readAllPack();

    @PostMapping("/pack/create")
    PackDto createPack(@RequestBody PackDto pack);

    @PutMapping("/pack/update")
    PackDto updatePack(@RequestBody PackDto pack);

    @DeleteMapping("/pack/delete/{id}")
    void deletePack(@PathVariable Integer id);

//  PURCHASE
    @GetMapping("/purchase/read/{id}")
    PurchaseDto readPurchaseById(@PathVariable Integer id);
    @GetMapping("/purchase/read/user/{id}")
    List<PurchaseDto> readByUserPurchaseId(@PathVariable Integer id);

    @GetMapping("/purchase/read")
    List<PurchaseDto> readAllPurchase();

    @GetMapping("/purchase/read/false")
    List<PurchaseDto> readAllFalse();

    @PostMapping("/purchase/create")
    ReporteDTO createPackPurchase(@RequestBody PurchaseDto packPurchases);

    @PutMapping("/purchase/update")
    PurchaseDto updatePackPurchase(@RequestBody PurchaseDto packPurchases);

    @PutMapping("/purchase/update/{packId}/{employeeId}")
    PurchaseDto updatePackPurchaseEmployee(@PathVariable Integer packId, @PathVariable Integer employeeId);
}
