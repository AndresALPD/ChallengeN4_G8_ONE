package com.andres.forohub.entities;

import com.andres.forohub.enums.StatusTopico;
import com.andres.forohub.entities.Curso;
import com.andres.forohub.entities.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.status = StatusTopico.ACTIVO;
        this.autor = autor;
        this.curso = curso;
    }

    public void cerrar() {
        this.status = StatusTopico.CERRADO;
    }

    public void resolver() {
        this.status = StatusTopico.RESUELTO;
    }

    public void activar() {
        this.status = StatusTopico.ACTIVO;
    }

    // Método para verificar si ya existe un tópico duplicado
    public boolean esDuplicado(String titulo, String mensaje) {
        return this.titulo.equals(titulo) && this.mensaje.equals(mensaje);
    }

    // Método para actualizar los datos del tópico
    public void actualizarDatos(String titulo, String mensaje, Usuario autor, Curso curso) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        }
        if (mensaje != null && !mensaje.trim().isEmpty()) {
            this.mensaje = mensaje;
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (curso != null) {
            this.curso = curso;
        }
    }
}