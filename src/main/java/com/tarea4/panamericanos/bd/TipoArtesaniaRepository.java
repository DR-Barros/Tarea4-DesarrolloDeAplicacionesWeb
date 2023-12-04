package com.tarea4.panamericanos.bd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoArtesaniaRepository extends JpaRepository<TipoArtesania, Long> {
    TipoArtesania findAllById(Integer id);
}