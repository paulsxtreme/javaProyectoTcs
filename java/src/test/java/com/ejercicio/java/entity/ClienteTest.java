package com.ejercicio.java.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Prueba unitaria para la entidad Cliente")
class ClienteTest {

    private Cliente cliente;
    private Persona persona;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
        persona = new Persona();
        // Asumiendo que Persona tiene algunos campos básicos
        // persona.setNombre("Juan");
        // persona.setApellido("Pérez");

        cliente = new Cliente();
    }

    @Test
    @DisplayName("Debería crear un cliente con valores por defecto")
    void deberiaCrearClienteConValoresPorDefecto() {
        // Arrange - ya configurado en setUp()

        // Act
        Cliente nuevoCliente = new Cliente();

        // Assert
        assertNotNull(nuevoCliente);
        assertNull(nuevoCliente.getClienteid());
        assertNull(nuevoCliente.getPersona());
        assertNull(nuevoCliente.getContrasena());
        assertTrue(nuevoCliente.getEstado()); // El estado por defecto debe ser true
    }

    @Test
    @DisplayName("Debería crear y configurar un cliente completo")
    void deberiaCrearYConfigurarClienteCompleto() {
        // Arrange
        Long expectedId = 1L;
        String expectedContrasena = "miContrasena123";
        Boolean expectedEstado = true;

        // Act
        cliente.setClienteid(expectedId);
        cliente.setPersona(persona);
        cliente.setContrasena(expectedContrasena);
        cliente.setEstado(expectedEstado);

        // Assert
        assertEquals(expectedId, cliente.getClienteid());
        assertEquals(persona, cliente.getPersona());
        assertEquals(expectedContrasena, cliente.getContrasena());
        assertEquals(expectedEstado, cliente.getEstado());
    }

    @Test
    @DisplayName("Debería crear cliente usando constructor con parámetros")
    void deberiaCrearClienteConConstructorParametros() {
        // Arrange
        Long expectedId = 2L;
        String expectedContrasena = "password456";
        Boolean expectedEstado = false;

        // Act
        Cliente clienteConParametros = new Cliente(expectedId, persona, expectedContrasena, expectedEstado);

        // Assert
        assertEquals(expectedId, clienteConParametros.getClienteid());
        assertEquals(persona, clienteConParametros.getPersona());
        assertEquals(expectedContrasena, clienteConParametros.getContrasena());
        assertEquals(expectedEstado, clienteConParametros.getEstado());
    }

    @Test
    @DisplayName("Debería permitir cambiar el estado del cliente")
    void deberiaPermitirCambiarEstadoDelCliente() {
        // Arrange
        cliente.setEstado(true);

        // Act
        cliente.setEstado(false);

        // Assert
        assertFalse(cliente.getEstado());

        // Act - cambiar de nuevo
        cliente.setEstado(true);

        // Assert
        assertTrue(cliente.getEstado());
    }

    @Test
    @DisplayName("Debería manejar valores nulos apropiadamente")
    void deberiaManejarValoresNulosApropiadamente() {
        // Act
        cliente.setPersona(null);
        cliente.setContrasena(null);
        cliente.setEstado(null);

        // Assert
        assertNull(cliente.getPersona());
        assertNull(cliente.getContrasena());
        assertNull(cliente.getEstado());
    }
}