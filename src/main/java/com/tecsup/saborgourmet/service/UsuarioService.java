package com.tecsup.saborgourmet.service;

import com.tecsup.saborgourmet.model.Usuario;
import com.tecsup.saborgourmet.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));
    }

    public Usuario createUsuario(Usuario usuario) {
        log.info("Creando usuario: {}", usuario.getNombreUsuario());

        if (usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese nombre");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = findById(id);

        if (!usuario.getNombreUsuario().equals(usuarioActualizado.getNombreUsuario())) {
            if (usuarioRepository.findByNombreUsuario(usuarioActualizado.getNombreUsuario()).isPresent()) {
                throw new RuntimeException("Ya existe un usuario con ese nombre");
            }
        }

        usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setEstado(usuarioActualizado.getEstado());

        if (usuarioActualizado.getContrasena() != null &&
                !usuarioActualizado.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
        }

        log.info("Actualizando usuario ID: {}", id);
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = findById(id);

        if ("juan".equals(usuario.getNombreUsuario())) {
            throw new RuntimeException("❌ El usuario 'juan' no puede ser eliminado");
        }

        usuario.setEstado(false);
        usuarioRepository.save(usuario);
        log.info("Usuario ID {} desactivado", id);
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAllActivos() {
        return usuarioRepository.findByEstadoTrue();
    }
}