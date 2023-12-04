package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "foto")
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ruta_archivo")
    private String rutaArchivo;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @ManyToOne
    @JoinColumn(name = "artesano_id", referencedColumnName = "id")
    private Artesano artesano;

}

