package edu.pucmm.clientemicroservice.servicios;

import edu.pucmm.clientemicroservice.clientes.ClienteFeign;
import edu.pucmm.clientemicroservice.dto.AuthenticationRequest;
import edu.pucmm.clientemicroservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SeguridadServices implements UserDetailsService {

    private final ClienteFeign clienteFeign;

    /**
     * Creando el usuario por defecto y su rol.
     */
//    public void crearUsuarios(){
//
//        if(usuarioRepository.findAll().isEmpty()){
//            System.out.println("Creación del usuario y rol en la base de datos");
//            Rol rolAdmin = new Rol("ROLE_ADMIN");
//            Rol rolUsuario = new Rol("ROLE_USER");
////            rolRepository.save(rolAdmin);
////            rolRepository.save(rolUsuario);
//
//            Usuario admin = new Usuario();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("admin"));
//            admin.setNombre("Administrador");
//            admin.setApellido("Admin");
//            admin.setRol("ROLE_ADMIN");
//            admin.setRoles(new HashSet<>(Arrays.asList(rolAdmin)));
//            admin.setActivo(true);
//            usuarioRepository.save(admin);
//
//            Usuario user = new Usuario();
//            user.setUsername("user");
//            user.setPassword(passwordEncoder.encode("user"));
//            user.setNombre("Usuario");
//            user.setApellido("User");
//            user.setRoles(new HashSet<>(Arrays.asList(rolUsuario)));
//            user.setActivo(true);
//            user.setRol("ROLE_USER");
//            usuarioRepository.save(user);
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Autenticación JPA");
        try{
            UserDto user = clienteFeign.readByEmailUser(new AuthenticationRequest(username, ""));
            if(user==null){
                throw new UsernameNotFoundException("Usuario no existe.");
            }
            Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
            roles.add(new SimpleGrantedAuthority(user.getRole()));
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

            System.out.println(user);

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, grantedAuthorities);
        }catch (Exception e){
            throw new UsernameNotFoundException("Usuario no existe.");
        }
    }
}