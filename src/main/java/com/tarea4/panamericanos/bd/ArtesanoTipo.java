package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa la tabla artesano_tipo de la base de datos
 */
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

    public ArtesanoTipo(){}

    public ArtesanoTipo(int artesanoId, int tipoArtesaniaId){
        this.artesanoId = artesanoId;
        this.tipoArtesaniaId = tipoArtesaniaId;
    }

    /**
     * @return id del artesano
     */
    public int getArtesanoId() {
        return artesanoId;
    }
    /**
     * @return id del tipo de artesania
     */
    public int getTipoArtesaniaId() {
        return tipoArtesaniaId;
    }
}

