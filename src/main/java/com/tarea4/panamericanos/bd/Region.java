package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    public Region(){}
    public Region(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public Integer getId(){return id;}
    public  String getNombre(){return nombre;}
}
