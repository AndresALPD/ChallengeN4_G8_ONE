package com.andres.forohub.controllers;

import com.andres.forohub.dto.DatosActualizacionTopico;
import com.andres.forohub.dto.DatosDetalleTopico;
import com.andres.forohub.dto.DatosListadoTopico;
import com.andres.forohub.dto.DatosRegistroTopico;
import com.andres.forohub.dto.DatosRespuestaTopico;
import com.andres.forohub.services.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriComponentsBuilder) {

        // Llamar al servicio para procesar la lógica de negocio
        DatosRespuestaTopico topicoCreado = topicoService.registrarTopico(datos);

        // Crear la URI del recurso creado
        URI uri = uriComponentsBuilder
                .path("/topicos/{id}")
                .buildAndExpand(topicoCreado.id())
                .toUri();

        // Retornar respuesta 201 (Created) con el tópico creado
        return ResponseEntity.created(uri).body(topicoCreado);
    }

    @GetMapping
    public ResponseEntity<List<DatosListadoTopico>> listarTopicos() {
        List<DatosListadoTopico> topicos = topicoService.listarTodosLosTopicos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicosPaginados(
            @PageableDefault(
                    size = 10,
                    sort = "fechaCreacion",
                    direction = Sort.Direction.DESC
            ) Pageable paginacion) {

        Page<DatosListadoTopico> topicos = topicoService.listarTopicosPaginados(paginacion);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopicoPorId(@PathVariable Long id) {
        DatosDetalleTopico topico = topicoService.obtenerTopicoPorId(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizacionTopico datos) {

        DatosDetalleTopico topicoActualizado = topicoService.actualizarTopico(id, datos);
        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}