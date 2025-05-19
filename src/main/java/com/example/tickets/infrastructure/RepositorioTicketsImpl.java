package com.example.tickets.infrastructure;

import com.example.tickets.domain.Ticket;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositorioTicketsImpl implements RepositorioTickets {

    private final RepositorioTicketsJPA repositorioTicketsJPA;

    public RepositorioTicketsImpl(RepositorioTicketsJPA repositorioTicketsJPA) {
        this.repositorioTicketsJPA = repositorioTicketsJPA;
    }

    @Override
    public Ticket guardar(Ticket ticket) {
        return repositorioTicketsJPA.save(ticket);
    }

    @Override
    public Optional<Ticket> obtenerPorId(Long id) {
        return repositorioTicketsJPA.findById(id);
    }

    @Override
    public List<Ticket> obtenerTodos() {
        return repositorioTicketsJPA.findAll();
    }

    @Override
    public List<Ticket> obtenerNoResueltosMasAntiguosQueDias(int dias) {
        return repositorioTicketsJPA.obtenerNoResueltosMasAntiguosQueDias(dias);
    }
}