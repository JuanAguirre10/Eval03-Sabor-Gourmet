package com.tecsup.saborgourmet.config;

import com.tecsup.saborgourmet.model.*;
import com.tecsup.saborgourmet.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializa datos de prueba en la base de datos
 * @author Juan Aguirre Saavedra
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;
    private final PlatoRepository platoRepository;
    private final InsumoRepository insumoRepository;
    private final ProveedorRepository proveedorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("🔄 Inicializando datos del sistema...");

        // Crear usuarios si no existen
        if (usuarioRepository.count() == 0) {
            crearUsuarios();
        }

        // Crear mesas si no existen
        if (mesaRepository.count() == 0) {
            crearMesas();
        }

        // Crear platos si no existen
        if (platoRepository.count() == 0) {
            crearPlatos();
        }

        // Crear insumos si no existen
        if (insumoRepository.count() == 0) {
            crearInsumos();
        }

        // Crear proveedores si no existen
        if (proveedorRepository.count() == 0) {
            crearProveedores();
        }

        // Crear clientes de ejemplo
        if (clienteRepository.count() == 0) {
            crearClientes();
        }

        log.info("✅ Datos inicializados correctamente");
    }

    private void crearUsuarios() {
        usuarioRepository.save(Usuario.builder()
                .nombreUsuario("admin")
                .contrasena(passwordEncoder.encode("admin123"))
                .rol("ADMIN")
                .estado(true)
                .build());

        usuarioRepository.save(Usuario.builder()
                .nombreUsuario("mozo")
                .contrasena(passwordEncoder.encode("mozo123"))
                .rol("MOZO")
                .estado(true)
                .build());

        usuarioRepository.save(Usuario.builder()
                .nombreUsuario("cocinero")
                .contrasena(passwordEncoder.encode("cocinero123"))
                .rol("COCINERO")
                .estado(true)
                .build());

        usuarioRepository.save(Usuario.builder()
                .nombreUsuario("cajero")
                .contrasena(passwordEncoder.encode("cajero123"))
                .rol("CAJERO")
                .estado(true)
                .build());

        // ✅ USUARIO JUAN (NO SE PUEDE ELIMINAR)
        usuarioRepository.save(Usuario.builder()
                .nombreUsuario("juan")
                .contrasena(passwordEncoder.encode("1234"))
                .rol("ADMIN")
                .estado(true)
                .build());

        log.info("👥 Usuarios creados");
    }

    private void crearMesas() {
        for (int i = 1; i <= 15; i++) {
            mesaRepository.save(Mesa.builder()
                    .numero(i)
                    .capacidad(i <= 10 ? 4 : 6)
                    .estado("disponible")
                    .build());
        }
        log.info("🪑 Mesas creadas");
    }

    private void crearPlatos() {
        // Entradas
        platoRepository.save(Plato.builder()
                .nombre("Causa Limeña")
                .tipo("entrada")
                .precio(18.00)
                .descripcion("Causa tradicional con pollo y palta")
                .estado(true)
                .build());

        platoRepository.save(Plato.builder()
                .nombre("Tequeños")
                .tipo("entrada")
                .precio(15.00)
                .descripcion("Tequeños de queso con salsas")
                .estado(true)
                .build());

        // Fondos
        platoRepository.save(Plato.builder()
                .nombre("Lomo Saltado")
                .tipo("fondo")
                .precio(35.00)
                .descripcion("Lomo saltado con papas fritas")
                .estado(true)
                .build());

        platoRepository.save(Plato.builder()
                .nombre("Arroz con Mariscos")
                .tipo("fondo")
                .precio(42.00)
                .descripcion("Arroz con mariscos frescos")
                .estado(true)
                .build());

        platoRepository.save(Plato.builder()
                .nombre("Ají de Gallina")
                .tipo("fondo")
                .precio(28.00)
                .descripcion("Ají de gallina con arroz")
                .estado(true)
                .build());

        // Postres
        platoRepository.save(Plato.builder()
                .nombre("Suspiro Limeño")
                .tipo("postre")
                .precio(12.00)
                .descripcion("Postre tradicional peruano")
                .estado(true)
                .build());

        platoRepository.save(Plato.builder()
                .nombre("Tres Leches")
                .tipo("postre")
                .precio(14.00)
                .descripcion("Torta tres leches")
                .estado(true)
                .build());

        // Bebidas
        platoRepository.save(Plato.builder()
                .nombre("Chicha Morada")
                .tipo("bebida")
                .precio(8.00)
                .descripcion("Chicha morada natural")
                .estado(true)
                .build());

        platoRepository.save(Plato.builder()
                .nombre("Inca Kola")
                .tipo("bebida")
                .precio(5.00)
                .descripcion("Gaseosa Inca Kola 500ml")
                .estado(true)
                .build());

        log.info("🍽️ Platos creados");
    }

    private void crearInsumos() {
        insumoRepository.save(Insumo.builder()
                .nombre("Papa Blanca")
                .unidadMedida("kg")
                .stock(50.0)
                .stockMinimo(10.0)
                .precioCompra(2.50)
                .estado(true)
                .build());

        insumoRepository.save(Insumo.builder()
                .nombre("Carne de Res")
                .unidadMedida("kg")
                .stock(30.0)
                .stockMinimo(5.0)
                .precioCompra(25.00)
                .estado(true)
                .build());

        insumoRepository.save(Insumo.builder()
                .nombre("Pollo")
                .unidadMedida("kg")
                .stock(40.0)
                .stockMinimo(10.0)
                .precioCompra(12.00)
                .estado(true)
                .build());

        insumoRepository.save(Insumo.builder()
                .nombre("Arroz")
                .unidadMedida("kg")
                .stock(100.0)
                .stockMinimo(20.0)
                .precioCompra(3.50)
                .estado(true)
                .build());

        insumoRepository.save(Insumo.builder()
                .nombre("Tomate")
                .unidadMedida("kg")
                .stock(20.0)
                .stockMinimo(5.0)
                .precioCompra(4.00)
                .estado(true)
                .build());

        log.info("📦 Insumos creados");
    }

    private void crearProveedores() {
        proveedorRepository.save(Proveedor.builder()
                .ruc("20123456789")
                .nombre("Distribuidora La Granja SAC")
                .telefono("987654321")
                .correo("ventas@lagranja.com")
                .direccion("Av. Argentina 123, Lima")
                .estado(true)
                .build());

        proveedorRepository.save(Proveedor.builder()
                .ruc("20987654321")
                .nombre("Mercado Central EIRL")
                .telefono("912345678")
                .correo("contacto@mercadocentral.com")
                .direccion("Jr. Huallaga 456, Lima")
                .estado(true)
                .build());

        log.info("🏪 Proveedores creados");
    }

    private void crearClientes() {
        clienteRepository.save(Cliente.builder()
                .dni("12345678")
                .nombres("Juan Carlos")
                .apellidos("Pérez García")
                .telefono("987654321")
                .correo("juan.perez@email.com")
                .estado(true)
                .build());

        clienteRepository.save(Cliente.builder()
                .dni("87654321")
                .nombres("María")
                .apellidos("López Torres")
                .telefono("912345678")
                .correo("maria.lopez@email.com")
                .estado(true)
                .build());

        log.info("👤 Clientes de ejemplo creados");
    }
}
