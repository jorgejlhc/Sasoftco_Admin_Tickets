package com.example.tickets.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Entidad que representa un ticket en el sistema")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del ticket", example = "1")
    private Long id;

    @Schema(description = "Título del ticket", example = "Problema con la actualización de datos", required = true)
    private String titulo;

    @Schema(description = "Descripción detallada del problema", example = "El sistema no esta almacenado la dirección del usuario")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado del ticket", example = "ABIERTO", required = true)
    private EstadoTicket estado;

    @Schema(description = "Fecha de creación del ticket", example = "20/05/2025", required = true)
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de actulización del ticket", example = "20/05/2025", required = true)
    private LocalDateTime fechaActualizacion;

    @Schema(description = "Fecha de vencimiento del ticket", example = "20/05/2025", required = true)
    private LocalDateTime fechaVencimiento;

    @Schema(description = "Comentarios adicionales del ticket", example = "El error se está presentando desde la última ventana de mantenimiento", required = true)
    private String comentario;

    public Ticket() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.estado = EstadoTicket.ABIERTO;
    }

    public Ticket(String titulo, String descripcion, LocalDateTime fechaVencimiento) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public void marcarComoResuelto(Optional<String> comentario) {
        if (this.estado != EstadoTicket.RESUELTO) {
            this.estado = EstadoTicket.RESUELTO;

            String valorComentario = comentario.map(String::trim).filter(c -> !c.isEmpty()).orElse("");
            this.comentario = valorComentario;

            this.fechaActualizacion = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
