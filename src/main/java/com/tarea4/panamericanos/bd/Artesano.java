package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "artesano")
public class Artesano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long comuna_id;
    private String descripcion_artesania;
    private String nombre;
    private String email;
    private String celular;

    public Integer getId() {
        return id;
    }

    public Long getComuna_id() {
        return comuna_id;
    }

    public String getCelular() {
        return celular;
    }

    public String getDescripcion_artesania() {
        return descripcion_artesania;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }
}
