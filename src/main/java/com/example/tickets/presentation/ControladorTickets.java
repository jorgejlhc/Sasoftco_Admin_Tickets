package com.example.tickets.presentation;

import com.example.tickets.application.ServicioTickets;
import com.example.tickets.domain.Ticket;
import com.example.tickets.domain.EstadoTicketUpd;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "API para la gestión de tickets del sistema")
public class ControladorTickets {

    private final ServicioTickets servicioTickets;

    public ControladorTickets(ServicioTickets servicioTickets) {
        this.servicioTickets = servicioTickets;
    }

    @Operation(summary = "Crear un nuevo ticket", description = "Registra un nuevo ticket en el sistema")
    @ApiResponse(responseCode = "200", description = "Ticket creado exitosamente", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @PostMapping
    public ResponseEntity<Ticket> crearTicket(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del ticket a crear", required = true, content = @Content(schema = @Schema(implementation = Ticket.class))) @RequestBody Ticket ticket) {
        return ResponseEntity.ok(servicioTickets.crearTicket(ticket));
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
        return ResponseEntity.ok(servicioTickets.obtenerTicketPorId(id));
    }

    @Operation(summary = "Actualizar estado de un ticket", description = "Modifica el estado de un ticket existente")
    @ApiResponse(responseCode = "200", description = "Estado del ticket actualizado", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstadoTicket(
            @Parameter(description = "ID del ticket a actualizar", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Nuevo estado del ticket", required = true) @RequestParam EstadoTicketUpd estado,
            @Parameter(description = "Comentario de resolución", required = true) @RequestParam String comentario) {
        return ResponseEntity.ok(servicioTickets.actualizarEstadoTicket(id, estado, comentario));
    }

    @Operation(summary = "Marcar ticket como resuelto", description = "Cambia el estado de un ticket a RESUELTO y agrega un comentario")
    @ApiResponse(responseCode = "200", description = "Ticket marcado como resuelto", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    @ApiResponse(responseCode = "500", description = "No fue posible realizar el proceso solicitado")
    @PatchMapping("/{id}/resolver")
    public ResponseEntity<Ticket> marcarComoResuelto(
            @Parameter(description = "ID del ticket a resolver", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Comentario de resolución", required = false) @RequestParam Optional<String> comentario) {
        return ResponseEntity.ok(servicioTickets.marcarComoResuelto(id, comentario));
    }

    @Operation(summary = "Obtener tickets no resueltos antiguos", description = "Retorna tickets no resueltos con más de 30 días de creación")
    @ApiResponse(responseCode = "200", description = "Lista de tickets no resueltos antiguos", content = @Content(schema = @Schema(implementation = Ticket.class)))
    @GetMapping("/no-resueltos-antiguos")
    public ResponseEntity<List<Ticket>> obtenerTicketsNoResueltosAntiguos() {
        return ResponseEntity.ok(servicioTickets.obtenerTicketsNoResueltosMayoresA30Dias());
    }
}