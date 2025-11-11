package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.*;
import com.tecsup.saborgourmet.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MesaService {

    private final MesaRepository mesaRepository;

    public Mesa createMesa(Mesa mesa) {
        log.info("Creando mesa número: {}", mesa.getNumero());

        if (mesaRepository.findByNumero(mesa.getNumero()).isPresent()) {
            throw new RuntimeException("Ya existe una mesa con el número: " + mesa.getNumero());
        }

        return mesaRepository.save(mesa);
    }

    public Mesa updateMesa(Long id, Mesa mesaActualizada) {
        Mesa mesa = findById(id);

        if (!mesa.getNumero().equals(mesaActualizada.getNumero())) {
            if (mesaRepository.findByNumero(mesaActualizada.getNumero()).isPresent()) {
                throw new RuntimeException("Ya existe una mesa con el número: " + mesaActualizada.getNumero());
            }
        }

        mesa.setNumero(mesaActualizada.getNumero());
        mesa.setCapacidad(mesaActualizada.getCapacidad());
        mesa.setEstado(mesaActualizada.getEstado());

        log.info("Actualizando mesa ID: {}", id);
        return mesaRepository.save(mesa);
    }

    public void deleteMesa(Long id) {
        Mesa mesa = findById(id);

        if ("ocupada".equals(mesa.getEstado())) {
            throw new RuntimeException("No se puede eliminar una mesa ocupada");
        }

        mesaRepository.delete(mesa);
        log.info("Mesa ID {} eliminada", id);
    }

    public Mesa cambiarEstado(Long id, String nuevoEstado) {
        Mesa mesa = findById(id);
        mesa.setEstado(nuevoEstado);
        log.info("Cambiando estado de mesa {} a: {}", id, nuevoEstado);
        return mesaRepository.save(mesa);
    }

    public Mesa ocuparMesa(Long id) {
        return cambiarEstado(id, "ocupada");
    }

    public Mesa liberarMesa(Long id) {
        return cambiarEstado(id, "disponible");
    }

    @Transactional(readOnly = true)
    public Mesa findById(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Mesa> findAll() {
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Mesa> findByEstado(String estado) {
        return mesaRepository.findByEstadoOrderByNumeroAsc(estado);
    }

    @Transactional(readOnly = true)
    public List<Mesa> findMesasDisponibles() {
        return findByEstado("disponible");
    }

    @Transactional(readOnly = true)
    public Long contarMesasDisponibles() {
        return mesaRepository.countMesasDisponibles();
    }
}