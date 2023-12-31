package edu.pucmm.packmicroservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackService {

    private final PackRepository packRepository;

    public Pack savePack(Pack pack){
        return packRepository.save(pack);
    }

    public void deletePack(int id){
        packRepository.deleteById(id);
    }

    public List<Pack> getAll(){
        return packRepository.findAll();
    }

    public Optional<Pack> findById(int id){
        return packRepository.findById(id);
    }

    public boolean existsById(int id){return packRepository.existsById(id);}
}
