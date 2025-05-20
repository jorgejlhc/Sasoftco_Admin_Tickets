package com.example.tickets.presentation;

import com.example.tickets.application.ServicioTickets;
import com.example.tickets.domain.Ticket;
import com.example.tickets.domain.EstadoTicketUpd;
import com.example.tickets.domain.exceptions.TicketNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControladorTicketsTest {

    @Mock
    private ServicioTickets servicioTickets;

    @InjectMocks
    private ControladorTickets controladorTickets;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket("Error en login", "No puedo iniciar sesión", LocalDateTime.now().plusDays(7));
        ticket.setId(1L);
    }

    @Test
    void crearTicket_DeberiaRetornar201ConTicket() {
        when(servicioTickets.crearTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> respuesta = controladorTickets.crearTicket(ticket);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(ticket.getId(), respuesta.getBody().getId());
        verify(servicioTickets, times(1)).crearTicket(any(Ticket.class));
    }

    @Test
    void obtenerTodosLosTickets_DeberiaRetornarLista() {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(servicioTickets.obtenerTodosLosTickets()).thenReturn(tickets);

        ResponseEntity<List<Ticket>> respuesta = controladorTickets.obtenerTodosLosTickets();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        verify(servicioTickets, times(1)).obtenerTodosLosTickets();
    }

    @Test
    void obtenerTicketPorId_DeberiaRetornarTicketCuandoExiste() {
        when(servicioTickets.obtenerTicketPorId(anyLong())).thenReturn(ticket);

        ResponseEntity<Ticket> respuesta = controladorTickets.obtenerTicketPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(ticket.getId(), respuesta.getBody().getId());
        verify(servicioTickets, times(1)).obtenerTicketPorId(anyLong());
    }

    @Test
    void obtenerTicketPorId_DeberiaLanzarExcepcionCuandoNoExiste() {
        when(servicioTickets.obtenerTicketPorId(anyLong()))
                .thenThrow(new TicketNotFoundException(1L));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controladorTickets.obtenerTicketPorId(1L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertTrue(exception.getReason().contains("No se encontró el ticket"));
        verify(servicioTickets, times(1)).obtenerTicketPorId(anyLong());
    }

    @Test
    void actualizarEstadoTicket_DeberiaRetornarTicketActualizado() {
        when(servicioTickets.actualizarEstadoTicket(anyLong(), any(EstadoTicketUpd.class), anyString()))
                .thenReturn(ticket);

        ResponseEntity<Ticket> respuesta = controladorTickets.actualizarEstadoTicket(
                1L, EstadoTicketUpd.EN_PROCESO, "En revisión");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        verify(servicioTickets, times(1))
                .actualizarEstadoTicket(anyLong(), any(EstadoTicketUpd.class), anyString());
    }

    @Test
    void marcarComoResuelto_DeberiaRetornarTicketResuelto() {
        when(servicioTickets.marcarComoResuelto(anyLong(), anyString()))
                .thenReturn(ticket);

        ResponseEntity<Ticket> respuesta = controladorTickets.marcarComoResuelto(1L, "Solucionado");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        verify(servicioTickets, times(1)).marcarComoResuelto(anyLong(), anyString());
    }

    @Test
    void marcarComoResuelto_DeberiaFuncionarSinComentario() {
        when(servicioTickets.marcarComoResuelto(anyLong(), isNull()))
                .thenReturn(ticket);

        ResponseEntity<Ticket> respuesta = controladorTickets.marcarComoResuelto(1L, null);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        verify(servicioTickets, times(1)).marcarComoResuelto(anyLong(), isNull());
    }

    @Test
    void obtenerTicketsNoResueltosAntiguos_DeberiaRetornarLista() {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(servicioTickets.obtenerTicketsNoResueltosMayoresA30Dias()).thenReturn(tickets);

        ResponseEntity<List<Ticket>> respuesta = controladorTickets.obtenerTicketsNoResueltosAntiguos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        verify(servicioTickets, times(1)).obtenerTicketsNoResueltosMayoresA30Dias();
    }
}
