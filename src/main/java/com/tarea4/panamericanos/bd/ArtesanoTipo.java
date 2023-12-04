package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

@Entity
@Table(name = "artesano_tipo")
@IdClass(ArtesanoTipoId.class)
public class ArtesanoTipo {
    @Id
    @Column(name = "artesano_id")
    private int artesanoId;

    @Id
    @Column(name = "tipo_artesania_id")
    private int tipoArtesaniaId;

    public int getTipoArtesaniaId() {
        return tipoArtesaniaId;
    }
}

