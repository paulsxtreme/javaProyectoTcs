package com.ejercicio.java.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long clienteid;

    @Valid
    @NotNull(message = "Los datos personales son obligatorios")
    private PersonaDTO persona;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;

    private Boolean estado = true;


    //Evita que dos usuarios modifiquen el mismo registro simultáneamente
    //Detecta conflictos de actualización sin usar bloqueos de base de datos
    //Mejora el rendimiento al no bloquear registros
    private Long version;
}