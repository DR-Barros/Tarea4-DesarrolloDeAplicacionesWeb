package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "hincha_deporte")
@IdClass(HinchaDeporteId.class)
public class HinchaDeporte {
    @Id
    @Column(name = "hincha_id")
    private int hinchaId;

    @Id
    @Column(name = "deporte_id")
    private int deporteId;

}

