package com.example.activemqwebsockets.servicios;

import com.example.activemqwebsockets.entidades.MiJson;
import com.example.activemqwebsockets.repositorios.MiJsonRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiJsonServiceImpl implements MiJsonService {

    private MiJsonRepositorio miJsonRepository;

    @Autowired
    public MiJsonServiceImpl(MiJsonRepositorio miJsonRepository) {
        this.miJsonRepository = miJsonRepository;
    }

    @Override
    public List<MiJson> findAllByIdDispositivo(int idDispositivo) {
        return miJsonRepository.findAllByIdDispositivo(idDispositivo);
    }

    @Override
    public MiJson save(MiJson miJson){
        return miJsonRepository.save(miJson);
    };
}
