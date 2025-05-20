package com.example.tickets.application;

import com.example.tickets.domain.*;
import com.example.tickets.infrastructure.RepositorioTickets;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioTicketsImpl implements ServicioTickets {

    private final RepositorioTickets repositorioTickets;

    public ServicioTicketsImpl(RepositorioTickets repositorioTickets) {
        this.repositorioTickets = repositorioTickets;
    }

    @Override
    public Ticket crearTicket(Ticket ticket) {
        return repositorioTickets.guardar(ticket);
    }

    @Override
    public List<Ticket> obtenerTodosLosTickets() {
        return repositorioTickets.obtenerTodos();
    }

    @Override
    public Ticket obtenerTicketPorId(Long id) {
        return repositorioTickets.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    }

    @Override
    public Ticket actualizarEstadoTicket(Long id, EstadoTicketUpd estado, String comentario) {
        EstadoTicket estadoTicket = EstadoTicket.valueOf(estado.name());
        Ticket ticket = obtenerTicketPorId(id);
        ticket.setEstado(estadoTicket);
        ticket.setComentario(comentario);
        return repositorioTickets.guardar(ticket);
    }

    @Override
    public Ticket marcarComoResuelto(Long id, String comentario) {
        Ticket ticket = obtenerTicketPorId(id);
        ticket.marcarComoResuelto(comentario);
        return repositorioTickets.guardar(ticket);
    }

    @Override
    public List<Ticket> obtenerTicketsNoResueltosMayoresA30Dias() {
        return repositorioTickets.obtenerNoResueltos(30);
    }
}