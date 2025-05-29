package com.ejercicio.java.repository;

import com.ejercicio.java.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaNumerocuenta(Long numerocuenta);

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.numerocuenta = :numerocuenta ORDER BY m.fecha DESC LIMIT 1")
    java.util.Optional<Movimiento> findTopByCuentaNumerocuentaOrderByFechaDesc(@Param("numerocuenta") Long numerocuenta);

    @Query("SELECT m FROM Movimiento m JOIN FETCH m.cuenta c JOIN FETCH c.cliente cl JOIN FETCH cl.persona p " +
            "WHERE m.id = :id")
    java.util.Optional<Movimiento> findByIdWithCuentaAndClienteAndPersona(@Param("id") Long id);

    @Query("SELECT m FROM Movimiento m JOIN FETCH m.cuenta c JOIN FETCH c.cliente cl JOIN FETCH cl.persona p")
    List<Movimiento> findAllWithCuentaAndClienteAndPersona();

    @Query("SELECT m FROM Movimiento m JOIN FETCH m.cuenta c JOIN FETCH c.cliente cl JOIN FETCH cl.persona p " +
            "WHERE cl.clienteid = :clienteid AND m.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY c.numerocuenta, m.fecha")
    List<Movimiento> findByClienteidAndFechaBetweenOrderByCuentaAndFecha(
            @Param("clienteid") Long clienteid,
            @Param("fechaInicio") ZonedDateTime fechaInicio,
            @Param("fechaFin") ZonedDateTime fechaFin);
}