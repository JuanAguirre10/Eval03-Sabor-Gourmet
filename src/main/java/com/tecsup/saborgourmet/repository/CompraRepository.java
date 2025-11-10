package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Compra;
import com.tecsup.saborgourmet.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByProveedorOrderByFechaCompraDesc(Proveedor proveedor);
    List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT SUM(c.total) FROM Compra c WHERE MONTH(c.fechaCompra) = MONTH(CURRENT_DATE) AND YEAR(c.fechaCompra) = YEAR(CURRENT_DATE)")
    Double calcularComprasMensuales();
}