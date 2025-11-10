package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Mesa;
import com.tecsup.saborgourmet.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(String estado);
    List<Pedido> findByMesaAndEstadoNot(Mesa mesa, String estado);
    List<Pedido> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT p FROM Pedido p WHERE p.estado IN ('pendiente', 'en_preparacion') ORDER BY p.fechaHora DESC")
    List<Pedido> findPedidosActivos();

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.estado = 'cerrado' AND DATE(p.fechaHora) = CURRENT_DATE")
    Double calcularVentasDiarias();
}