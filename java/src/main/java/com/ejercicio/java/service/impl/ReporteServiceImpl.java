package com.ejercicio.java.service.impl;

import com.ejercicio.java.dto.EntityMapper;
import com.ejercicio.java.dto.ReporteDTO;
import com.ejercicio.java.entity.Cliente;
import com.ejercicio.java.entity.Cuenta;
import com.ejercicio.java.entity.Movimiento;
import com.ejercicio.java.exception.RecursoNoEncontradoException;
import com.ejercicio.java.repository.ClienteRepository;
import com.ejercicio.java.repository.CuentaRepository;
import com.ejercicio.java.repository.MovimientoRepository;
import com.ejercicio.java.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final EntityMapper entityMapper;

    @Override
    public ReporteDTO generarReporteEstadoCuenta(Long clienteid, LocalDate fechaInicio, LocalDate fechaFin) {
        // Buscar el cliente
        Cliente cliente = clienteRepository.findByIdWithPersona(clienteid)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", "id", clienteid));

        // Buscar las cuentas del cliente
        List<Cuenta> cuentas = cuentaRepository.findByClienteidWithClienteAndPersona(clienteid);

        // Convertir fechas a ZonedDateTime
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime fechaInicioZDT = fechaInicio.atStartOfDay(zoneId);
        ZonedDateTime fechaFinZDT = fechaFin.plusDays(1).atStartOfDay(zoneId); // Incluir el día completo de la fecha fin

        // Buscar los movimientos del cliente en el rango de fechas
        List<Movimiento> movimientos = movimientoRepository.findByClienteidAndFechaBetweenOrderByCuentaAndFecha(
                clienteid, fechaInicioZDT, fechaFinZDT);

        // Agrupar los movimientos por cuenta
        Map<Long, List<Movimiento>> movimientosPorCuenta = movimientos.stream()
                .collect(Collectors.groupingBy(mov -> mov.getCuenta().getNumerocuenta()));

        // Crear el DTO de reporte
        List<ReporteDTO.CuentaReporteDTO> cuentasReporte = new ArrayList<>();

        // Procesar cada cuenta y sus movimientos
        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientosCuenta = movimientosPorCuenta.getOrDefault(cuenta.getNumerocuenta(), new ArrayList<>());

            // Convertir los movimientos a DTOs
            List<ReporteDTO.MovimientoReporteDTO> movimientosReporte = movimientosCuenta.stream()
                    .map(entityMapper::toMovimientoReporteDTO)
                    .collect(Collectors.toList());

            // Crear el DTO de la cuenta con sus movimientos
            ReporteDTO.CuentaReporteDTO cuentaReporte = entityMapper.toCuentaReporteDTO(cuenta, movimientosReporte);
            cuentasReporte.add(cuentaReporte);
        }

        // Crear y retornar el reporte final
        return entityMapper.toReporteDTO(cliente, cuentasReporte);
    }
}