package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumero(Integer numero);
    List<Mesa> findByEstado(String estado);
    List<Mesa> findByEstadoOrderByNumeroAsc(String estado);

    @Query("SELECT COUNT(m) FROM Mesa m WHERE m.estado = 'disponible'")
    Long countMesasDisponibles();
}