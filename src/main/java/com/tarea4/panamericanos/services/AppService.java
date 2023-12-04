package com.tarea4.panamericanos.services;

import com.tarea4.panamericanos.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppService {
    private final TipoArtesaniaRepository tipoArtesaniaRepository;
    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;
    @Autowired
    public AppService(TipoArtesaniaRepository tipoArtesaniaRepository,
                      RegionRepository regionRepository,
                      ComunaRepository comunaRepository) {
        this.tipoArtesaniaRepository = tipoArtesaniaRepository;
        this.regionRepository = regionRepository;
        this.comunaRepository = comunaRepository;
    }
    public List<TipoArtesania> getTiposArtesania() {
        return tipoArtesaniaRepository.findAll();
    }

    public List<Region> getRegiones() {
        return regionRepository.findAll();
    }

    public List<Comuna> getComunas() {
        return comunaRepository.findAll();
    }

}
