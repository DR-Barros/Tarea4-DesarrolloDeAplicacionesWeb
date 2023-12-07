package com.tarea4.panamericanos.bd;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "comentario")
public class Comentario {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private LocalDateTime fecha;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_hincha", referencedColumnName = "id")
    private Hincha hincha;
    @ManyToOne
    @JoinColumn(name = "id_artesano", referencedColumnName = "id")
    private Artesano artesano;

    // Constructor
    public Comentario() {
    }
    /**
     * Constructor de la clase
     * @param nombre nombre del hincha o artesano
     * @param email email del hincha o artesano
     * @param fecha fecha del comentario
     * @param comentario comentario
     * @param hincha hincha
     * @param artesano artesano
     */
    public Comentario(String nombre, String email, LocalDateTime fecha, String comentario, Hincha hincha, Artesano artesano) {
        this.nombre = nombre;
        this.email = email;
        this.fecha = fecha;
        this.comentario = comentario;
        this.hincha = hincha;
        this.artesano = artesano;
    }
    /**
     * Método que retorna el id del comentario
     * @return id del comentario
     */
    public Long getId() {
        return id;
    }
    /**
     * Método que retorna el nombre del hincha o artesano
     * @return nombre del hincha o artesano
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Método que retorna el email del hincha o artesano
     * @return email del hincha o artesano
     */
    public String getEmail() {
        return email;
    }

    public String getComentario() {
        return comentario;
    }
    public Hincha getHincha() {
        return hincha;
    }
    public Artesano getArtesano() {
        return artesano;
    }

}
