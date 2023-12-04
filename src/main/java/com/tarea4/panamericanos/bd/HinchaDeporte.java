package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "hincha_deporte")
public class HinchaDeporte {
    @Id
    @ManyToOne
    @JoinColumn(name = "hincha_id", referencedColumnName = "id")
    private Hincha hincha;

    @Id
    @ManyToOne
    @JoinColumn(name = "deporte_id", referencedColumnName = "id")
    private Deporte deporte;

    // Getters y setters
}

