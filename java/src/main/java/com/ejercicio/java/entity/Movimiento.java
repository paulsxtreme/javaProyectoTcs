package com.ejercicio.java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "numerocuenta")
    private Cuenta cuenta;

    @Column(columnDefinition = "timestamp with time zone default CURRENT_TIMESTAMP")
    private ZonedDateTime fecha;

    @Column(nullable = false)
    private String tipomovimiento;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private BigDecimal saldo;

    @PrePersist
    public void prePersist() {
        if (fecha == null) {
            fecha = ZonedDateTime.now();
        }
    }
}