package edu.pucmm.eict.practica4.servicios.seguridad;

import edu.pucmm.eict.practica4.entidades.seguridad.Rol;
import edu.pucmm.eict.practica4.entidades.seguridad.Usuario;
import edu.pucmm.eict.practica4.repositorio.seguridad.RolRepository;
import edu.pucmm.eict.practica4.repositorio.seguridad.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeguridadServices implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Lazy
    @Autowired
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public SeguridadServices(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return passwordEncoder;
    }

    /**
     * Creando el usuario por defecto y su rol.
     */
    public void crearUsuarios(){

        if(usuarioRepository.findAll().isEmpty()){
            try {
                System.out.println("Creación del usuario y rol en la base de datos");
                Rol rolAdmin = new Rol("ROLE_ADMIN");
                Rol rolUsuario = new Rol("ROLE_USER");
//                rolRepository.save(rolAdmin);
//                rolRepository.save(rolUsuario);

                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setNombre("Administrador");
                admin.setApellido("Admin");
                admin.setRol("ROLE_ADMIN");
                admin.setRoles(new HashSet<>(List.of(rolAdmin)));
                admin.setActivo(true);
                usuarioRepository.save(admin);

                Usuario user = new Usuario();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user"));
                user.setNombre("Usuario");
                user.setApellido("User");
                user.setRoles(new HashSet<>(List.of(rolUsuario)));
                user.setActivo(true);
                user.setRol("ROLE_USER");
                usuarioRepository.save(user);
            }catch (Exception e){
                System.out.println("Something went wrong.");
            }
        }
    }

    public Usuario crearUsuario(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario crearUsuarioSinCambioClave(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Rol rolFindById(String rol){
        return rolRepository.getReferenceById(rol);
    }

    public Usuario deleteById(Usuario user){
        return usuarioRepository.save(user);
    }


    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
    public Usuario findByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }
    public boolean existsUsuarioByUsername(String username){return usuarioRepository.existsUsuarioByUsername(username);}

    public Usuario findUsuarioById(int id){return  usuarioRepository.findUsuarioById(id);}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Autenticación JPA");
        Usuario user = usuarioRepository.findByUsername(username);
        if(user==null || !user.isActivo()){
            throw new UsernameNotFoundException("Usuario no existe.");
        }

        user.setToken(jwtService.generateToken(user.getUsername()).token());
        usuarioRepository.save(user);

        Set<GrantedAuthority> roles = new HashSet<>();
        for (Rol role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }
}