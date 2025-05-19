package com.example.tickets.presentation;

import com.example.tickets.application.*;
import com.example.tickets.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class ControladorTickets {

    private final ServicioTickets servicioTickets;

    public ControladorTickets(ServicioTickets servicioTickets) {
        this.servicioTickets = servicioTickets;
    }

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(servicioTickets.crearTicket(ticket));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> obtenerTodosLosTickets() {
        return ResponseEntity.ok(servicioTickets.obtenerTodosLosTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioTickets.obtenerTicketPorId(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstadoTicket(
            @PathVariable Long id,
            @RequestParam EstadoTicket estado) {
        return ResponseEntity.ok(servicioTickets.actualizarEstadoTicket(id, estado));
    }

    @PatchMapping("/{id}/resolver")
    public ResponseEntity<Ticket> marcarComoResuelto(
            @PathVariable Long id,
            @RequestParam String comentario) {
        return ResponseEntity.ok(servicioTickets.marcarComoResuelto(id, comentario));
    }

    @GetMapping("/no-resueltos-antiguos")
    public ResponseEntity<List<Ticket>> obtenerTicketsNoResueltosAntiguos() {
        return ResponseEntity.ok(servicioTickets.obtenerTicketsNoResueltosMayoresA30Dias());
    }
}
