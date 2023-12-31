package edu.pucmm.clientemicroservice.config;

import edu.pucmm.clientemicroservice.servicios.SeguridadServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracionSeguridad {
    private SeguridadServices seguridadServices;

    public ConfiguracionSeguridad(SeguridadServices seguridadServices) {
        this.seguridadServices = seguridadServices;
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(seguridadServices);
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);

        System.out.println("Autentificación en JPA");
        auth.userDetailsService(seguridadServices).passwordEncoder(NoOpPasswordEncoder.getInstance());


        return auth.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(authorization ->
                        authorization
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/")).hasAnyRole("ADMIN", "USER") //permitiendo llamadas a esas urls.
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).permitAll()
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/user/**")).hasRole("USER") //hasAnyRole("ADMIN", "USER")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/logout")).permitAll()
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/mock/**")).permitAll()
                                .anyRequest().authenticated() //cualquier llamada debe ser validada
                )
                .formLogin((form) -> form
                        .loginPage("/login") //indicando la ruta que estaremos utilizando.
                        .failureUrl("/login?error") //en caso de fallar puedo indicar otra pagina.
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logout"))
                        .permitAll());
        return http.build();
    }

}
