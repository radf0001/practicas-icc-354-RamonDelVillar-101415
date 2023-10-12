package edu.pucmm.eict.practica2;

import edu.pucmm.eict.practica2.servicios.seguridad.SeguridadServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Date;

@SpringBootApplication
public class Practica2Application implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;

    public Practica2Application(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(localeChangeInterceptor);
    }

    public static void main(String[] args) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("lang/messages");
        messageSource.setDefaultEncoding("UTF-8");

        ApplicationContext applicationContext = SpringApplication.run(Practica2Application.class, args);

        String[] lista = applicationContext.getBeanDefinitionNames();
        System.out.println("====== Beans Registrados =====");
        for(String bean : lista){
            System.out.println(""+bean);
        }
        System.out.println("====== FIN Beans Registrados =====");

        SeguridadServices seguridadServices = (SeguridadServices) applicationContext.getBean("seguridadServices");
        seguridadServices.crearUsuarios();
    }

}
