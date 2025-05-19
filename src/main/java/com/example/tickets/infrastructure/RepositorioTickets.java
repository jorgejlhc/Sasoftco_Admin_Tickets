package com.example.tickets.infrastructure;

import com.example.tickets.domain.Ticket;
import java.util.List;
import java.util.Optional;

public interface RepositorioTickets {
    Ticket guardar(Ticket ticket);

    Optional<Ticket> obtenerPorId(Long id);

    List<Ticket> obtenerTodos();

    List<Ticket> obtenerNoResueltosMasAntiguosQueDias(int dias);
}