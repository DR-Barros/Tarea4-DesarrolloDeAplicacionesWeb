package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "deporte")
public class Deporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    public Integer getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
}

