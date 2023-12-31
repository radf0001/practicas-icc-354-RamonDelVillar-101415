package edu.pucmm.servidorperimetral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ServidorPerimetralApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServidorPerimetralApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        //Configurando las rutas programatica.
        return builder.routes()

                .route("user",p -> p.path("/user/**")
                        .filters(f -> f.rewritePath("/user/(?<segment>.*)", "/user/${segment}"))
                        .uri("lb://users-microservice"))

                .route("pack", p -> p.path("/pack/**")
                        .filters(f -> f.rewritePath("/pack/(?<segment>.*)", "/pack/${segment}"))
                        .uri("lb://pack-microservice"))

                .route("purchase", p -> p.path("/purchase/**")
                        .filters(f -> f.rewritePath("/purchase/(?<segment>.*)", "/packPurchase/${segment}"))
                        .uri("lb://packPurchases-microservice"))

                .build();
    }
}
