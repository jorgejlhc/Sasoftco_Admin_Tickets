package com.example.tickets.application;

import com.example.tickets.domain.*;
import java.util.List;
import java.util.Optional;

public interface ServicioTickets {
    Ticket crearTicket(Ticket ticket);

    List<Ticket> obtenerTodosLosTickets();

    Ticket obtenerTicketPorId(Long id);

    Ticket actualizarEstadoTicket(Long id, EstadoTicketUpd estado, String comentario);

    Ticket marcarComoResuelto(Long id, Optional<String> comentario);

    List<Ticket> obtenerTicketsNoResueltosMayoresA30Dias();
}
