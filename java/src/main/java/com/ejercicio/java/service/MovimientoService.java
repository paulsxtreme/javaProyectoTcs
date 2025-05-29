package com.ejercicio.java.service;

import com.ejercicio.java.dto.MovimientoDTO;
import java.util.List;

public interface MovimientoService {

    List<MovimientoDTO> listarMovimientos();

    MovimientoDTO obtenerMovimientoPorId(Long id);

    List<MovimientoDTO> listarMovimientosPorCuenta(Long numerocuenta);

    MovimientoDTO registrarMovimiento(MovimientoDTO movimientoDTO);

    MovimientoDTO actualizarMovimiento(Long id, MovimientoDTO movimientoDTO);

    void eliminarMovimiento(Long id);
}