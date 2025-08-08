package com.andres.forohub.dto;

import com.andres.forohub.entities.Topico;
import com.andres.forohub.enums.StatusTopico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        Long autorId,
        String nombreAutor,
        String emailAutor,
        Long cursoId,
        String nombreCurso,
        String categoriaCurso
) {
    public DatosDetalleTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getId(),
                topico.getAutor().getNombre(),
                topico.getAutor().getEmail(),
                topico.getCurso().getId(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
    }
}