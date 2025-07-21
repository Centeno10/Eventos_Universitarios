package com.example.eventos_universitarios.repository;

import com.example.eventos_universitarios.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Spring Data JPA provee automáticamente los métodos CRUD básicos
    // Puedes añadir métodos de consulta personalizados aquí si los necesitas
}