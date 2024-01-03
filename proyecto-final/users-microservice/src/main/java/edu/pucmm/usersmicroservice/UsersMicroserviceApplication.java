package edu.pucmm.usersmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UsersMicroserviceApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(UsersMicroserviceApplication.class, args);

		UserService userService = (UserService) applicationContext.getBean("userService");
		try{
			if(userService.getAll().isEmpty()){
				userService.saveUser(new User(0, "admin", "admin", "admin@gmail.com", "admin", "admin"));
				userService.saveUser(new User(0, "cliente", "cliente", "cliente@gmail.com", "cliente", "cliente"));
				userService.saveUser(new User(0, "empleado", "empleado", "empleado@gmail.com", "empleado", "empleado"));
			}
		}catch (Exception e){
			System.out.println("Ya existen registros");
		}
	}

}
