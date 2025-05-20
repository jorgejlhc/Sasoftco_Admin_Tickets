package com.example.tickets.presentation;

import com.example.tickets.application.ServicioTickets;
import com.example.tickets.domain.Ticket;
import com.example.tickets.domain.EstadoTicketUpd;
import com.example.tickets.domain.exceptions.TicketNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "API para la gestión de tickets del sistema")
public class ControladorTickets {

    private final ServicioTickets servicioTickets;

    public ControladorTickets(ServicioTickets servicioTickets) {
        this.servicioTickets = servicioTickets;
    }

    @Operation(summary = "Crear un nuevo ticket", description = "Registra un nuevo ticket en el sistema")
    @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @PostMapping
    public ResponseEntity<Ticket> crearTicket(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del ticket a crear", required = true, content = @Content(schema = @Schema(implementation = Ticket.class))) @RequestBody Ticket ticket) {
        ticket.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioTickets.crearTicket(ticket));
    }

    @Operation(summary = "Obtener todos los tickets", description = "Retorna una lista completa de todos los tickets existentes")
    @ApiResponse(responseCode = "200", description = "Lista de tickets obtenida exitosamente", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @GetMapping
    public ResponseEntity<List<Ticket>> obtenerTodosLosTickets() {
        return ResponseEntity.ok(servicioTickets.obtenerTodosLosTickets());
    }

    @Operation(summary = "Obtener un ticket por ID", description = "Busca y retorna un ticket específico según su ID único")
    @ApiResponse(responseCode = "200", description = "Ticket encontrado", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(
            @Parameter(description = "ID único del ticket", required = true, example = "1") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(servicioTickets.obtenerTicketPorId(id));
        } catch (TicketNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "Actualizar estado de un ticket", description = "Modifica el estado de un ticket existente")
    @ApiResponse(responseCode = "200", description = "Estado del ticket actualizado", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstadoTicket(
            @Parameter(description = "ID del ticket a actualizar", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Nuevo estado del ticket", required = true) @RequestParam EstadoTicketUpd estado,
            @Parameter(description = "Comentario opcional", required = true) @RequestParam(required = true) String comentario) {
        try {
            return ResponseEntity.ok(servicioTickets.actualizarEstadoTicket(id, estado, comentario));
        } catch (TicketNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "Marcar ticket como resuelto", description = "Cambia el estado de un ticket a RESUELTO")
    @ApiResponse(responseCode = "200", description = "Ticket marcado como resuelto", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    @PatchMapping("/{id}/resolver")
    public ResponseEntity<Ticket> marcarComoResuelto(
            @Parameter(description = "ID del ticket a resolver", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Comentario de resolución", required = false) @RequestParam(required = false) String comentario) {
        try {
            return ResponseEntity.ok(servicioTickets.marcarComoResuelto(id, comentario));
        } catch (TicketNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "Obtener tickets no resueltos antiguos", description = "Retorna tickets no resueltos con más de 30 días de creación")
    @ApiResponse(responseCode = "200", description = "Lista de tickets no resueltos antiguos", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @GetMapping("/no-resueltos-antiguos")
    public ResponseEntity<List<Ticket>> obtenerTicketsNoResueltosAntiguos() {
        return ResponseEntity.ok(servicioTickets.obtenerTicketsNoResueltosMayoresA30Dias());
    }
}