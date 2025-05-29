package com.ejercicio.java.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {

    private Long id;

    @NotNull(message = "El número de cuenta es obligatorio")
    private Long numerocuenta;

    private ZonedDateTime fecha;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "^(DEBITO|CREDITO)$", message = "El tipo de movimiento debe ser DEBITO o CREDITO y tienen que estar en mayúsculas")
    private String tipomovimiento;

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "5.00", message = "El valor debe ser mayor o igual a cinco, sea para acreditar o debitar")
    @Digits(integer = 12, fraction = 2, message = "El valor debe tener máximo 12 dígitos enteros y 2 decimales")
    private BigDecimal valor;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    @Digits(integer = 12, fraction = 2, message = "El saldo debe tener máximo 12 dígitos enteros y 2 decimales")
    private BigDecimal saldo;

    // Campos adicionales para mostrar en respuestas
    private String nombreCliente;
    private String tipoCuenta;

    // Campo para control de concurrencia optimista
    private Long version;
}