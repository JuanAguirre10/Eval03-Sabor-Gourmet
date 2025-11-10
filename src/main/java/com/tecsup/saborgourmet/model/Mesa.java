package com.tecsup.saborgourmet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entidad que representa una Mesa del restaurante
 */
@Entity
@Table(name = "mesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;

    @NotNull(message = "El número de mesa es obligatorio")
    @Min(value = 1, message = "El número debe ser mayor a 0")
    @Column(nullable = false, unique = true)
    private Integer numero;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Max(value = 20, message = "La capacidad máxima es 20")
    @Column(nullable = false)
    private Integer capacidad;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "disponible|ocupada|reservada|mantenimiento",
            message = "Estado inválido")
    @Column(nullable = false, length = 20)
    private String estado = "disponible";

    public boolean isDisponible() {
        return "disponible".equals(estado);
    }

    public boolean isOcupada() {
        return "ocupada".equals(estado);
    }
}