package com.tarea4.panamericanos.bd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    Comuna findFirstByNombre(String nombre);
}
