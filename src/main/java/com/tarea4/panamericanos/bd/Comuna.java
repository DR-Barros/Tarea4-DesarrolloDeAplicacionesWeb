package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "comuna")
public class Comuna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region; // Suponiendo que tienes una entidad Region

    // Getters y setters
}

