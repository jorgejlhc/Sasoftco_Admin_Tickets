package com.example.tickets.infrastructure;

import com.example.tickets.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.time.temporal.ChronoUnit;

public interface RepositorioTicketsJPA extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.estado != :estadoResuelto AND t.fechaCreacion < :fechaCorte")
    List<Ticket> findUnresolvedOlderThan(@Param("estadoResuelto") EstadoTicket estadoResuelto,
            @Param("fechaCorte") LocalDateTime fechaCorte);

    default List<Ticket> obtenerNoResueltosMasAntiguosQueDias(int dias) {
        LocalDateTime fechaCorte = LocalDateTime.now().minus(dias, ChronoUnit.DAYS);
        return findUnresolvedOlderThan(EstadoTicket.RESUELTO, fechaCorte);
    }
}
