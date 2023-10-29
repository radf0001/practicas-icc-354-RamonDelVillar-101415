package com.example.activemqwebsockets.entidades;

import com.google.gson.Gson;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name="mijson")
public class MiJson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fechaGeneracion;
    private int idDispositivo;
    private int temperatura;
    private int humedad;

    public MiJson(int idDispositivo) {
        this.fechaGeneracion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.idDispositivo = idDispositivo;
        this.temperatura = new Random().nextInt(50+10)-10;
        this.humedad = new Random().nextInt(100);
    }

    public MiJson() {
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, MiJson.class);
    }
}
