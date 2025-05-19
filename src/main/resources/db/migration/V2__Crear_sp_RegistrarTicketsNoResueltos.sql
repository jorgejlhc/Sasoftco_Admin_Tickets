CREATE PROCEDURE sp_RegistrarTicketsNoResueltos
AS
BEGIN
    INSERT INTO registro_tickets_no_resueltos (ticket_id, titulo, dias_no_resuelto)
    SELECT id, titulo, DATEDIFF(day, fecha_creacion, GETDATE())
    FROM tickets
    WHERE estado != 'RESUELTO' AND DATEDIFF(day, fecha_creacion, GETDATE()) > 30
    AND id NOT IN (SELECT ticket_id FROM registro_tickets_no_resueltos WHERE DATEDIFF(day, fecha_registro, GETDATE()) = 0)
END