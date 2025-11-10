package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.DetallePedido;
import com.tecsup.saborgourmet.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedido(Pedido pedido);
}