package com.ejercicio.java.service;

import com.ejercicio.java.dto.ReporteDTO;
import java.time.LocalDate;

public interface ReporteService {

    ReporteDTO generarReporteEstadoCuenta(Long clienteid, LocalDate fechaInicio, LocalDate fechaFin);
}