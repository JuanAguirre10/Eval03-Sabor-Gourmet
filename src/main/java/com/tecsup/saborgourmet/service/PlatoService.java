package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.Plato;
import com.tecsup.saborgourmet.repository.PlatoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlatoService {

    private final PlatoRepository platoRepository;

    public Plato createPlato(Plato plato) {
        log.info("Creando plato: {}", plato.getNombre());
        return platoRepository.save(plato);
    }

    public Plato updatePlato(Long id, Plato platoActualizado) {
        Plato plato = findById(id);

        plato.setNombre(platoActualizado.getNombre());
        plato.setTipo(platoActualizado.getTipo());
        plato.setPrecio(platoActualizado.getPrecio());
        plato.setDescripcion(platoActualizado.getDescripcion());
        plato.setEstado(platoActualizado.getEstado());

        log.info("Actualizando plato ID: {}", id);
        return platoRepository.save(plato);
    }

    public void deletePlato(Long id) {
        Plato plato = findById(id);
        plato.setEstado(false);
        platoRepository.save(plato);
        log.info("Plato ID {} desactivado", id);
    }

    @Transactional(readOnly = true)
    public Plato findById(Long id) {
        return platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Plato> findAllActivos() {
        return platoRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public List<Plato> findAll() {
        return platoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Plato> findByTipo(String tipo) {
        return platoRepository.findByTipoAndEstadoTrue(tipo);
    }

    @Transactional(readOnly = true)
    public List<Plato> buscarPorNombre(String nombre) {
        return platoRepository.findByNombreContainingIgnoreCaseAndEstadoTrue(nombre);
    }
}