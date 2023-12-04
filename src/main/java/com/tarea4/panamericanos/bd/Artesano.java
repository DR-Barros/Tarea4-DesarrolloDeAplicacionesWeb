package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "artesano")
public class Artesano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long comuna_id;
    private String descripcion_artesania;
    private String nombre;
    private String email;
    private String celular;

    public Artesano(Long comuna_id,
                    String descripcion_artesania,
                    String nombre,
                    String email,
                    String celular){
        this.comuna_id = comuna_id;
        this.descripcion_artesania = descripcion_artesania;
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
    }
}
