package com.ejercicio.java.service.impl;

import com.ejercicio.java.dto.CuentaDTO;
import com.ejercicio.java.dto.EntityMapper;
import com.ejercicio.java.entity.Cliente;
import com.ejercicio.java.entity.Cuenta;
import com.ejercicio.java.exception.RecursoNoEncontradoException;
import com.ejercicio.java.repository.ClienteRepository;
import com.ejercicio.java.repository.CuentaRepository;
import com.ejercicio.java.service.CuentaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final EntityMapper entityMapper;

    @Override
    public List<CuentaDTO> listarCuentas() {
        return cuentaRepository.findAllWithClienteAndPersona().stream()
                .map(entityMapper::toCuentaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaDTO obtenerCuentaPorId(Long numerocuenta) {
        Cuenta cuenta = cuentaRepository.findByIdWithClienteAndPersona(numerocuenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta", "numerocuenta", numerocuenta));

        return entityMapper.toCuentaDTO(cuenta);
    }

    @Override
    public List<CuentaDTO> listarCuentasPorCliente(Long clienteid) {
        return cuentaRepository.findByClienteidWithClienteAndPersona(clienteid).stream()
                .map(entityMapper::toCuentaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CuentaDTO crearCuenta(CuentaDTO cuentaDTO) {
        // Verificar si el cliente existe
        Cliente cliente = clienteRepository.findById(cuentaDTO.getClienteid())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", "id", cuentaDTO.getClienteid()));

        // Convertir DTO a entidad
        Cuenta cuenta = entityMapper.toCuenta(cuentaDTO);

        // Configurar la relación con el cliente
        cuenta.setCliente(cliente);

        // Guardar la cuenta
        cuenta = cuentaRepository.save(cuenta);

        return entityMapper.toCuentaDTO(cuenta);
    }

    @Override
    @Transactional
    public CuentaDTO actualizarCuenta(Long numerocuenta, CuentaDTO cuentaDTO) {
        // Verificar si la cuenta existe
        Cuenta cuentaExistente = cuentaRepository.findById(numerocuenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta", "numerocuenta", numerocuenta));

        // VALIDACIÓN: No permitir cambiar el saldo inicial
        if (cuentaDTO.getSaldoinicial() != null &&
                cuentaExistente.getSaldoinicial().compareTo(cuentaDTO.getSaldoinicial()) != 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser modificado");
        }

        // Si se está actualizando el cliente, verificar que exista
        if (!cuentaExistente.getCliente().getClienteid().equals(cuentaDTO.getClienteid())) {
            Cliente nuevoCliente = clienteRepository.findById(cuentaDTO.getClienteid())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", "id", cuentaDTO.getClienteid()));

            cuentaExistente.setCliente(nuevoCliente);
        }

        // Actualizar solo los campos permitidos (NO el saldo inicial)
        cuentaExistente.setTipocuenta(cuentaDTO.getTipocuenta());
        cuentaExistente.setEstado(cuentaDTO.getEstado());
        // NO actualizar: cuentaExistente.setSaldoinicial(cuentaDTO.getSaldoinicial());

        // Guardar los cambios
        Cuenta cuentaActualizada = cuentaRepository.save(cuentaExistente);

        return entityMapper.toCuentaDTO(cuentaActualizada);
    }

    @Override
    @Transactional
    public void eliminarCuenta(Long numerocuenta) {
        if (!cuentaRepository.existsById(numerocuenta)) {
            throw new RecursoNoEncontradoException("Cuenta", "numerocuenta", numerocuenta);
        }

        cuentaRepository.deleteById(numerocuenta);
    }
}