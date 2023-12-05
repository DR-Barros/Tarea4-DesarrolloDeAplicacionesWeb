package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa la tabla foto de la base de datos
 */
@Entity
@Table(name = "foto")
public class Foto {
    /**
     * Identificador de la foto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Ruta del archivo
     */
    @Column(name = "ruta_archivo")
    private String rutaArchivo;

    /**
     * Nombre del archivo
     */
    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    /**
     * Artesano al que pertenece la foto
     */
    @ManyToOne
    @JoinColumn(name = "artesano_id", referencedColumnName = "id")
    private Artesano artesano;

    /**
     * @return ruta del archivo
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * @return nombre del archivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }
}

