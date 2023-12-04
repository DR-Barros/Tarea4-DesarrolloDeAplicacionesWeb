package com.tarea4.panamericanos.services;

import com.tarea4.panamericanos.bd.*;
import org.antlr.v4.runtime.misc.Pair;
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
    private final FotoRepository fotoRepository;
    @Autowired
    public AppService(TipoArtesaniaRepository tipoArtesaniaRepository,
                      RegionRepository regionRepository,
                      ComunaRepository comunaRepository,
                      DeporteRepository deporteRepository,
                      ArtesanoRepository artesanoRepository,
                      ArtesanoTipoRepository artesanoTipoRepository,
                      HinchaRepository hinchaRepository,
                      HinchaDeporteRepository hinchaDeporteRepository,
                      FotoRepository fotoRepository) {
        this.tipoArtesaniaRepository = tipoArtesaniaRepository;
        this.regionRepository = regionRepository;
        this.comunaRepository = comunaRepository;
        this.deporteRepository = deporteRepository;
        this.artesanoRepository = artesanoRepository;
        this.artesanoTipoRepository = artesanoTipoRepository;
        this.hinchaRepository = hinchaRepository;
        this.hinchaDeporteRepository = hinchaDeporteRepository;
        this.fotoRepository = fotoRepository;
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

    public List<Artesano> getArtesanos(int offset){
        List<Artesano> artesanos = artesanoRepository.findAll();
        return artesanos.subList(offset, offset+5);
    }
    public Map<Artesano, Pair<List<Foto>, List<TipoArtesania>>> getFullArtesanos(List<Artesano> artesanos){
        Map<Artesano, Pair<List<Foto>, List<TipoArtesania>>> artesanosFull = new HashMap<>();
        for (Artesano artesano: artesanos){
            List<Foto> fotos = fotoRepository.findAllByArtesanoId(artesano.getId());
            List<ArtesanoTipo> artesanoTipo = artesanoTipoRepository.findAllByArtesanoId(artesano.getId());
            List<TipoArtesania> artesanias = new ArrayList<>();
            for (ArtesanoTipo tipo: artesanoTipo){
                artesanias.add(tipoArtesaniaRepository.findAllById(tipo.getTipoArtesaniaId()));
            }
            artesanosFull.put(artesano, new Pair<>(fotos, artesanias));
        }
        return artesanosFull;
    }
    public Long countArtesanos(){return artesanoRepository.count();}
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
