package com.ejercicio.java.service.impl;

import com.ejercicio.java.dto.EntityMapper;
import com.ejercicio.java.dto.MovimientoDTO;
import com.ejercicio.java.entity.Cuenta;
import com.ejercicio.java.entity.Movimiento;
import com.ejercicio.java.exception.RecursoNoEncontradoException;
import com.ejercicio.java.exception.SaldoNoDisponibleException;
import com.ejercicio.java.repository.CuentaRepository;
import com.ejercicio.java.repository.MovimientoRepository;
import com.ejercicio.java.service.MovimientoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final EntityMapper entityMapper;

    @Override
    public List<MovimientoDTO> listarMovimientos() {
        return movimientoRepository.findAllWithCuentaAndClienteAndPersona().stream()
                .map(entityMapper::toMovimientoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoDTO obtenerMovimientoPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findByIdWithCuentaAndClienteAndPersona(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Movimiento", "id", id));

        return entityMapper.toMovimientoDTO(movimiento);
    }

    @Override
    public List<MovimientoDTO> listarMovimientosPorCuenta(Long numerocuenta) {
        // Verificar si la cuenta existe
        if (!cuentaRepository.existsById(numerocuenta)) {
            throw new RecursoNoEncontradoException("Cuenta", "numerocuenta", numerocuenta);
        }

        return movimientoRepository.findByCuentaNumerocuenta(numerocuenta).stream()
                .map(entityMapper::toMovimientoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoDTO registrarMovimiento(MovimientoDTO movimientoDTO) {
        // Buscar la cuenta
        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getNumerocuenta())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta", "numerocuenta", movimientoDTO.getNumerocuenta()));

        // Obtener el saldo actual de la cuenta
        BigDecimal saldoActual = obtenerSaldoActual(cuenta);

        // Determinar el tipo de movimiento y validar el saldo
        String tipoMovimiento = movimientoDTO.getTipomovimiento();
        BigDecimal valor = movimientoDTO.getValor().abs(); // Siempre usar el valor absoluto
        BigDecimal nuevoSaldo;

        if ("DEBITO".equals(tipoMovimiento)) {
            // Para débito, el valor debe ser negativo en la base de datos
            valor = valor.negate();

            // Verificar si hay saldo suficiente
            if (saldoActual.add(valor).compareTo(BigDecimal.ZERO) < 0) {
                throw new SaldoNoDisponibleException();
            }

            nuevoSaldo = saldoActual.add(valor);
        } else if ("CREDITO".equals(tipoMovimiento)) {
            // Para crédito, el valor es positivo
            nuevoSaldo = saldoActual.add(valor);
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido. Debe ser DEBITO o CREDITO.");
        }

        // Crear el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setFecha(ZonedDateTime.now());
        movimiento.setTipomovimiento(tipoMovimiento);
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);

        // Guardar el movimiento
        movimiento = movimientoRepository.save(movimiento);

        return entityMapper.toMovimientoDTO(movimiento);
    }

    @Override
    @Transactional
    public MovimientoDTO actualizarMovimiento(Long id, MovimientoDTO movimientoDTO) {
        // Este método es más complejo porque implica recalcular saldos.
        // Por simplicidad, no permitiremos actualizar ciertos campos.

        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Movimiento", "id", id));

        // Solo permitiremos actualizar el tipo de movimiento, pero no el valor o la cuenta
        // para evitar inconsistencias en los saldos.
        movimientoExistente.setTipomovimiento(movimientoDTO.getTipomovimiento());

        // Guardar los cambios
        Movimiento movimientoActualizado = movimientoRepository.save(movimientoExistente);

        return entityMapper.toMovimientoDTO(movimientoActualizado);
    }

    @Override
    @Transactional
    public void eliminarMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Movimiento", "id", id);
        }

        movimientoRepository.deleteById(id);

        // Nota: en un sistema real, deberíamos recalcular los saldos de todos los movimientos
        // posteriores a este, pero por simplicidad no lo haremos en este ejemplo.
    }

    /**
     * Obtiene el saldo actual de la cuenta basado en su saldo inicial y los movimientos registrados.
     *
     * @param cuenta La cuenta para la que se obtiene el saldo.
     * @return El saldo actual de la cuenta.
     */
    private BigDecimal obtenerSaldoActual(Cuenta cuenta) {
        // Obtener el último movimiento de la cuenta, si existe
        return movimientoRepository.findTopByCuentaNumerocuentaOrderByFechaDesc(cuenta.getNumerocuenta())
                .map(Movimiento::getSaldo)
                .orElse(cuenta.getSaldoinicial());
    }
}