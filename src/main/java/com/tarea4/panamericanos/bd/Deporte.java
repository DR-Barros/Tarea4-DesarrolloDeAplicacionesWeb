package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "deporte")
public class Deporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    // Getters y setters
}

