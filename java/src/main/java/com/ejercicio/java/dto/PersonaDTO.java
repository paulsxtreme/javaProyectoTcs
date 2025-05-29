package com.ejercicio.java.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {

    private Long id;

    // Validaciones para nombre
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 6, max = 100, message = "El nombre debe tener entre 6 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    // Validaciones para genero
    @Size(max = 20, message = "El género no puede exceder 20 caracteres")
    @Pattern(regexp = "^(MASCULINO|FEMENINO|Masculino|Femenino)?$",
            message = "El género debe ser: MASCULINO, FEMENINO")
    private String genero;

    // Validaciones para edad
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor a 150 años")
    private Integer edad;

    // Validaciones para identificacion
    @NotBlank(message = "La identificación es obligatoria")
    @Pattern(regexp = "^[0-9]{10,13}$", message = "La identificación debe tener entre 10 y 13 dígitos")
    private String identificacion;

    // Validaciones para direccion
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s\\.,#\\-]*$",
            message = "La dirección contiene caracteres no válidos")
    private String direccion;

    // Validaciones para telefono
    @Pattern(regexp = "^[0-9]{7,10}$", message = "El teléfono debe tener entre 7 y 10 dígitos")
    private String telefono;

    // Campo para control de concurrencia optimista
    // Evita que dos usuarios modifiquen el mismo registro simultáneamente
    // Detecta conflictos de actualización sin usar bloqueos de base de datos
    // Mejora el rendimiento al no bloquear registros
    private Long version;
}