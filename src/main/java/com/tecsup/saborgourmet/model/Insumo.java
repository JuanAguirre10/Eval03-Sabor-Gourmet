package com.tecsup.saborgourmet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entidad que representa un Insumo del inventario
 */
@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsumo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Column(nullable = false, length = 20)
    private String unidadMedida;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Double stock;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Column(nullable = false)
    private Double stockMinimo;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false)
    private Double precioCompra;

    @Column(nullable = false)
    private Boolean estado = true;

    public boolean necesitaReabastecimiento() {
        return stock <= stockMinimo;
    }

    public String getEstadoStock() {
        if (stock == 0) return "Sin Stock";
        if (necesitaReabastecimiento()) return "Stock Bajo";
        return "Stock Normal";
    }
}