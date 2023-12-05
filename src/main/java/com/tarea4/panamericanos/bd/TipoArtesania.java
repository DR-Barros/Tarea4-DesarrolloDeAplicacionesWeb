package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa la tabla tipo_artesania de la base de datos
 */
@Entity
@Table(name = "tipo_artesania")
public class TipoArtesania {
    /**
     * Identificador del tipo de artesanía
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nombre del tipo de artesanía
     */
    private String nombre;

    // Getters y setters
    /**
     * Método que retorna el identificador del tipo de artesanía
     * @return identificador del tipo de artesanía
     */
    public Integer getId(){return id;}
    /**
     * Método que retorna el nombre del tipo de artesanía
     * @return nombre del tipo de artesanía
     */
    public  String getNombre(){return nombre;}
}

