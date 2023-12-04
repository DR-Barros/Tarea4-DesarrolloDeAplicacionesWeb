package com.tarea4.panamericanos.bd;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Integer> {
    List<Foto> findAllByArtesanoId(Integer id);
}
