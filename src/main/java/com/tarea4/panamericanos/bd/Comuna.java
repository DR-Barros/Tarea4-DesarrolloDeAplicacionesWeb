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
    private Region region;

    public Comuna(){}

    public Comuna(Long id, String nombre, Region region){
        this.id = id;
        this.nombre = nombre;
        this.region = region;
    }

    // Getters y setters
    public Long getId(){return id;}
    public String getNombre(){return nombre;}
    public Integer getRegion(){return region.getId();}

}

