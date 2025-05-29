package com.ejercicio.java.controller;

import com.ejercicio.java.dto.MovimientoDTO;
import com.ejercicio.java.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> listarMovimientos() {
        return ResponseEntity.ok(movimientoService.listarMovimientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtenerMovimientoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerMovimientoPorId(id));
    }

    @GetMapping("/cuenta/{numerocuenta}")
    public ResponseEntity<List<MovimientoDTO>> listarMovimientosPorCuenta(@PathVariable Long numerocuenta) {
        return ResponseEntity.ok(movimientoService.listarMovimientosPorCuenta(numerocuenta));
    }

    @PostMapping
    public ResponseEntity<MovimientoDTO> registrarMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        return new ResponseEntity<>(movimientoService.registrarMovimiento(movimientoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDTO> actualizarMovimiento(
            @PathVariable Long id,
            @Valid @RequestBody MovimientoDTO movimientoDTO) {
        return ResponseEntity.ok(movimientoService.actualizarMovimiento(id, movimientoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}