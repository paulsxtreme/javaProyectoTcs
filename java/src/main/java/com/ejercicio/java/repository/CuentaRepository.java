package com.ejercicio.java.repository;

import com.ejercicio.java.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    List<Cuenta> findByClienteClienteid(Long clienteid);

    @Query("SELECT c FROM Cuenta c JOIN FETCH c.cliente cl JOIN FETCH cl.persona WHERE c.numerocuenta = :numerocuenta")
    java.util.Optional<Cuenta> findByIdWithClienteAndPersona(@Param("numerocuenta") Long numerocuenta);

    @Query("SELECT c FROM Cuenta c JOIN FETCH c.cliente cl JOIN FETCH cl.persona")
    List<Cuenta> findAllWithClienteAndPersona();

    @Query("SELECT c FROM Cuenta c JOIN FETCH c.cliente cl JOIN FETCH cl.persona WHERE cl.clienteid = :clienteid")
    List<Cuenta> findByClienteidWithClienteAndPersona(@Param("clienteid") Long clienteid);
}