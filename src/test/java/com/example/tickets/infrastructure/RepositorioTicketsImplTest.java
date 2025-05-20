package com.example.tickets.infrastructure;

import com.example.tickets.domain.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositorioTicketsImplTest {

    @Mock
    private RepositorioTicketsJPA repositorioTicketsJPA;

    @InjectMocks
    private RepositorioTicketsImpl repositorioTickets;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket("Error en login", "No puedo iniciar sesión", LocalDateTime.now().plusDays(7));
        ticket.setId(1L);
    }

    @Test
    void guardar_DeberiaRetornarTicketGuardado() {
        // Configurar mock
        when(repositorioTicketsJPA.save(any(Ticket.class))).thenReturn(ticket);

        // Ejecutar
        Ticket resultado = repositorioTickets.guardar(ticket);

        // Verificar
        assertNotNull(resultado);
        assertEquals(ticket.getId(), resultado.getId());
        verify(repositorioTicketsJPA, times(1)).save(ticket);
    }

    @Test
    void obtenerPorId_DeberiaRetornarTicketCuandoExiste() {
        // Configurar mock
        when(repositorioTicketsJPA.findById(1L)).thenReturn(Optional.of(ticket));

        // Ejecutar
        Optional<Ticket> resultado = repositorioTickets.obtenerPorId(1L);

        // Verificar
        assertTrue(resultado.isPresent());
        assertEquals(ticket.getId(), resultado.get().getId());
        verify(repositorioTicketsJPA, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_DeberiaRetornarVacioCuandoNoExiste() {
        // Configurar mock
        when(repositorioTicketsJPA.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar
        Optional<Ticket> resultado = repositorioTickets.obtenerPorId(1L);

        // Verificar
        assertTrue(resultado.isPresent());
        verify(repositorioTicketsJPA, times(1)).findById(1L);
    }

    @Test
    void obtenerTodos_DeberiaRetornarListaDeTickets() {
        // Configurar mock
        List<Ticket> tickets = Arrays.asList(ticket);
        when(repositorioTicketsJPA.findAll()).thenReturn(tickets);

        // Ejecutar
        List<Ticket> resultado = repositorioTickets.obtenerTodos();

        // Verificar
        assertEquals(1, resultado.size());
        verify(repositorioTicketsJPA, times(1)).findAll();
    }

    @Test
    void obtenerNoResueltos_DeberiaLlamarAlMetodoDelJPA() {
        // Configurar mock
        List<Ticket> tickets = Arrays.asList(ticket);
        when(repositorioTicketsJPA.obtenerNoResueltos(30)).thenReturn(tickets);

        // Ejecutar
        List<Ticket> resultado = repositorioTickets.obtenerNoResueltos(30);

        // Verificar
        assertEquals(1, resultado.size());
        verify(repositorioTicketsJPA, times(1)).obtenerNoResueltos(30);
    }
}