package edu.pucmm.ia.ds.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import edu.pucmm.ia.ds.encapsulaciones.Solicitud;
import edu.pucmm.ia.ds.util.ServerlessHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase para encapsular la funcionalidad CRUD en la base de datos DynamoDB
 * utilizando el DynamoDBMapper, ver:
 * https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.Methods.html
 */
public class SolicitudDynamoDbServices {

    /**
     * Utilizando el Dynamo Mapper que funciona como un ORM.
     * @param solicitud
     * @param context
     * @return
     */
    public SolicitudResponse insertarSolicitud(Solicitud solicitud, Context context){

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        if(solicitud.getMatricula().isEmpty() || solicitud.getNombre().isEmpty() || solicitud.getCorreo().isEmpty() || solicitud.getLaboratorio().isEmpty() || solicitud.getFecha().isEmpty() || solicitud.getHora().isEmpty()){
           throw new RuntimeException("Datos enviados no son validos");
        }

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":fecha", new AttributeValue().withS(solicitud.getFecha()));
        expressionAttributeValues.put(":hora", new AttributeValue().withS(solicitud.getHora()));
        List<Solicitud> solicitudes = mapper.scan(Solicitud.class, new DynamoDBScanExpression()
        .withFilterExpression("fecha = :fecha AND hora = :hora")
        .withExpressionAttributeValues(expressionAttributeValues));

        if(solicitudes.size() < 7){
            try {
                mapper.save(solicitud);
            }catch (Exception e){
                return new SolicitudResponse(true, e.getMessage(), null);
            }

            //Retornando
            return new SolicitudResponse(false,"", solicitud);
        }
        return new SolicitudResponse(true, "Ya hay 7 solicitudes en este horario", null);
    }

    /**
     * Metodo para retornar el listado de todos los elementos de la tablas
     * @param filtro
     * @param context
     * @return
     */
    public ListarSolicitudResponse listarSolicitudes(FiltroListaSolicitud filtro, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        List<Solicitud> solicitudes = mapper.scan(Solicitud.class, new DynamoDBScanExpression());

        return new ListarSolicitudResponse(false, "", solicitudes);
    }

    /**
     * Función para eliminar un estudiantes
     * @param solictud
     * @param context
     * @return
     */
    public SolicitudResponse eliminarSolicitudes(Solicitud solicitud, Context context){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        mapper.delete(solicitud);

        return new SolicitudResponse(false, null, solicitud);
    }

    /**
     * Representa el objeto que encapsula la información de la consulta
     */
    public static class ListarSolicitudResponse {
        boolean error;
        String mensajeError;
        List<Solicitud> solicitudes;

        public ListarSolicitudResponse() {
        }

        public ListarSolicitudResponse(boolean error, String mensajeError, List<Solicitud> solicitudes) {
            this.error = error;
            this.mensajeError = mensajeError;
            this.solicitudes = solicitudes;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMensajeError() {
            return mensajeError;
        }

        public void setMensajeError(String mensajeError) {
            this.mensajeError = mensajeError;
        }

        public List<Solicitud> getSolicitudes() {
            return solicitudes;
        }

        public void setSolicitudes(List<Solicitud> solicitudes) {
            this.solicitudes = solicitudes;
        }
    }

    /**
     *  Encapsulación del objeto de respuesta.
     */
    public static class SolicitudResponse {
        boolean error;
        String mensajeError;
        Solicitud solicitud;

        public SolicitudResponse(){

        }

        public SolicitudResponse(boolean error, String mensajeError, Solicitud solicitud) {
            this.error = error;
            this.mensajeError = mensajeError;
            this.solicitud = solicitud;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMensajeError() {
            return mensajeError;
        }

        public void setMensajeError(String mensajeError) {
            this.mensajeError = mensajeError;
        }

        public Solicitud getSolicitud() {
            return solicitud;
        }

        public void setSolicitud(Solicitud solicitud) {
            this.solicitud = solicitud;
        }
    }

    public static class FiltroListaSolicitud {
        String filtro;

        public String getFiltro() {
            return filtro;
        }

        public void setFiltro(String filtro) {
            this.filtro = filtro;
        }
    }

}
