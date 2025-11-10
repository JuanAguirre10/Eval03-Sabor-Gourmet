package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    List<Insumo> findByEstadoTrue();

    @Query("SELECT i FROM Insumo i WHERE i.stock <= i.stockMinimo AND i.estado = true")
    List<Insumo> findInsumosConStockBajo();

    List<Insumo> findByNombreContainingIgnoreCaseAndEstadoTrue(String nombre);
}