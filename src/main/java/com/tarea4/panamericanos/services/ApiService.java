package com.tarea4.panamericanos.services;

import com.tarea4.panamericanos.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {
    private final HinchaDeporteRepository hinchaDeporteRepository;
    private final DeporteRepository deporteRepository;
    private final ArtesanoTipoRepository artesanoTipoRepository;
    private final TipoArtesaniaRepository tipoArtesaniaRepository;
    @Autowired
    public ApiService(HinchaDeporteRepository hinchaDeporteRepository,
                      DeporteRepository deporteRepository,
                      ArtesanoTipoRepository artesanoTipoRepository,
                      TipoArtesaniaRepository tipoArtesaniaRepository){
        this.hinchaDeporteRepository = hinchaDeporteRepository;
        this.deporteRepository = deporteRepository;
        this.artesanoTipoRepository = artesanoTipoRepository;
        this.tipoArtesaniaRepository = tipoArtesaniaRepository;
    }
    private List<Deporte> getDeportes(){
        return deporteRepository.findAll();
    }
    private Integer getCountHinchaDeporte(Integer id){
        return hinchaDeporteRepository.countByDeporteId(id);
    }
    private List<TipoArtesania> getTipoArtesania(){return tipoArtesaniaRepository.findAll();}
    private Integer getCountArtesanoTipo(Integer id){return  artesanoTipoRepository.countByTipoArtesaniaId(id);}
    public Map<String, Integer> getCountDeportes(){
        List<Deporte> Deportes = this.getDeportes();
        Map<String, Integer> CountDeportes = new HashMap<>();
        for (Deporte deporte: Deportes){
            CountDeportes.put(deporte.getNombre(), getCountHinchaDeporte(deporte.getId()));
        }
        return  CountDeportes;
    }
    public Map<String, Integer> getCountTipoArtesanias(){
        List<TipoArtesania> tipos = this.getTipoArtesania();
        Map<String, Integer> CountTipos = new HashMap<>();
        for (TipoArtesania tipo: tipos){
            CountTipos.put(tipo.getNombre(), getCountArtesanoTipo(tipo.getId()));
        }
        return  CountTipos;
    }
}
