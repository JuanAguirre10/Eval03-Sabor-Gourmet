package com.tecsup.saborgourmet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un Pedido
 */
@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mesa", nullable = false)
    @NotNull(message = "La mesa es obligatoria")
    private Mesa mesa;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "pendiente|en_preparacion|servido|cerrado",
            message = "Estado inválido"
    )
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String estado = "pendiente";

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Double total = 0.0;

    @PrePersist
    protected void onCreate() {
        fechaHora = LocalDateTime.now();
    }

    /**
     * Calcula el total del pedido sumando los subtotales de cada detalle.
     */
    public void calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    /**
     * Agrega un detalle al pedido y recalcula el total.
     */
    public void agregarDetalle(DetallePedido detalle) {
        detalle.setPedido(this);
        detalle.calcularSubtotal();
        detalles.add(detalle);
        calcularTotal();
    }
}
