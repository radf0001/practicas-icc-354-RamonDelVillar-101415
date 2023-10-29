package com.example.activemqwebsockets.repositorios;

import com.example.activemqwebsockets.entidades.MiJson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MiJsonRepositorio extends JpaRepository<MiJson, Integer> {

    List<MiJson> findAllByIdDispositivo(int idDispositivo);
}
