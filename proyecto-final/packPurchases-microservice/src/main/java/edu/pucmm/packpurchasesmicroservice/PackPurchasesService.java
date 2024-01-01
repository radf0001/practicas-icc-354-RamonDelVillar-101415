package edu.pucmm.packpurchasesmicroservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackPurchasesService {
    private final PackPurchasesRepository packPurchasesRepository;

    public PackPurchases savePack(PackPurchases packPurchases){
        return packPurchasesRepository.save(packPurchases);
    }

//    public void deletePack(int id){
//        packPurchasesRepository.deleteById(id);
//    }

    public List<PackPurchases> getAll(){
        return packPurchasesRepository.findAll();
    }

    public List<PackPurchases> getAllFalse(){
        return packPurchasesRepository.findAllByEmpleadoIs(false);
    }

    public Optional<PackPurchases> findById(int id){
        return packPurchasesRepository.findById(id);
    }

//    public boolean existsById(int id){return packPurchasesRepository.existsById(id);}

    public List<PackPurchases> findAllByIdClienteOrIdEmpleado(int idUser){
        return packPurchasesRepository.findAllByIdClienteOrIdEmpleado(idUser, idUser);
    }
}
