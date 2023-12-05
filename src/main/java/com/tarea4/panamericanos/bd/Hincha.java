package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

/**
 * Clase que representa la tabla hincha de la base de datos
 */
@Entity
@Table(name = "hincha")
public class Hincha {
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
     * Modo de transporte del hincha
     */
    private String modoTransporte;
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
     * Comentarios del hincha
     */
    private String comentarios;
    /**
     * Constructor vacio
     */
    public Hincha(){
        super();
    }
    /**
     * Constructor para crear un hincha
     * @param comuna comuna a la que pertenece el hincha
     * @param modoTransporte modo de transporte del hincha
     * @param nombre nombre del hincha
     * @param email email del hincha
     * @param celular celular del hincha
     * @param comentarios comentarios del hincha
     */
    public Hincha(Comuna comuna, String modoTransporte, String nombre, String email, String celular, String comentarios){
        this.comuna = comuna;
        this.modoTransporte = modoTransporte;
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
        this.comentarios = comentarios;
    }
    /**
     * @return id del hincha
     */
    public Integer getId(){
        return id;
    }
    /**
     * @return nombre del hincha
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @return id de la comuna a la que pertenece el hincha
     */
    public Comuna getComuna() {
        return comuna;
    }
    /**
     * @return modo de transporte del hincha
     */
    public String getModoTransporte() {
        return modoTransporte;
    }
    /**
     * @return celular del hincha
     */
    public String getCelular() {
        return celular;
    }
}
