package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.Bitacora;
import com.tecsup.saborgourmet.model.Usuario;
import com.tecsup.saborgourmet.repository.BitacoraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public List<Bitacora> findByUsuario(Usuario usuario) {
        return bitacoraRepository.findByUsuarioOrderByFechaHoraDesc(usuario);
    }

    public List<Bitacora> findByModulo(String modulo) {
        return bitacoraRepository.findByModuloOrderByFechaHoraDesc(modulo);
    }

    public List<Bitacora> findTop100() {
        return bitacoraRepository.findTop100ByOrderByFechaHoraDesc();
    }

    public List<Bitacora> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin) {
        return bitacoraRepository.findByFechaHoraBetween(inicio, fin);
    }
}