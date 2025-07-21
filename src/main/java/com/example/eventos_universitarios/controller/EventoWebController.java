package com.example.eventos_universitarios.controller;

import com.example.eventos_universitarios.model.Evento;
import com.example.eventos_universitarios.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/eventos")
public class EventoWebController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public String listarEventos(Model model) {
        model.addAttribute("eventos", eventoService.obtenerTodosLosEventos());
        model.addAttribute("titulo", "Lista de Eventos");
        return "eventos/lista"; // RENDERIZA lista.html DENTRO DE LA CARPETA 'eventos'
    }

    @GetMapping("/nueva")
    public String mostrarFormularioDeNuevoEvento(Model model) {
        model.addAttribute("evento", new Evento());
        model.addAttribute("titulo", "Nuevo Evento");
        return "eventos/formulario"; // RENDERIZA formulario.html DENTRO DE LA CARPETA 'eventos'
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Evento> evento = eventoService.obtenerEventoPorId(id);
        if (evento.isPresent()) {
            model.addAttribute("evento", evento.get());
            model.addAttribute("titulo", "Editar Evento");
            return "eventos/formulario"; // RENDERIZA formulario.html DENTRO DE LA CARPETA 'eventos'
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Evento no encontrado.");
            return "redirect:/eventos";
        }
    }

    @PostMapping("/guardar")
    public String guardarEvento(@ModelAttribute Evento evento, @RequestParam("fechaStr") String fechaStr, RedirectAttributes redirectAttributes) {
        // Formatear la fecha del String al tipo Date
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = formatter.parse(fechaStr);
            evento.setFecha(fecha);
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("mensaje", "Formato de fecha inválido.");
            return "redirect:/eventos/nueva"; // O a donde corresponda
        }

        eventoService.guardarEvento(evento);
        redirectAttributes.addFlashAttribute("mensaje", "Evento guardado exitosamente.");
        return "redirect:/eventos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (eventoService.existeEvento(id)) {
            eventoService.eliminarEvento(id);
            redirectAttributes.addFlashAttribute("mensaje", "Evento eliminado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Evento no encontrado.");
        }
        return "redirect:/eventos";
    }
}