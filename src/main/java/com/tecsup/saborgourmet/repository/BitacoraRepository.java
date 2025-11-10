package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Bitacora;
import com.tecsup.saborgourmet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
    List<Bitacora> findByUsuarioOrderByFechaHoraDesc(Usuario usuario);
    List<Bitacora> findByModuloOrderByFechaHoraDesc(String modulo);
    List<Bitacora> findTop100ByOrderByFechaHoraDesc();
    List<Bitacora> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}