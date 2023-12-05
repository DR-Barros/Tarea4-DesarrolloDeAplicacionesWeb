package com.tarea4.panamericanos.services;

import com.tarea4.panamericanos.bd.*;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiService {
    private List<String> errores;
    private final HinchaRepository hinchaRepository;
    private final HinchaDeporteRepository hinchaDeporteRepository;
    private final DeporteRepository deporteRepository;
    private final ArtesanoTipoRepository artesanoTipoRepository;
    private final TipoArtesaniaRepository tipoArtesaniaRepository;
    private final ComunaRepository comunaRepository;
    private final RegionRepository regionRepository;
    @Autowired
    public ApiService(HinchaRepository hinchaRepository,
                      HinchaDeporteRepository hinchaDeporteRepository,
                      DeporteRepository deporteRepository,
                      ArtesanoTipoRepository artesanoTipoRepository,
                      TipoArtesaniaRepository tipoArtesaniaRepository,
                      ComunaRepository comunaRepository,
                      RegionRepository regionRepository){
        this.hinchaRepository = hinchaRepository;
        this.hinchaDeporteRepository = hinchaDeporteRepository;
        this.deporteRepository = deporteRepository;
        this.artesanoTipoRepository = artesanoTipoRepository;
        this.tipoArtesaniaRepository = tipoArtesaniaRepository;
        this.comunaRepository = comunaRepository;
        this.regionRepository = regionRepository;
        this.errores = new ArrayList<>();
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
    public Pair<Boolean, List<String>> validar(String region, String comuna, List<String> deportes,
                                               String transporte, String nombre, String email,
                                               String telefono, String comentario) {
        this.errores.clear();
        boolean validacion = validarRegion(region) && validarComuna(region, comuna) &&
                validarDeportes(deportes) && validarTransporte(transporte) &&
                validarNombre(nombre) && validarMail(email) &&
                validarCelular(telefono) && validarComentario(comentario);

        return new Pair<>(validacion, this.errores);
    }

    private boolean validarRegion(String region) {
        int regionInt = Integer.parseInt(region);
        if (regionInt < 1 || regionInt > 16) {
            this.errores.add("region");
            return false;
        }
        return true;
    }

    private boolean validarComuna(String region, String comuna) {
        List<Comuna> comunas = comunaRepository.findAll();
        long regionInt = (long) Integer.parseInt(region);
        int comunaInt = Integer.parseInt(comuna);
        for (Comuna com : comunas) {
            if (com.getId() == comunaInt) {
                if (regionInt != com.getRegion()) {
                    this.errores.add("region");
                    return false;
                } else {
                    return true;
                }
            }
        }
        this.errores.add("region");
        return false;
    }

    private boolean validarDeportes(List<String> deportes) {
        for (String t : deportes) {
            int deporteInt = Integer.parseInt(t);
            if (deporteInt < 1 || deporteInt > 60) {
                this.errores.add("deporte");
                return false;
            }
        }
        return true;
    }

    private boolean validarTransporte(String transporte) {
        if (!transporte.equals("particular") && !transporte.equals("locomoción pública")) {
            this.errores.add("transporte");
            return false;
        }
        return true;
    }

    private boolean validarNombre(String nombre) {
        if (nombre.length() < 3 || nombre.length() > 80) {
            this.errores.add("nombre");
            return false;
        }
        return true;
    }

    private boolean validarMail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            this.errores.add("mail");
            return false;
        }
        return true;
    }

    private boolean validarCelular(String telefono) {
        Pattern pattern = Pattern.compile("^(\\+569|9)\\d{8}$");
        Matcher matcher = pattern.matcher(telefono);
        if (!matcher.matches()) {
            this.errores.add("celular");
            return false;
        }
        return true;
    }

    private boolean validarComentario(String comentario) {
        if (comentario.length() > 80) {
            this.errores.add("comentario");
            return false;
        }
        return true;
    }
    public Integer getRegionId(String region){
        return regionRepository.findFirstByNombre(region).getId();
    }
    public Long getComunaId(String comuna){
        return  comunaRepository.findFirstByNombre(comuna).getId();
    }
    public boolean saveHincha(Hincha hincha){
        try {
            hinchaRepository.save(hincha);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
