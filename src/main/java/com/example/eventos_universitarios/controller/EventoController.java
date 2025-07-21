package com.example.eventos_universitarios.controller;

import com.example.eventos_universitarios.model.Evento;
import com.example.eventos_universitarios.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> obtenerTodosLosEventos() {
        return eventoService.obtenerTodosLosEventos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerEventoPorId(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.obtenerEventoPorId(id);
        return evento.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evento> crearEvento(@RequestBody Evento evento) {
        Evento nuevoEvento = eventoService.guardarEvento(evento);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizarEvento(@PathVariable Long id, @RequestBody Evento eventoActualizado) {
        if (!eventoService.existeEvento(id)) {
            return ResponseEntity.notFound().build();
        }
        eventoActualizado.setId(id); // Asegura que el ID del objeto sea el del path
        Evento eventoGuardado = eventoService.guardarEvento(eventoActualizado);
        return ResponseEntity.ok(eventoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        if (!eventoService.existeEvento(id)) {
            return ResponseEntity.notFound().build();
        }
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }
}