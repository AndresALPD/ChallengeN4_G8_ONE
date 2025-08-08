package com.andres.forohub.services;

import com.andres.forohub.dto.DatosActualizacionTopico;
import com.andres.forohub.dto.DatosDetalleTopico;
import com.andres.forohub.dto.DatosListadoTopico;
import com.andres.forohub.dto.DatosRegistroTopico;
import com.andres.forohub.dto.DatosRespuestaTopico;
import com.andres.forohub.entities.Curso;
import com.andres.forohub.entities.Topico;
import com.andres.forohub.entities.Usuario;
import com.andres.forohub.repositories.CursoRepository;
import com.andres.forohub.repositories.TopicoRepository;
import com.andres.forohub.repositories.UsuarioRepository;
import com.andres.forohub.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DatosRespuestaTopico registrarTopico(DatosRegistroTopico datos) {

        // Validar que el autor existe
        Usuario autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado con ID: " + datos.autorId()));

        // Validar que el curso existe
        Curso curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new ValidacionException("Curso no encontrado con ID: " + datos.cursoId()));

        // Validar que el usuario está activo
        if (!autor.getActivo()) {
            throw new ValidacionException("El usuario no está activo");
        }

        // Validar que el curso está activo
        if (!curso.getActivo()) {
            throw new ValidacionException("El curso no está activo");
        }

        // Regla de negocio: No permitir tópicos duplicados
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje");
        }

        // Crear el nuevo tópico
        Topico nuevoTopico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);

        // Guardar en la base de datos
        Topico topicoGuardado = topicoRepository.save(nuevoTopico);

        // Retornar DTO de respuesta
        return new DatosRespuestaTopico(topicoGuardado);
    }

    public List<DatosListadoTopico> listarTodosLosTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream()
                .map(DatosListadoTopico::new)
                .toList();
    }

    public Page<DatosListadoTopico> listarTopicosPaginados(Pageable paginacion) {
        return topicoRepository.findAll(paginacion)
                .map(DatosListadoTopico::new);
    }

    public DatosDetalleTopico obtenerTopicoPorId(Long id) {
        // Validar que el ID no sea nulo
        if (id == null) {
            throw new ValidacionException("El ID del tópico es obligatorio");
        }

        // Buscar el tópico por ID
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No se encontró el tópico con ID: " + id));

        // Retornar DTO con los detalles completos
        return new DatosDetalleTopico(topico);
    }

    public DatosDetalleTopico actualizarTopico(Long id, DatosActualizacionTopico datos) {
        // Validar que el ID no sea nulo
        if (id == null) {
            throw new ValidacionException("El ID del tópico es obligatorio");
        }

        // Verificar que el tópico existe usando Optional.isPresent()
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (!topicoOptional.isPresent()) {
            throw new ValidacionException("No se encontró el tópico con ID: " + id);
        }

        Topico topico = topicoOptional.get();

        // Validar que el autor existe
        Usuario autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado con ID: " + datos.autorId()));

        // Validar que el curso existe
        Curso curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new ValidacionException("Curso no encontrado con ID: " + datos.cursoId()));

        // Validar que el usuario está activo
        if (!autor.getActivo()) {
            throw new ValidacionException("El usuario no está activo");
        }

        // Validar que el curso está activo
        if (!curso.getActivo()) {
            throw new ValidacionException("El curso no está activo");
        }

        // Regla de negocio: No permitir tópicos duplicados (excluyendo el actual)
        if (topicoRepository.existsByTituloAndMensajeAndIdNot(datos.titulo(), datos.mensaje(), id)) {
            throw new ValidacionException("Ya existe otro tópico con el mismo título y mensaje");
        }

        // Actualizar los datos del tópico
        topico.actualizarDatos(datos.titulo(), datos.mensaje(), autor, curso);

        // Guardar los cambios en la base de datos
        Topico topicoActualizado = topicoRepository.save(topico);

        // Retornar DTO con los datos actualizados
        return new DatosDetalleTopico(topicoActualizado);
    }

    public void eliminarTopico(Long id) {
        // Validar que el ID no sea nulo
        if (id == null) {
            throw new ValidacionException("El ID del tópico es obligatorio");
        }

        // Verificar que el tópico existe usando Optional.isPresent()
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (!topicoOptional.isPresent()) {
            throw new ValidacionException("No se encontró el tópico con ID: " + id);
        }

        // Eliminar el tópico de la base de datos usando deleteById
        topicoRepository.deleteById(id);
    }
}