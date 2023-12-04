package com.tarea4.panamericanos.bd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
@Repository
public interface ArtesanoRepository extends JpaRepository<Artesano, Long> {
    Page<Artesano> findAllByIdDesc(Pageable pageable);
}
**/