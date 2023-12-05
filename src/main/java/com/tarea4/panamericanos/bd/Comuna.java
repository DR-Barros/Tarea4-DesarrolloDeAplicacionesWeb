package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa una comuna
 */
@Entity
@Table(name = "comuna")
public class Comuna {
    /**
     * Identificador de la comuna
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre de la comuna
     */
    private String nombre;

    /**
     * Region a la que pertenece la comuna
     */
    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    /**
     * Constructor vacio
     */
    public Comuna(){}

    /**
     * Constructor para crear una comuna
     * @param id identificador de la comuna
     * @param nombre nombre de la comuna
     * @param region region a la que pertenece la comuna
     */
    public Comuna(Long id, String nombre, Region region){
        this.id = id;
        this.nombre = nombre;
        this.region = region;
    }

    // Getters y setters
    /**
     * @return id de la comuna
     */
    public Long getId(){return id;}
    /**
     * @return nombre de la comuna
     */
    public String getNombre(){return nombre;}
    /**
     * @return id de la region a la que pertenece la comuna
     */
    public Integer getRegion(){return region.getId();}

}

