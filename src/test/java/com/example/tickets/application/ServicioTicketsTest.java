package com.example.tickets.application;

import com.example.tickets.domain.*;
import com.example.tickets.domain.EstadoTicketUpd;
import com.example.tickets.infrastructure.RepositorioTickets;
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
class ServicioTicketsImplTest {

    @Mock
    private RepositorioTickets repositorioTickets;

    @InjectMocks
    private ServicioTicketsImpl servicioTickets;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket("Error en login", "No puedo iniciar sesión", LocalDateTime.now().plusDays(7));
        ticket.setId(1L);
    }

    @Test
    void crearTicket_DeberiaRetornarTicketGuardado() {
        when(repositorioTickets.guardar(any(Ticket.class))).thenReturn(ticket);

        Ticket resultado = servicioTickets.crearTicket(ticket);

        assertNotNull(resultado);
        assertEquals("Error en login", resultado.getTitulo());
        verify(repositorioTickets, times(1)).guardar(any(Ticket.class));
    }

    @Test
    void obtenerTodosLosTickets_DeberiaRetornarListaDeTickets() {
        when(repositorioTickets.obtenerTodos()).thenReturn(Arrays.asList(ticket));

        List<Ticket> resultado = servicioTickets.obtenerTodosLosTickets();

        assertEquals(1, resultado.size());
        assertEquals("Error en login", resultado.get(0).getTitulo());
    }

    @Test
    void obtenerTicketPorId_DeberiaRetornarTicketExistente() {
        when(repositorioTickets.obtenerPorId(1L)).thenReturn(Optional.of(ticket));

        Ticket resultado = servicioTickets.obtenerTicketPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void actualizarEstadoTicket_DeberiaCambiarEstadoYComentario() {
        EstadoTicketUpd estadoUpd = EstadoTicketUpd.valueOf(EstadoTicketUpd.EN_PROCESO.name());
        String comentario = "En revisión";

        when(repositorioTickets.obtenerPorId(1L)).thenReturn(Optional.of(ticket));
        when(repositorioTickets.guardar(any(Ticket.class))).thenReturn(ticket);

        Ticket resultado = servicioTickets.actualizarEstadoTicket(1L, estadoUpd, comentario);

        assertEquals(EstadoTicket.EN_PROCESO, resultado.getEstado());
        assertEquals("En revisión", resultado.getComentario());
    }

    @Test
    void marcarComoResuelto_DeberiaActualizarEstadoYComentario() {
        when(repositorioTickets.obtenerPorId(1L)).thenReturn(Optional.of(ticket));
        when(repositorioTickets.guardar(any(Ticket.class))).thenReturn(ticket);

        Ticket resultado = servicioTickets.marcarComoResuelto(1L, "Resuelto correctamente");

        assertEquals(EstadoTicket.RESUELTO, resultado.getEstado());
        assertEquals("Resuelto correctamente", resultado.getComentario());
    }

    @Test
    void marcarComoResuelto_SinComentario_DeberiaAsignarPorDefecto() {
        when(repositorioTickets.obtenerPorId(1L)).thenReturn(Optional.of(ticket));
        when(repositorioTickets.guardar(any(Ticket.class))).thenReturn(ticket);

        Ticket resultado = servicioTickets.marcarComoResuelto(1L, "");

        assertEquals(EstadoTicket.RESUELTO, resultado.getEstado());
        assertEquals("", resultado.getComentario());
    }

    @Test
    void obtenerTicketsNoResueltosMayoresA30Dias_DeberiaRetornarLista() {
        Ticket viejo = new Ticket("Ticket viejo", "Sigue sin resolver", LocalDateTime.now().minusDays(40));
        viejo.setId(2L);
        viejo.setEstado(EstadoTicket.EN_PROCESO);

        when(repositorioTickets.obtenerNoResueltos(30)).thenReturn(Arrays.asList(viejo));

        List<Ticket> resultado = servicioTickets.obtenerTicketsNoResueltosMayoresA30Dias();

        assertEquals(1, resultado.size());
        assertEquals("Ticket viejo", resultado.get(0).getTitulo());
    }
}
