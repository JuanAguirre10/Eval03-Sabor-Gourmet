package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    List<Usuario> findByEstadoTrue();
    List<Usuario> findByRol(String rol);
}