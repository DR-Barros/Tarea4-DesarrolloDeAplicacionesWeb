package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa la tabla Region de la base de datos
 */
@Entity
@Table(name = "region")
public class Region {
    /**
     * Identificador de la región
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nombre de la región
     */
    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;
    /**
     * Constructor vacío
     */
    public Region(){}
    /**
     * Constructor con parámetros
     * @param id identificador de la región
     * @param nombre nombre de la región
     */
    public Region(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    /**
     * Método que retorna el identificador de la región
     * @return identificador de la región
     */
    public Integer getId(){return id;}
    /**
     * Método que retorna el nombre de la región
     * @return nombre de la región
     */
    public  String getNombre(){return nombre;}
}
