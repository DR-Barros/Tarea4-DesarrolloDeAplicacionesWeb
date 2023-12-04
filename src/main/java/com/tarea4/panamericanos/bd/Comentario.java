package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentario")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private LocalDateTime fecha;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_hincha", referencedColumnName = "id")
    private Hincha hincha;
    @ManyToOne
    @JoinColumn(name = "id_artesano", referencedColumnName = "id")
    private Artesano artesano;

}
