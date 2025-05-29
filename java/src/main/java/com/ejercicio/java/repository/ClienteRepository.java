package com.ejercicio.java.repository;

import com.ejercicio.java.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByPersonaIdentificacion(String identificacion);

    @Query("SELECT c FROM Cliente c JOIN FETCH c.persona WHERE c.clienteid = :clienteid")
    Optional<Cliente> findByIdWithPersona(@Param("clienteid") Long clienteid);

    @Query("SELECT c FROM Cliente c JOIN FETCH c.persona")
    java.util.List<Cliente> findAllWithPersona();
}