package com.example.activemqwebsockets.servicios;

import com.example.activemqwebsockets.entidades.MiJson;

import java.util.List;

public interface MiJsonService {

    public List<MiJson> findAllByIdDispositivo(int idDispositivo);

    public MiJson save(MiJson miJson);

}
