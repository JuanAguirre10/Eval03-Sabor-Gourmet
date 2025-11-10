package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.Cliente;
import com.tecsup.saborgourmet.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) {
        log.info("Creando cliente: {}", cliente.getNombreCompleto());

        if (clienteRepository.findByDni(cliente.getDni()).isPresent()) {
            throw new RuntimeException("Ya existe un cliente con el DNI: " + cliente.getDni());
        }

        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente clienteActualizado) {
        Cliente cliente = findById(id);

        if (!cliente.getDni().equals(clienteActualizado.getDni())) {
            if (clienteRepository.findByDni(clienteActualizado.getDni()).isPresent()) {
                throw new RuntimeException("Ya existe un cliente con el DNI: " + clienteActualizado.getDni());
            }
        }

        cliente.setDni(clienteActualizado.getDni());
        cliente.setNombres(clienteActualizado.getNombres());
        cliente.setApellidos(clienteActualizado.getApellidos());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setEstado(clienteActualizado.getEstado());

        log.info("Actualizando cliente ID: {}", id);
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        Cliente cliente = findById(id);
        cliente.setEstado(false);
        clienteRepository.save(cliente);
        log.info("Cliente ID {} desactivado", id);
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAllActivos() {
        return clienteRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNombre(String texto) {
        return clienteRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(
                texto, texto);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> findByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
}