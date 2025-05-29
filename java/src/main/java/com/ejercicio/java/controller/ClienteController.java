package com.ejercicio.java.controller;

import com.ejercicio.java.dto.ClienteDTO;
import com.ejercicio.java.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * GET /clientes - Obtiene la lista de todos los clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    /**
     * GET /clientes/{clienteid} - Obtiene un cliente específico por su ID
     */
    @GetMapping("/{clienteid}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long clienteid) {
        return ResponseEntity.ok(clienteService.obtenerClientePorId(clienteid));
    }

    /**
     * POST /clientes - Crea un nuevo cliente
     * CORREGIDO: Removido el parámetro {clienteid} porque el ID se genera automáticamente
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);
        return new ResponseEntity<>(clienteCreado, HttpStatus.CREATED);
    }

    /**
     * PUT /clientes/{clienteid} - Actualiza un cliente existente
     * CORREGIDO: Cambiado parámetro de 'id' a 'clienteid' para consistencia
     */
    @PutMapping("/{clienteid}")
    public ResponseEntity<ClienteDTO> actualizarCliente(
            @PathVariable Long clienteid,
            @Valid @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.actualizarCliente(clienteid, clienteDTO));
    }

    /**
     * DELETE /clientes/{clienteid} - Elimina un cliente
     */
    @DeleteMapping("/{clienteid}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long clienteid) {
        clienteService.eliminarCliente(clienteid);
        return ResponseEntity.noContent().build();
    }
}