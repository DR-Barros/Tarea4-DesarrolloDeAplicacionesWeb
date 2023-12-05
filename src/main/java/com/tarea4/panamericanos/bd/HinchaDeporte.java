package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa la tabla hinchaDeporte de la base de datos
 */
@Entity
@Table(name = "hincha_deporte")
@IdClass(HinchaDeporteId.class)
public class HinchaDeporte {
    /**
     * Identificador del hincha
     */
    @Id
    @Column(name = "hincha_id")
    private int hinchaId;
    /**
     * Identificador del deporte
     */
    @Id
    @Column(name = "deporte_id")
    private int deporteId;
    /**
     * @return el identificador del deporte
     */
    public int getDeporteId() {
        return deporteId;
    }
}

