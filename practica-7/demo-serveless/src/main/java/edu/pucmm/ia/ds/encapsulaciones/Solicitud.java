package edu.pucmm.ia.ds.encapsulaciones;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="solicitudes")
public class Solicitud {


    private String id;
    
    @DynamoDBAttribute(attributeName="matricula")
    private String matricula;

    @DynamoDBAttribute(attributeName = "nombre")
    private String nombre;

    @DynamoDBAttribute(attributeName="correo")
    private String correo;

    @DynamoDBAttribute(attributeName = "laboratorio")
    private String laboratorio;

    @DynamoDBAttribute(attributeName="fecha")
    private String fecha;

    @DynamoDBAttribute(attributeName = "hora")
    private String hora;

    public Solicitud() {
    }

    public Solicitud(String matricula, String nombre, String correo, String laboratorio, String fecha, String hora) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.correo = correo;
        this.laboratorio = laboratorio;
        this.fecha = fecha;
        this.hora = hora;
    }

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Solicitud [id=" + id + ", matricula=" + matricula + ", nombre=" + nombre + ", correo=" + correo
                + ", laboratorio=" + laboratorio + ", fecha=" + fecha + ", hora=" + hora + "]";
    }
}