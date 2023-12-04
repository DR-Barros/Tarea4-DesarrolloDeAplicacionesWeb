package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "artesano_tipo")
public class ArtesanoTipo {
    @Id
    @ManyToOne
    @JoinColumn(name = "artesano_id")
    private Artesano artesano;

    @Id
    @ManyToOne
    @JoinColumn(name = "tipo_artesania_id")
    private TipoArtesania tipoArtesania;

    // Otros campos, métodos, getters y setters si es necesario
}

