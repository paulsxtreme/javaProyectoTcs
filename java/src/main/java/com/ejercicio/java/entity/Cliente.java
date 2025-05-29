package com.ejercicio.java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", unique = true)
    private Persona persona;

    @Column(nullable = false)
    private String contrasena;

    private Boolean estado = true;
}