package com.example.tickets.domain.exceptions;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long id) {
        super("No se encontró el ticket con ID: " + id);
    }
}
