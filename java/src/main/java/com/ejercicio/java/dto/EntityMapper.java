package com.ejercicio.java.dto;
//llamar a los archivos entity
import com.ejercicio.java.entity.Cliente;
import com.ejercicio.java.entity.Cuenta;
import com.ejercicio.java.entity.Movimiento;
import com.ejercicio.java.entity.Persona;
//dependencias de mapstruct mapper
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    // Persona mappings
    PersonaDTO toPersonaDTO(Persona persona);
    Persona toPersona(PersonaDTO personaDTO);
    void updatePersonaFromDTO(PersonaDTO personaDTO, @MappingTarget Persona persona);

    // Cliente mappings
    @Mapping(target = "persona", source = "persona")
    ClienteDTO toClienteDTO(Cliente cliente);

    @Mapping(target = "persona", source = "persona")
    Cliente toCliente(ClienteDTO clienteDTO);

    void updateClienteFromDTO(ClienteDTO clienteDTO, @MappingTarget Cliente cliente);

    // Cuenta mappings
    @Mapping(target = "clienteid", source = "cliente.clienteid")
    @Mapping(target = "nombreCliente", source = "cliente.persona.nombre")
    CuentaDTO toCuentaDTO(Cuenta cuenta);

    @Mapping(target = "cliente.clienteid", source = "clienteid")
    Cuenta toCuenta(CuentaDTO cuentaDTO);

    List<CuentaDTO> toCuentaDTOList(List<Cuenta> cuentas);

    // Movimiento mappings
    @Mapping(target = "numerocuenta", source = "cuenta.numerocuenta")
    @Mapping(target = "nombreCliente", source = "cuenta.cliente.persona.nombre")
    @Mapping(target = "tipoCuenta", source = "cuenta.tipocuenta")
    MovimientoDTO toMovimientoDTO(Movimiento movimiento);

    @Mapping(target = "cuenta.numerocuenta", source = "numerocuenta")
    Movimiento toMovimiento(MovimientoDTO movimientoDTO);

    List<MovimientoDTO> toMovimientoDTOList(List<Movimiento> movimientos);

    // Reporte mappings
    @Mapping(target = "nombreCliente", source = "cliente.persona.nombre")
    @Mapping(target = "identificacion", source = "cliente.persona.identificacion")
    @Mapping(target = "cuentas", source = "cuentas")
    ReporteDTO toReporteDTO(Cliente cliente, List<ReporteDTO.CuentaReporteDTO> cuentas);

    //**
    //Convierte una entidad Cuenta y sus movimientos a un DTO CuentaReporteDTO.
    //Esta es una clase interna de ReporteDTO utilizada para los reportes de estado de cuenta.
    //@param cuenta La entidad Cuenta a convertir
    //@param movimientos Lista de movimientos convertidos a DTOs
    //@return Un DTO CuentaReporteDTO para el reporte

    @Mapping(target = "numeroCuenta", source = "cuenta.numerocuenta")
    @Mapping(target = "tipoCuenta", source = "cuenta.tipocuenta")
    @Mapping(target = "saldoDisponible", source = "cuenta.saldoinicial")
    @Mapping(target = "movimientos", source = "movimientos")
    ReporteDTO.CuentaReporteDTO toCuentaReporteDTO(Cuenta cuenta, List<ReporteDTO.MovimientoReporteDTO> movimientos);

    //Convierte una entidad Movimiento a un DTO MovimientoReporteDTO para los reportes.
    //@param movimiento La entidad Movimiento a convertir
    //@return Un DTO MovimientoReporteDTO para el reporte
    @Mapping(target = "fecha", source = "fecha")
    @Mapping(target = "tipoMovimiento", source = "tipomovimiento")
    @Mapping(target = "valor", source = "valor")
    @Mapping(target = "saldo", source = "saldo")
    ReporteDTO.MovimientoReporteDTO toMovimientoReporteDTO(Movimiento movimiento);
}