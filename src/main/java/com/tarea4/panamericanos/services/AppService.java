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

/**
 * Clase que representa el servicio de la aplicación
 */
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
    /**
     * Constructor de la clase
     * @param tipoArtesaniaRepository repositorio de los tipos de artesanía
     * @param regionRepository repositorio de las regiones
     * @param comunaRepository repositorio de las comunas
     * @param deporteRepository repositorio de los deportes
     * @param artesanoRepository repositorio de los artesanos
     * @param artesanoTipoRepository repositorio de los tipos de artesanía de los artesanos
     * @param hinchaRepository repositorio de los hinchas
     * @param hinchaDeporteRepository repositorio de los deportes de los hinchas
     * @param fotoRepository repositorio de las fotos
     */
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
    /**
     * Método que retorna los tipos de artesanía
     * @return lista con tipos de artesanía
     */
    public List<TipoArtesania> getTiposArtesania() {
        return tipoArtesaniaRepository.findAll();
    }
    /**
     * Método que retorna las regiones
     * @return lista con regiones
     */
    public List<Region> getRegiones() {
        return regionRepository.findAll();
    }
    /**
     * Método que retorna las comunas
     * @return lista con comunas
     */
    public List<Comuna> getComunas() {
        return comunaRepository.findAll();
    }
    /**
     * Método que retorna los deportes
     * @return lista con deportes
     */
    public List<Deporte> getDeportes(){return  deporteRepository.findAll();}

    /**
     * Método que retorna una lista con los artesanos
     * @param offset offset de la lista de artesanos en la base de datos
     * @return lista con 5 artesanos
     */
    public List<Artesano> getArtesanos(int offset){
        List<Artesano> artesanos = artesanoRepository.findAll();
        return artesanos.subList(offset, offset+5);
    }

    /**
     * Método que retorna un Map de los artesanos con sus fotos y tipos de artesanía
     * @param artesanos lista de artesanos
     * @return mapa con los artesanos, sus fotos de artesanias y sus tipos de artesanía
     */
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
    /**
     * Método que retorna la cantidad de artesanos
     * @return cantidad de artesanos
     */
    public Long countArtesanos(){return artesanoRepository.count();}
    /**
     * Método que retorna una lista con los hinchas
     * @param offset offset de la lista de hinchas en la base de datos
     * @return lista con 5 hinchas
     */
    public List<Hincha> getHinchas(int offset){
        List<Hincha> hinchas = hinchaRepository.findAll();
        return hinchas.subList(offset, offset+5);
    }

    /**
     *  Método que retorna una Lista de los deportes del hincha
     * @param hincha_id identificador del hincha
     * @return lista de deportes del hincha
     */
    private List<Deporte> getDeporteByHincha(int hincha_id){
        List<HinchaDeporte> hinchas = hinchaDeporteRepository.findAllByHinchaId(hincha_id);
        List<Deporte> deportes = new ArrayList<>();
        for (HinchaDeporte hincha: hinchas){
            deportes.add(deporteRepository.findById(hincha.getDeporteId()).get());
        }
        return  deportes;
    }

    /**
     * Método que retorna un Map de los hinchas con sus deportes
     * @param hinchas lista de hinchas
     * @return mapa con los hinchas y sus deportes
     */
    public Map<Hincha, List<Deporte>> getDeporteByHinchas(List<Hincha> hinchas){
        Map<Hincha, List<Deporte>> ret = new HashMap<>();
        for(Hincha hincha: hinchas){
            ret.put(hincha, getDeporteByHincha(hincha.getId()));
        }
        return ret;
    }

    /**
     *  Método que retorna la cantidad de hinchas
     * @return cantidad de hinchas
     */
    public Long countHinchas(){return hinchaRepository.count();}
    /**
     * Método que retorna un hincha
     * @param id identificador del hincha
     * @return hincha
     */
    public Hincha getHincha(Long id){return hinchaRepository.findById(id).get();}

    /**
     * Método que retorna una lista de deportes de un hincha
     * @param hincha hincha
     * @return lista de deportes del hincha
     */
    public List<Deporte> getDeportesByHincha(Hincha hincha){
        List<HinchaDeporte> hinchas = hinchaDeporteRepository.findAllByHinchaId(hincha.getId());
        List<Deporte> deportes = new ArrayList<>();
        for (HinchaDeporte hinchaDeporte: hinchas){
            deportes.add(deporteRepository.findById(hinchaDeporte.getDeporteId()).get());
        }
        return deportes;
    }
}
