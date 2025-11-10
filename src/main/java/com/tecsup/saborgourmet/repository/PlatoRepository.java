package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findByEstadoTrue();
    List<Plato> findByTipoAndEstadoTrue(String tipo);
    List<Plato> findByNombreContainingIgnoreCaseAndEstadoTrue(String nombre);
}