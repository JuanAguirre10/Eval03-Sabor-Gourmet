package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.Proveedor;
import com.tecsup.saborgourmet.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public Proveedor createProveedor(Proveedor proveedor) {
        log.info("Creando proveedor: {}", proveedor.getNombre());

        if (proveedorRepository.findByRuc(proveedor.getRuc()).isPresent()) {
            throw new RuntimeException("Ya existe un proveedor con el RUC: " + proveedor.getRuc());
        }

        return proveedorRepository.save(proveedor);
    }

    public Proveedor updateProveedor(Long id, Proveedor proveedorActualizado) {
        Proveedor proveedor = findById(id);

        if (!proveedor.getRuc().equals(proveedorActualizado.getRuc())) {
            if (proveedorRepository.findByRuc(proveedorActualizado.getRuc()).isPresent()) {
                throw new RuntimeException("Ya existe un proveedor con el RUC: " + proveedorActualizado.getRuc());
            }
        }

        proveedor.setRuc(proveedorActualizado.getRuc());
        proveedor.setNombre(proveedorActualizado.getNombre());
        proveedor.setTelefono(proveedorActualizado.getTelefono());
        proveedor.setCorreo(proveedorActualizado.getCorreo());
        proveedor.setDireccion(proveedorActualizado.getDireccion());
        proveedor.setEstado(proveedorActualizado.getEstado());

        log.info("Actualizando proveedor ID: {}", id);
        return proveedorRepository.save(proveedor);
    }

    public void deleteProveedor(Long id) {
        Proveedor proveedor = findById(id);
        proveedor.setEstado(false);
        proveedorRepository.save(proveedor);
        log.info("Proveedor ID {} desactivado", id);
    }

    @Transactional(readOnly = true)
    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Proveedor> findAllActivos() {
        return proveedorRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }
}