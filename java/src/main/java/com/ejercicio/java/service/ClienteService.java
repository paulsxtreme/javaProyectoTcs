package com.ejercicio.java.service;

import com.ejercicio.java.dto.ClienteDTO;
import java.util.List;

public interface ClienteService {

    List<ClienteDTO> listarClientes();

    ClienteDTO obtenerClientePorId(Long clienteid);

    ClienteDTO crearCliente(ClienteDTO clienteDTO);

    ClienteDTO actualizarCliente(Long clienteid, ClienteDTO clienteDTO);

    void eliminarCliente(Long clienteid);
}