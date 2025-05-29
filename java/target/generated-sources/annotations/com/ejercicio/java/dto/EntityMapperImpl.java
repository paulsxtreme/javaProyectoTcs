package com.ejercicio.java.dto;

import com.ejercicio.java.entity.Cliente;
import com.ejercicio.java.entity.Cuenta;
import com.ejercicio.java.entity.Movimiento;
import com.ejercicio.java.entity.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-29T16:42:18-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public PersonaDTO toPersonaDTO(Persona persona) {
        if ( persona == null ) {
            return null;
        }

        PersonaDTO personaDTO = new PersonaDTO();

        personaDTO.setId( persona.getId() );
        personaDTO.setNombre( persona.getNombre() );
        personaDTO.setGenero( persona.getGenero() );
        personaDTO.setEdad( persona.getEdad() );
        personaDTO.setIdentificacion( persona.getIdentificacion() );
        personaDTO.setDireccion( persona.getDireccion() );
        personaDTO.setTelefono( persona.getTelefono() );
        personaDTO.setVersion( persona.getVersion() );

        return personaDTO;
    }

    @Override
    public Persona toPersona(PersonaDTO personaDTO) {
        if ( personaDTO == null ) {
            return null;
        }

        Persona persona = new Persona();

        persona.setId( personaDTO.getId() );
        persona.setNombre( personaDTO.getNombre() );
        persona.setGenero( personaDTO.getGenero() );
        persona.setEdad( personaDTO.getEdad() );
        persona.setIdentificacion( personaDTO.getIdentificacion() );
        persona.setDireccion( personaDTO.getDireccion() );
        persona.setTelefono( personaDTO.getTelefono() );
        persona.setVersion( personaDTO.getVersion() );

        return persona;
    }

    @Override
    public void updatePersonaFromDTO(PersonaDTO personaDTO, Persona persona) {
        if ( personaDTO == null ) {
            return;
        }

        persona.setId( personaDTO.getId() );
        persona.setNombre( personaDTO.getNombre() );
        persona.setGenero( personaDTO.getGenero() );
        persona.setEdad( personaDTO.getEdad() );
        persona.setIdentificacion( personaDTO.getIdentificacion() );
        persona.setDireccion( personaDTO.getDireccion() );
        persona.setTelefono( personaDTO.getTelefono() );
        persona.setVersion( personaDTO.getVersion() );
    }

    @Override
    public ClienteDTO toClienteDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setPersona( toPersonaDTO( cliente.getPersona() ) );
        clienteDTO.setClienteid( cliente.getClienteid() );
        clienteDTO.setContrasena( cliente.getContrasena() );
        clienteDTO.setEstado( cliente.getEstado() );

        return clienteDTO;
    }

    @Override
    public Cliente toCliente(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setPersona( toPersona( clienteDTO.getPersona() ) );
        cliente.setClienteid( clienteDTO.getClienteid() );
        cliente.setContrasena( clienteDTO.getContrasena() );
        cliente.setEstado( clienteDTO.getEstado() );

        return cliente;
    }

    @Override
    public void updateClienteFromDTO(ClienteDTO clienteDTO, Cliente cliente) {
        if ( clienteDTO == null ) {
            return;
        }

        cliente.setClienteid( clienteDTO.getClienteid() );
        if ( clienteDTO.getPersona() != null ) {
            if ( cliente.getPersona() == null ) {
                cliente.setPersona( new Persona() );
            }
            updatePersonaFromDTO( clienteDTO.getPersona(), cliente.getPersona() );
        }
        else {
            cliente.setPersona( null );
        }
        cliente.setContrasena( clienteDTO.getContrasena() );
        cliente.setEstado( clienteDTO.getEstado() );
    }

    @Override
    public CuentaDTO toCuentaDTO(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }

        CuentaDTO cuentaDTO = new CuentaDTO();

        cuentaDTO.setClienteid( cuentaClienteClienteid( cuenta ) );
        cuentaDTO.setNombreCliente( cuentaClientePersonaNombre( cuenta ) );
        cuentaDTO.setNumerocuenta( cuenta.getNumerocuenta() );
        cuentaDTO.setTipocuenta( cuenta.getTipocuenta() );
        cuentaDTO.setSaldoinicial( cuenta.getSaldoinicial() );
        cuentaDTO.setEstado( cuenta.getEstado() );

        return cuentaDTO;
    }

    @Override
    public Cuenta toCuenta(CuentaDTO cuentaDTO) {
        if ( cuentaDTO == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setCliente( cuentaDTOToCliente( cuentaDTO ) );
        cuenta.setNumerocuenta( cuentaDTO.getNumerocuenta() );
        cuenta.setTipocuenta( cuentaDTO.getTipocuenta() );
        cuenta.setSaldoinicial( cuentaDTO.getSaldoinicial() );
        cuenta.setEstado( cuentaDTO.getEstado() );

        return cuenta;
    }

    @Override
    public List<CuentaDTO> toCuentaDTOList(List<Cuenta> cuentas) {
        if ( cuentas == null ) {
            return null;
        }

        List<CuentaDTO> list = new ArrayList<CuentaDTO>( cuentas.size() );
        for ( Cuenta cuenta : cuentas ) {
            list.add( toCuentaDTO( cuenta ) );
        }

        return list;
    }

    @Override
    public MovimientoDTO toMovimientoDTO(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }

        MovimientoDTO movimientoDTO = new MovimientoDTO();

        movimientoDTO.setNumerocuenta( movimientoCuentaNumerocuenta( movimiento ) );
        movimientoDTO.setNombreCliente( movimientoCuentaClientePersonaNombre( movimiento ) );
        movimientoDTO.setTipoCuenta( movimientoCuentaTipocuenta( movimiento ) );
        movimientoDTO.setId( movimiento.getId() );
        movimientoDTO.setFecha( movimiento.getFecha() );
        movimientoDTO.setTipomovimiento( movimiento.getTipomovimiento() );
        movimientoDTO.setValor( movimiento.getValor() );
        movimientoDTO.setSaldo( movimiento.getSaldo() );

        return movimientoDTO;
    }

    @Override
    public Movimiento toMovimiento(MovimientoDTO movimientoDTO) {
        if ( movimientoDTO == null ) {
            return null;
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setCuenta( movimientoDTOToCuenta( movimientoDTO ) );
        movimiento.setId( movimientoDTO.getId() );
        movimiento.setFecha( movimientoDTO.getFecha() );
        movimiento.setTipomovimiento( movimientoDTO.getTipomovimiento() );
        movimiento.setValor( movimientoDTO.getValor() );
        movimiento.setSaldo( movimientoDTO.getSaldo() );

        return movimiento;
    }

    @Override
    public List<MovimientoDTO> toMovimientoDTOList(List<Movimiento> movimientos) {
        if ( movimientos == null ) {
            return null;
        }

        List<MovimientoDTO> list = new ArrayList<MovimientoDTO>( movimientos.size() );
        for ( Movimiento movimiento : movimientos ) {
            list.add( toMovimientoDTO( movimiento ) );
        }

        return list;
    }

    @Override
    public ReporteDTO toReporteDTO(Cliente cliente, List<ReporteDTO.CuentaReporteDTO> cuentas) {
        if ( cliente == null && cuentas == null ) {
            return null;
        }

        ReporteDTO reporteDTO = new ReporteDTO();

        if ( cliente != null ) {
            reporteDTO.setNombreCliente( clientePersonaNombre( cliente ) );
            reporteDTO.setIdentificacion( clientePersonaIdentificacion( cliente ) );
        }
        List<ReporteDTO.CuentaReporteDTO> list = cuentas;
        if ( list != null ) {
            reporteDTO.setCuentas( new ArrayList<ReporteDTO.CuentaReporteDTO>( list ) );
        }

        return reporteDTO;
    }

    @Override
    public ReporteDTO.CuentaReporteDTO toCuentaReporteDTO(Cuenta cuenta, List<ReporteDTO.MovimientoReporteDTO> movimientos) {
        if ( cuenta == null && movimientos == null ) {
            return null;
        }

        ReporteDTO.CuentaReporteDTO cuentaReporteDTO = new ReporteDTO.CuentaReporteDTO();

        if ( cuenta != null ) {
            cuentaReporteDTO.setNumeroCuenta( cuenta.getNumerocuenta() );
            cuentaReporteDTO.setTipoCuenta( cuenta.getTipocuenta() );
            cuentaReporteDTO.setSaldoDisponible( cuenta.getSaldoinicial() );
        }
        List<ReporteDTO.MovimientoReporteDTO> list = movimientos;
        if ( list != null ) {
            cuentaReporteDTO.setMovimientos( new ArrayList<ReporteDTO.MovimientoReporteDTO>( list ) );
        }

        return cuentaReporteDTO;
    }

    @Override
    public ReporteDTO.MovimientoReporteDTO toMovimientoReporteDTO(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }

        ReporteDTO.MovimientoReporteDTO movimientoReporteDTO = new ReporteDTO.MovimientoReporteDTO();

        movimientoReporteDTO.setFecha( movimiento.getFecha() );
        movimientoReporteDTO.setTipoMovimiento( movimiento.getTipomovimiento() );
        movimientoReporteDTO.setValor( movimiento.getValor() );
        movimientoReporteDTO.setSaldo( movimiento.getSaldo() );

        return movimientoReporteDTO;
    }

    private Long cuentaClienteClienteid(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }
        Cliente cliente = cuenta.getCliente();
        if ( cliente == null ) {
            return null;
        }
        Long clienteid = cliente.getClienteid();
        if ( clienteid == null ) {
            return null;
        }
        return clienteid;
    }

    private String cuentaClientePersonaNombre(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }
        Cliente cliente = cuenta.getCliente();
        if ( cliente == null ) {
            return null;
        }
        Persona persona = cliente.getPersona();
        if ( persona == null ) {
            return null;
        }
        String nombre = persona.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    protected Cliente cuentaDTOToCliente(CuentaDTO cuentaDTO) {
        if ( cuentaDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setClienteid( cuentaDTO.getClienteid() );

        return cliente;
    }

    private Long movimientoCuentaNumerocuenta(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }
        Cuenta cuenta = movimiento.getCuenta();
        if ( cuenta == null ) {
            return null;
        }
        Long numerocuenta = cuenta.getNumerocuenta();
        if ( numerocuenta == null ) {
            return null;
        }
        return numerocuenta;
    }

    private String movimientoCuentaClientePersonaNombre(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }
        Cuenta cuenta = movimiento.getCuenta();
        if ( cuenta == null ) {
            return null;
        }
        Cliente cliente = cuenta.getCliente();
        if ( cliente == null ) {
            return null;
        }
        Persona persona = cliente.getPersona();
        if ( persona == null ) {
            return null;
        }
        String nombre = persona.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private String movimientoCuentaTipocuenta(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }
        Cuenta cuenta = movimiento.getCuenta();
        if ( cuenta == null ) {
            return null;
        }
        String tipocuenta = cuenta.getTipocuenta();
        if ( tipocuenta == null ) {
            return null;
        }
        return tipocuenta;
    }

    protected Cuenta movimientoDTOToCuenta(MovimientoDTO movimientoDTO) {
        if ( movimientoDTO == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setNumerocuenta( movimientoDTO.getNumerocuenta() );

        return cuenta;
    }

    private String clientePersonaNombre(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }
        Persona persona = cliente.getPersona();
        if ( persona == null ) {
            return null;
        }
        String nombre = persona.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private String clientePersonaIdentificacion(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }
        Persona persona = cliente.getPersona();
        if ( persona == null ) {
            return null;
        }
        String identificacion = persona.getIdentificacion();
        if ( identificacion == null ) {
            return null;
        }
        return identificacion;
    }
}
