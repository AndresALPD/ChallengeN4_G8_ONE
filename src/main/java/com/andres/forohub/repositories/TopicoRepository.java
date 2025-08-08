package com.andres.forohub.repositories;

import com.andres.forohub.entities.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Método para verificar duplicados - implementa la regla de negocio
    @Query("SELECT COUNT(t) > 0 FROM Topico t WHERE t.titulo = :titulo AND t.mensaje = :mensaje")
    boolean existsByTituloAndMensaje(@Param("titulo") String titulo, @Param("mensaje") String mensaje);

    // Método para verificar duplicados excluyendo el tópico actual (para actualización)
    @Query("SELECT COUNT(t) > 0 FROM Topico t WHERE t.titulo = :titulo AND t.mensaje = :mensaje AND t.id != :id")
    boolean existsByTituloAndMensajeAndIdNot(@Param("titulo") String titulo, @Param("mensaje") String mensaje, @Param("id") Long id);
}