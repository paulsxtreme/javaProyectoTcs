package com.ejercicio.java.service.impl;

import com.ejercicio.java.dto.ClienteDTO;
import com.ejercicio.java.dto.EntityMapper;
import com.ejercicio.java.entity.Cliente;
import com.ejercicio.java.entity.Persona;
import com.ejercicio.java.exception.RecursoNoEncontradoException;
import com.ejercicio.java.repository.ClienteRepository;
import com.ejercicio.java.repository.PersonaRepository;
import com.ejercicio.java.service.ClienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final EntityMapper entityMapper;

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAllWithPersona().stream()
                .map(entityMapper::toClienteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long clienteid) {
        Cliente cliente = clienteRepository.findByIdWithPersona(clienteid)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", "id", clienteid));

        return entityMapper.toClienteDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        // Verificar si la identificación ya existe
        if (personaRepository.existsByIdentificacion(clienteDTO.getPersona().getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe una persona con la identificación proporcionada");
        }

        // Crear y guardar la persona primero (MODIFICADO)
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre(clienteDTO.getPersona().getNombre());
        nuevaPersona.setGenero(clienteDTO.getPersona().getGenero());
        nuevaPersona.setEdad(clienteDTO.getPersona().getEdad());
        nuevaPersona.setIdentificacion(clienteDTO.getPersona().getIdentificacion());
        nuevaPersona.setDireccion(clienteDTO.getPersona().getDireccion());
        nuevaPersona.setTelefono(clienteDTO.getPersona().getTelefono());
        nuevaPersona.setVersion(0L); // Inicializar versión

        Persona personaGuardada = personaRepository.save(nuevaPersona);

        // Crear y guardar el cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setPersona(personaGuardada);
        nuevoCliente.setContrasena(clienteDTO.getContrasena());
        nuevoCliente.setEstado(clienteDTO.getEstado() != null ? clienteDTO.getEstado() : true);

        Cliente clienteGuardado = clienteRepository.save(nuevoCliente);

        return entityMapper.toClienteDTO(clienteGuardado);
    }

    @Override
    @Transactional
    public ClienteDTO actualizarCliente(Long clienteid, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findByIdWithPersona(clienteid)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", "id", clienteid));

        // Verificar si se está actualizando la identificación y si ya existe
        if (!clienteExistente.getPersona().getIdentificacion().equals(clienteDTO.getPersona().getIdentificacion()) &&
                personaRepository.existsByIdentificacion(clienteDTO.getPersona().getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe una persona con la identificación proporcionada");
        }

        // Actualizar datos de la persona manualmente
        Persona personaExistente = clienteExistente.getPersona();
        personaExistente.setNombre(clienteDTO.getPersona().getNombre());
        personaExistente.setGenero(clienteDTO.getPersona().getGenero());
        personaExistente.setEdad(clienteDTO.getPersona().getEdad());
        personaExistente.setIdentificacion(clienteDTO.getPersona().getIdentificacion());
        personaExistente.setDireccion(clienteDTO.getPersona().getDireccion());
        personaExistente.setTelefono(clienteDTO.getPersona().getTelefono());
        // NO modificamos el campo version - se actualizará automáticamente

        // Actualizar datos del cliente
        clienteExistente.setContrasena(clienteDTO.getContrasena());
        clienteExistente.setEstado(clienteDTO.getEstado());

        // Guardar los cambios
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);

        return entityMapper.toClienteDTO(clienteActualizado);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long clienteid) {
        if (!clienteRepository.existsById(clienteid)) {
            throw new RecursoNoEncontradoException("Cliente", "id", clienteid);
        }

        clienteRepository.deleteById(clienteid);
    }
}