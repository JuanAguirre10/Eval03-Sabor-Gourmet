package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByRuc(String ruc);
    List<Proveedor> findByEstadoTrue();
    List<Proveedor> findByNombreContainingIgnoreCaseAndEstadoTrue(String nombre);
}