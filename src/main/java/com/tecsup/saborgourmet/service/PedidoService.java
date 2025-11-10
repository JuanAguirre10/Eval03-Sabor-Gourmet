package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.*;
import com.tecsup.saborgourmet.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaService mesaService;

    public Pedido createPedido(Pedido pedido) {
        log.info("Creando pedido para mesa: {}", pedido.getMesa().getNumero());

        Mesa mesa = pedido.getMesa();
        if ("mantenimiento".equals(mesa.getEstado())) {
            throw new RuntimeException("La mesa está en mantenimiento");
        }

        mesaService.ocuparMesa(mesa.getIdMesa());

        pedido.calcularTotal();

        return pedidoRepository.save(pedido);
    }

    public Pedido updatePedido(Long id, Pedido pedidoActualizado) {
        Pedido pedido = findById(id);

        if ("cerrado".equals(pedido.getEstado())) {
            throw new RuntimeException("No se puede modificar un pedido cerrado");
        }

        pedido.setCliente(pedidoActualizado.getCliente());
        pedido.setEstado(pedidoActualizado.getEstado());

        if ("cerrado".equals(pedidoActualizado.getEstado())) {
            mesaService.liberarMesa(pedido.getMesa().getIdMesa());
        }

        pedido.calcularTotal();

        log.info("Actualizando pedido ID: {}", id);
        return pedidoRepository.save(pedido);
    }

    public Pedido agregarDetalle(Long idPedido, DetallePedido detalle) {
        Pedido pedido = findById(idPedido);

        if ("cerrado".equals(pedido.getEstado())) {
            throw new RuntimeException("No se puede agregar items a un pedido cerrado");
        }

        pedido.agregarDetalle(detalle);
        log.info("Agregando item al pedido ID: {}", idPedido);
        return pedidoRepository.save(pedido);
    }

    public Pedido cambiarEstado(Long id, String nuevoEstado) {
        Pedido pedido = findById(id);
        pedido.setEstado(nuevoEstado);

        if ("cerrado".equals(nuevoEstado)) {
            mesaService.liberarMesa(pedido.getMesa().getIdMesa());
        }

        log.info("Cambiando estado de pedido {} a: {}", id, nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    public Pedido cerrarPedido(Long id) {
        return cambiarEstado(id, "cerrado");
    }

    public void deletePedido(Long id) {
        Pedido pedido = findById(id);

        if (!"cerrado".equals(pedido.getEstado())) {
            mesaService.liberarMesa(pedido.getMesa().getIdMesa());
        }

        pedidoRepository.delete(pedido);
        log.info("Pedido ID {} eliminado", id);
    }

    @Transactional(readOnly = true)
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Pedido> findPedidosActivos() {
        return pedidoRepository.findPedidosActivos();
    }

    @Transactional(readOnly = true)
    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public Double calcularVentasDiarias() {
        Double ventas = pedidoRepository.calcularVentasDiarias();
        return ventas != null ? ventas : 0.0;
    }

    @Transactional(readOnly = true)
    public List<Pedido> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin) {
        return pedidoRepository.findByFechaHoraBetween(inicio, fin);
    }
}