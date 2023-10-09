package edu.pucmm.eict.practica2.repositorio;

import edu.pucmm.eict.practica2.entidades.Mock;
import edu.pucmm.eict.practica2.entidades.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MockRepository extends JpaRepository<Mock, Integer> {

    List<Mock> findAllByUsuario(Usuario usuario);

    boolean existsByRuta(String ruta);

    Mock findByRuta(String ruta);

    void deleteAllByFechaExpiracionLessThan(LocalDateTime lt);
}
