package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_artesania")
public class TipoArtesania {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    // Getters y setters
    public Integer getId(){return id;}
    public  String getNombre(){return nombre;}
}

