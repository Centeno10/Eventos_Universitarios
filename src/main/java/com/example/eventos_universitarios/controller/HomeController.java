package com.example.eventos_universitarios.controller; // PAQUETE AJUSTADO

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Inicio");
        return "index"; // Renderiza index.html
    }
}