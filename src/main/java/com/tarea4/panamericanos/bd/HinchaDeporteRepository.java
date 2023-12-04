package com.tarea4.panamericanos.bd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HinchaDeporteRepository extends JpaRepository<HinchaDeporte, HinchaDeporteId> {
    Integer countByDeporteId(Integer id);
    List<HinchaDeporte> findAllByHinchaId(int id);
}
