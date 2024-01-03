package edu.pucmm.packmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class PackMicroserviceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(PackMicroserviceApplication.class, args);

		PackService packService = (PackService) applicationContext.getBean("packService");

		try{
			if(packService.getAll().isEmpty()){
				packService.savePack(new Pack(0, "Pre-Boda", 1000F));
				packService.savePack(new Pack(0, "Boda", 5000F));
				packService.savePack(new Pack(0, "Cumpleagnos", 3000F));
				packService.savePack(new Pack(0, "Video de evento", 4000F));
			}
		}catch (Exception e){
			System.out.println("Ya existen registros");
		}
	}

}
