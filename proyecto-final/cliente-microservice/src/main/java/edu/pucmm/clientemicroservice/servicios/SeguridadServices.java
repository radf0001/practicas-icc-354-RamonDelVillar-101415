package edu.pucmm.clientemicroservice.servicios;

import edu.pucmm.clientemicroservice.clientes.ClienteFeign;
import edu.pucmm.clientemicroservice.dto.AuthenticationRequest;
import edu.pucmm.clientemicroservice.dto.PackDto;
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