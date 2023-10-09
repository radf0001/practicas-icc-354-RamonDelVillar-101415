package edu.pucmm.eict.practica2.repositorio.seguridad;

import edu.pucmm.eict.practica2.entidades.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    //Cualquier metodo que necesite incluir.
    Usuario findByUsername(String username);

    boolean existsUsuarioByUsername(String username);

    Usuario findUsuarioById(int id);

}
