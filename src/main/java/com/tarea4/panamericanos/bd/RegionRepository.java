package com.tarea4.panamericanos.bd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Page<Region> findAll(Pageable pageable);
    Region findFirstById(Integer id);
}
