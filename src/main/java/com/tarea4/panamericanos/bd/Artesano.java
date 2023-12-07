package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa a un hincha
 */
@Entity
@Table(name = "artesano")
public class Artesano {
    /**
     * Identificador del hincha
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Comuna a la que pertenece el hincha
     */
    @ManyToOne
    @JoinColumn(name = "comuna_id", referencedColumnName = "id")
    private Comuna comuna;
    /**
     * Descripcion de las artesanias
     */
    private String descripcion_artesania;
    /**
     * Nombre del hincha
     */
    private String nombre;
    /**
     * Email del hincha
     */
    private String email;
    /**
     * Celular del hincha
     */
    private String celular;
    /**
     * Constructor vacio
     */
    public Artesano(){

    }
    /**
     * Constructor para crear un hincha
     * @param comuna comuna a la que pertenece el hincha
     * @param descripcion_artesania descripcion de las artesanias
     * @param nombre nombre del artesano
     * @param email email del artesano
     * @param celular celular del artesano
     */
    public Artesano(Comuna comuna, String descripcion_artesania, String nombre, String email, String celular){
        this.comuna = comuna;
        this.descripcion_artesania = descripcion_artesania;
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
    }

    /**
     * @return id del hincha
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return comuna del hincha
     */
    public Comuna getComuna() {
        return comuna;
    }

    /**
     * @return celular del hincha
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @return descripcion de las artesanias
     */
    public String getDescripcionArtesania() {
        return descripcion_artesania;
    }

    /**
     * @return email del hincha
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return nombre del hincha
     */
    public String getNombre() {
        return nombre;
    }
}
