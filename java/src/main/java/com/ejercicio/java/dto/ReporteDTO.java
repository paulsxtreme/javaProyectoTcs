// Define el paquete donde se encuentra esta clase
package com.ejercicio.java.dto;

// Importa anotaciones de Lombok para generar automáticamente código boilerplate
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Importa clases necesarias para los tipos de datos utilizados en este DTO
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

// Anotación de Lombok que genera getters, setters, equals, hashCode y toString
@Data

// Anotación de Lombok que genera un constructor sin argumentos
@NoArgsConstructor

// Anotación de Lombok que genera un constructor con todos los argumentos
@AllArgsConstructor


// Clase principal que representa el DTO para los reportes de estado de cuenta
public class ReporteDTO {

    // Nombre del cliente al que pertenece el reporte
    private String nombreCliente;
    // Número de identificación del cliente
    private String identificacion;
    // Lista de cuentas asociadas al cliente con sus respectivos movimientos
    private List<CuentaReporteDTO> cuentas;

    // Clase interna que representa una cuenta en el reporte
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CuentaReporteDTO {
        // Número único de la cuenta
        private Long numeroCuenta;
        // Tipo de cuenta (AHORRO o CORRIENTE)
        private String tipoCuenta;
        // Saldo actual disponible en la cuenta
        private BigDecimal saldoDisponible;
        // Lista de movimientos asociados a esta cuenta
        private List<MovimientoReporteDTO> movimientos;
    }

    // Clase interna que representa un movimiento en el reporte
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimientoReporteDTO {
        // Fecha y hora en que se realizó el movimiento
        private ZonedDateTime fecha;
        // Tipo de movimiento (DEBITO o CREDITO)
        private String tipoMovimiento;
        // Valor del movimiento (positivo para créditos, negativo para débitos)
        private BigDecimal valor;
        // Saldo resultante después de aplicar el movimiento
        private BigDecimal saldo;
        private Long version;
    }
}