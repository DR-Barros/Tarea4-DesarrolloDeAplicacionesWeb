package com.tarea4.panamericanos.bd;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Integer countByHincha(Hincha hincha);
    Integer countByArtesano(Artesano artesano);
    List<Comentario> findAllByHincha(Hincha hincha, org.springframework.data.domain.Sort sort);
    List<Comentario> findAllByArtesano(Artesano artesano, org.springframework.data.domain.Sort sort);
}
