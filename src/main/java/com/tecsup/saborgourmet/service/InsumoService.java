package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.Insumo;
import com.tecsup.saborgourmet.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public Insumo createInsumo(Insumo insumo) {
        log.info("Creando insumo: {}", insumo.getNombre());
        return insumoRepository.save(insumo);
    }

    public Insumo updateInsumo(Long id, Insumo insumoActualizado) {
        Insumo insumo = findById(id);

        insumo.setNombre(insumoActualizado.getNombre());
        insumo.setUnidadMedida(insumoActualizado.getUnidadMedida());
        insumo.setStock(insumoActualizado.getStock());
        insumo.setStockMinimo(insumoActualizado.getStockMinimo());
        insumo.setPrecioCompra(insumoActualizado.getPrecioCompra());
        insumo.setEstado(insumoActualizado.getEstado());

        log.info("Actualizando insumo ID: {}", id);
        return insumoRepository.save(insumo);
    }

    public Insumo actualizarStock(Long id, Double cantidad) {
        Insumo insumo = findById(id);
        Double nuevoStock = insumo.getStock() + cantidad;

        if (nuevoStock < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }

        insumo.setStock(nuevoStock);
        log.info("Actualizando stock de insumo ID {}: {} -> {}",
                id, insumo.getStock(), nuevoStock);

        return insumoRepository.save(insumo);
    }

    public Insumo aumentarStock(Long id, Double cantidad) {
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        return actualizarStock(id, cantidad);
    }

    public Insumo reducirStock(Long id, Double cantidad) {
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        return actualizarStock(id, -cantidad);
    }

    public void deleteInsumo(Long id) {
        Insumo insumo = findById(id);
        insumo.setEstado(false);
        insumoRepository.save(insumo);
        log.info("Insumo ID {} desactivado", id);
    }

    @Transactional(readOnly = true)
    public Insumo findById(Long id) {
        return insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Insumo> findAllActivos() {
        return insumoRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public List<Insumo> findAll() {
        return insumoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Insumo> findInsumosConStockBajo() {
        return insumoRepository.findInsumosConStockBajo();
    }

    @Transactional(readOnly = true)
    public List<Insumo> buscarPorNombre(String nombre) {
        return insumoRepository.findByNombreContainingIgnoreCaseAndEstadoTrue(nombre);
    }
}