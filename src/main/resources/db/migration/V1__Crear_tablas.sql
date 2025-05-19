CREATE TABLE tickets (
    id BIGINT PRIMARY KEY IDENTITY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_actualizacion DATETIME NOT NULL,
    fecha_vencimiento DATETIME NOT NULL,
    comentario VARCHAR(255)
);

CREATE TABLE registro_tickets_no_resueltos (
    id BIGINT PRIMARY KEY IDENTITY,
    ticket_id BIGINT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    dias_no_resuelto INT NOT NULL,
    fecha_registro DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);