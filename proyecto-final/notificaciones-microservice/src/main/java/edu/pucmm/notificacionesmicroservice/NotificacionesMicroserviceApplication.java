package edu.pucmm.notificacionesmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificacionesMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificacionesMicroserviceApplication.class, args);
	}

}
