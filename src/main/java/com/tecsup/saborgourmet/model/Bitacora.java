package com.tecsup.saborgourmet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que registra todas las acciones realizadas en el sistema
 * Utilizada por el Aspecto de Auditoría (AOP)
 */
@Entity
@Table(name = "bitacora")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBitacora;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false, length = 50)
    private String modulo;

    @Column(nullable = false, length = 20)
    private String accion; // CREATE, UPDATE, DELETE

    @Column(columnDefinition = "TEXT")
    private String detalle;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(length = 50)
    private String ipAddress;

    @PrePersist
    protected void onCreate() {
        fechaHora = LocalDateTime.now();
    }
}