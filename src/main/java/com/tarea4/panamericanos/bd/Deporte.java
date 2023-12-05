package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa la tabla deporte de la base de datos
 */
@Entity
@Table(name = "deporte")
public class Deporte {
    /**
     * Identificador del deporte
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nombre del deporte
     */
    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    /**
     * @return identificador del deporte
     */
    public Integer getId(){
        return id;
    }
    /**
     * @return nombre del deporte
     */
    public String getNombre(){
        return nombre;
    }
}

