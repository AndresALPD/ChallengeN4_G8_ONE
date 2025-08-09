-- Crear base de datos (esto se ejecutará automáticamente por la URL)

-- Tabla de usuarios (autores de los tópicos)
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de cursos
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL UNIQUE,
    categoria VARCHAR(100) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de tópicos
CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVO',
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

-- Índices para mejorar rendimiento
CREATE INDEX idx_topicos_autor ON topicos(autor_id);
CREATE INDEX idx_topicos_curso ON topicos(curso_id);
CREATE INDEX idx_topicos_fecha ON topicos(fecha_creacion);
CREATE INDEX idx_topicos_status ON topicos(status);

-- Insertar datos iniciales para pruebas
INSERT INTO usuarios (nombre, email, password) VALUES
    ('Admin Usuario', 'admin@forohub.com', '$2a$12$3K.6vBwVJWrQqJgF8vQ8.eKj4kJ8HnG5J3fJ8rQ9wQ5vJ8rQ9wQ5v'),
    ('Juan Pérez', 'juan.perez@email.com', '$2a$12$3K.6vBwVJWrQqJgF8vQ8.eKj4kJ8HnG5J3fJ8rQ9wQ5vJ8rQ9wQ5v'),
    ('María González', 'maria.gonzalez@email.com', '$2a$12$3K.6vBwVJWrQqJgF8vQ8.eKj4kJ8HnG5J3fJ8rQ9wQ5vJ8rQ9wQ5v');

INSERT INTO cursos (nombre, categoria) VALUES
    ('Spring Boot', 'Backend'),
    ('Java Orientado a Objetos', 'Backend'),
    ('React', 'Frontend'),
    ('MySQL', 'Base de Datos'),
    ('JavaScript', 'Frontend');

INSERT INTO topicos (titulo, mensaje, autor_id, curso_id, status) VALUES
    ('¿Cómo configurar Spring Security?', 'Tengo dudas sobre la configuración básica de Spring Security en mi proyecto.', 2, 1, 'ACTIVO'),
    ('Error con JPA Repository', 'Mi repositorio no está funcionando correctamente, ¿alguna idea?', 3, 1, 'ACTIVO'),
    ('Mejores prácticas en React', 'Me gustaría conocer las mejores prácticas para componentes en React.', 2, 3, 'ACTIVO');
