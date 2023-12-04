package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "hincha")

public class Hincha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comuna_id", referencedColumnName = "id")
    private Comuna comuna;

    @Enumerated(EnumType.STRING)
    @Column(name = "modo_transporte")
    private ModoTransporte modoTransporte;

    private String nombre;
    private String email;
    private String celular;
    private String comentarios;

}
