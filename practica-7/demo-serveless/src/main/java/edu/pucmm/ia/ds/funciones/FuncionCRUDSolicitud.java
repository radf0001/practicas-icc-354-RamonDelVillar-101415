package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;
import edu.pucmm.ia.ds.encapsulaciones.Solicitud;
import edu.pucmm.ia.ds.services.SolicitudDynamoDbServices;

import java.util.HashMap;

/**
 * Función para trabajar con el CRUD de solicitudes en AWS.
 */
public class FuncionCRUDSolicitud implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final SolicitudDynamoDbServices solicitudDynamoDbServices = new SolicitudDynamoDbServices();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        Solicitud solicitud = null;
        //Recuperando la entrada, ver en CloudWatch la salida:
        context.getLogger().log("Petición: "+gson.toJson(request));

        //
        String metodoHttp = request.getHttpMethod();

        context.getLogger().log("Metodo de acceso: "+metodoHttp);
        context.getLogger().log("Parametros enviados: "+request.getPathParameters());
        context.getLogger().log("Cuerpo enviado: "+request.getBody());

        //
        //Realizando la operacion
        String salida = "";
        switch (metodoHttp){
            case "GET":
                SolicitudDynamoDbServices.ListarSolicitudResponse listarSolicitudResponse = solicitudDynamoDbServices.listarSolicitudes(null, context);
                salida = gson.toJson(listarSolicitudResponse);
                break;
            case "POST":
            case "PUT":
                solicitud = gson.fromJson(request.getBody(), Solicitud.class);
                if(solicitud.getId() != null){
                    if(solicitud.getId().isEmpty()){
                        solicitud.setId(null);
                    }
                }
                SolicitudDynamoDbServices.SolicitudResponse solicitudResponse = solicitudDynamoDbServices.insertarSolicitud(solicitud, context);
                salida = gson.toJson(solicitudResponse);
                break;
            case "DELETE":
                solicitud = gson.fromJson(request.getBody(), Solicitud.class);
                solicitudDynamoDbServices.eliminarSolicitudes(solicitud, context);
                salida = gson.toJson(solicitud);
                break;
        }

        //Headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        //Salida.
        APIGatewayProxyResponseEvent reponse = new APIGatewayProxyResponseEvent();
        reponse.setStatusCode(200);
        reponse.setHeaders(headers);
        reponse.setBody(salida);

        //
        return reponse;

    }
}
