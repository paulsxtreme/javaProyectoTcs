package com.ejercicio.java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numerocuenta;

    @ManyToOne
    @JoinColumn(name = "clienteid")
    private Cliente cliente;

    @Column(nullable = false)
    private String tipocuenta;

    @Column(nullable = false)
    private BigDecimal saldoinicial;

    private Boolean estado = true;
}