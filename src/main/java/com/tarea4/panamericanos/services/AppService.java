package com.tarea4.panamericanos.services;

import com.tarea4.panamericanos.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppService {
    private final TipoArtesaniaRepository tipoArtesaniaRepository;
    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;
    private final DeporteRepository deporteRepository;
    private final ArtesanoRepository artesanoRepository;
    private final ArtesanoTipoRepository artesanoTipoRepository;
    private final HinchaRepository hinchaRepository;
    private final HinchaDeporteRepository hinchaDeporteRepository;
    @Autowired
    public AppService(TipoArtesaniaRepository tipoArtesaniaRepository,
                      RegionRepository regionRepository,
                      ComunaRepository comunaRepository,
                      DeporteRepository deporteRepository,
                      ArtesanoRepository artesanoRepository,
                      ArtesanoTipoRepository artesanoTipoRepository,
                      HinchaRepository hinchaRepository,
                      HinchaDeporteRepository hinchaDeporteRepository) {
        this.tipoArtesaniaRepository = tipoArtesaniaRepository;
        this.regionRepository = regionRepository;
        this.comunaRepository = comunaRepository;
        this.deporteRepository = deporteRepository;
        this.artesanoRepository = artesanoRepository;
        this.artesanoTipoRepository = artesanoTipoRepository;
        this.hinchaRepository = hinchaRepository;
        this.hinchaDeporteRepository = hinchaDeporteRepository;
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

    public List<Deporte> getDeportes(){return  deporteRepository.findAll();}

    public List<Artesano> getArtesanos(){
        return artesanoRepository.findAll();
    }
    public List<ArtesanoTipo> getTipoArtesaniaOfArtesanos(List<Artesano> artesanos){
        List<ArtesanoTipo> tipos = new ArrayList<>();
        for (Artesano artesano: artesanos){
            List<ArtesanoTipo> tiposAux = artesanoTipoRepository.findAllByArtesanoId(artesano.getId());
            for (ArtesanoTipo tipo: tiposAux){
                tipos.add(tipo);
            }
        }
        return tipos;
    }
    public List<Hincha> getHinchas(int offset){
        List<Hincha> hinchas = hinchaRepository.findAll();
        return hinchas.subList(offset, offset+5);
    }
    private List<Deporte> getDeporteByHincha(int hincha_id){
        List<HinchaDeporte> hinchas = hinchaDeporteRepository.findAllByHinchaId(hincha_id);
        List<Deporte> deportes = new ArrayList<>();
        for (HinchaDeporte hincha: hinchas){
            deportes.add(deporteRepository.findById(hincha.getDeporteId()).get());
        }
        return  deportes;
    }

    public Map<Hincha, List<Deporte>> getDeporteByHinchas(List<Hincha> hinchas){
        Map<Hincha, List<Deporte>> ret = new HashMap<>();
        for(Hincha hincha: hinchas){
            ret.put(hincha, getDeporteByHincha(hincha.getId()));
        }
        return ret;
    }
    public Long countHinchas(){return hinchaRepository.count();}
}
