package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "hincha")
public class Hincha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "comuna_id", referencedColumnName = "id")
    private Comuna comuna;
    
    private String modoTransporte;

    private String nombre;
    private String email;
    private String celular;
    private String comentarios;

    public Integer getId(){
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public String getModoTransporte() {
        return modoTransporte;
    }

    public String getCelular() {
        return celular;
    }
}
