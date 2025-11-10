package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.Compra;
import com.tecsup.saborgourmet.model.DetalleCompra;
import com.tecsup.saborgourmet.model.Proveedor;
import com.tecsup.saborgourmet.repository.CompraRepository;
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
public class CompraService {

    private final CompraRepository compraRepository;
    private final InsumoService insumoService;

    public Compra createCompra(Compra compra) {
        log.info("Creando compra para proveedor: {}", compra.getProveedor().getNombre());

        compra.calcularTotal();

        Compra compraSaved = compraRepository.save(compra);

        for (DetalleCompra detalle : compra.getDetalles()) {
            insumoService.aumentarStock(
                    detalle.getInsumo().getIdInsumo(),
                    detalle.getCantidad()
            );
        }

        log.info("Stock actualizado para {} insumos", compra.getDetalles().size());
        return compraSaved;
    }

    public Compra updateCompra(Long id, Compra compraActualizada) {
        Compra compra = findById(id);

        compra.setProveedor(compraActualizada.getProveedor());
        compra.calcularTotal();

        log.info("Actualizando compra ID: {}", id);
        return compraRepository.save(compra);
    }

    public void deleteCompra(Long id) {
        Compra compra = findById(id);

        for (DetalleCompra detalle : compra.getDetalles()) {
            try {
                insumoService.reducirStock(
                        detalle.getInsumo().getIdInsumo(),
                        detalle.getCantidad()
                );
            } catch (Exception e) {
                log.warn("No se pudo ajustar stock del insumo ID: {}",
                        detalle.getInsumo().getIdInsumo());
            }
        }

        compraRepository.delete(compra);
        log.info("Compra ID {} eliminada", id);
    }

    @Transactional(readOnly = true)
    public Compra findById(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Compra> findByProveedor(Proveedor proveedor) {
        return compraRepository.findByProveedorOrderByFechaCompraDesc(proveedor);
    }

    @Transactional(readOnly = true)
    public Double calcularComprasMensuales() {
        Double compras = compraRepository.calcularComprasMensuales();
        return compras != null ? compras : 0.0;
    }

    @Transactional(readOnly = true)
    public List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin) {
        return compraRepository.findByFechaCompraBetween(inicio, fin);
    }
}