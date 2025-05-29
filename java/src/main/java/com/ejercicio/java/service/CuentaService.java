package com.ejercicio.java.service;

import com.ejercicio.java.dto.CuentaDTO;
import java.util.List;

public interface CuentaService {

    List<CuentaDTO> listarCuentas();

    CuentaDTO obtenerCuentaPorId(Long numerocuenta);

    List<CuentaDTO> listarCuentasPorCliente(Long clienteid);

    CuentaDTO crearCuenta(CuentaDTO cuentaDTO);

    CuentaDTO actualizarCuenta(Long numerocuenta, CuentaDTO cuentaDTO);

    void eliminarCuenta(Long numerocuenta);
}