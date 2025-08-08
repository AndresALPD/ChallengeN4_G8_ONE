package com.andres.forohub.dto;

import com.andres.forohub.entities.Topico;
import com.andres.forohub.enums.StatusTopico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        String nombreAutor,
        String emailAutor,
        String nombreCurso,
        String categoriaCurso
) {
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getAutor().getEmail(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
    }
}