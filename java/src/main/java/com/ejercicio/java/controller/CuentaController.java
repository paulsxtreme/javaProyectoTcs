package com.ejercicio.java.controller;

import com.ejercicio.java.dto.CuentaDTO;
import com.ejercicio.java.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    @GetMapping("/{numerocuenta}")
    public ResponseEntity<CuentaDTO> obtenerCuentaPorId(@PathVariable Long numerocuenta) {
        return ResponseEntity.ok(cuentaService.obtenerCuentaPorId(numerocuenta));
    }

    @GetMapping("/cliente/{clienteid}")
    public ResponseEntity<List<CuentaDTO>> listarCuentasPorCliente(@PathVariable Long clienteid) {
        return ResponseEntity.ok(cuentaService.listarCuentasPorCliente(clienteid));
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> crearCuenta(@Valid @RequestBody CuentaDTO cuentaDTO) {
        return new ResponseEntity<>(cuentaService.crearCuenta(cuentaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{numerocuenta}")
    public ResponseEntity<CuentaDTO> actualizarCuenta(
            @PathVariable Long numerocuenta,
            @Valid @RequestBody CuentaDTO cuentaDTO) {
        return ResponseEntity.ok(cuentaService.actualizarCuenta(numerocuenta, cuentaDTO));
    }

    @DeleteMapping("/{numerocuenta}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long numerocuenta) {
        cuentaService.eliminarCuenta(numerocuenta);
        return ResponseEntity.noContent().build();
    }
}