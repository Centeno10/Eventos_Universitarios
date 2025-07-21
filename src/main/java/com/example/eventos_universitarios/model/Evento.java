package com.example.eventos_universitarios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data; // Importado de Lombok
import lombok.NoArgsConstructor; // Importado de Lombok
import lombok.AllArgsConstructor; // Importado de Lombok
import java.util.Date;

@Entity
@Data // Genera automáticamente getters, setters, toString(), equals() y hashCode()
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String lugar;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String descripcion;
    private int capacidad;
    private String tipoEvento; // Conferencia, Taller, Seminario, etc.

    // ¡Gracias a Lombok, no necesitas escribir los getters, setters y constructores aquí!
    // El @Data, @NoArgsConstructor y @AllArgsConstructor se encargan de eso.
}