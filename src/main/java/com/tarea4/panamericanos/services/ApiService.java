package com.tarea4.panamericanos.services;

import com.tarea4.panamericanos.bd.*;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiService {
    private List<String> errores;
    private final String pathStatic = "./src/main/resources/static";
    private final HinchaRepository hinchaRepository;
    private final HinchaDeporteRepository hinchaDeporteRepository;
    private final DeporteRepository deporteRepository;
    private final ArtesanoRepository artesanoRepository;
    private final ArtesanoTipoRepository artesanoTipoRepository;
    private final TipoArtesaniaRepository tipoArtesaniaRepository;
    private final ComunaRepository comunaRepository;
    private final RegionRepository regionRepository;
    @Autowired
    public ApiService(HinchaRepository hinchaRepository,
                      HinchaDeporteRepository hinchaDeporteRepository,
                      DeporteRepository deporteRepository,
                      ArtesanoRepository artesanoRepository,
                      ArtesanoTipoRepository artesanoTipoRepository,
                      TipoArtesaniaRepository tipoArtesaniaRepository,
                      ComunaRepository comunaRepository,
                      RegionRepository regionRepository){
        this.hinchaRepository = hinchaRepository;
        this.hinchaDeporteRepository = hinchaDeporteRepository;
        this.deporteRepository = deporteRepository;
        this.artesanoRepository = artesanoRepository;
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
    //Validaciones
    public Pair<Boolean, List<String>> validarHincha(String region, String comuna, List<String> deportes,
                                                     String transporte, String nombre, String email,
                                                     String telefono, String comentario) {
        this.errores.clear();
        boolean validacion = validarRegion(region) && validarComuna(region, comuna) &&
                validarDeportes(deportes) && validarTransporte(transporte) &&
                validarNombre(nombre) && validarMail(email) &&
                validarCelular(telefono) && validarComentario(comentario);

        return new Pair<>(validacion, this.errores);
    }
    public Pair<Boolean, List<String>> validarArtesano(String region, String comuna, List<String> artesanias,
                                                       MultipartFile photo, MultipartFile photo2,
                                                       MultipartFile photo3, String name, String mail, String phone){
        this.errores.clear();
        boolean validacion = validarRegion(region) && validarComuna(region,comuna) && validarArtesanias(artesanias) &&
                validarPhoto(photo) && (photo2.isEmpty() || validarPhoto(photo2)) &&
                (photo3.isEmpty() || validarPhoto(photo3)) && validarNombre(name) &&
                validarMail(mail) && validarCelular(phone);
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
    private boolean validarArtesanias(List<String> artesanias){
        Set<Integer> allowedTypes = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        for (String tipo : artesanias) {
            int tipoInt = Integer.parseInt(tipo);
            if (!allowedTypes.contains(tipoInt)) {
                this.errores.add("tipo artesania");
                return false;
            }
        }
        return true;
    }
    private boolean validarPhoto(MultipartFile photo){
        Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList("png", "jpg", "jpeg", "gif"));
        Set<String> ALLOWED_MIMETYPES = new HashSet<>(Arrays.asList("image/jpeg", "image/png", "image/gif"));
        String ftype_guess = obtenerExtension(photo);
        if (!ALLOWED_EXTENSIONS.contains(ftype_guess)) {
            this.errores.add("formato archivo");
            return false;
        }
        String mime_type = photo.getContentType();
        if (!ALLOWED_MIMETYPES.contains(mime_type)) {
            this.errores.add("formato archivo");
            return false;
        }
        return true;
    }
    private String obtenerExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }
    public String getRegionNombre(Integer id){
        return regionRepository.findFirstById(id).getNombre();
    }
    public String getComunaNombre(Integer id){
        return  comunaRepository.findFirstById(id).getNombre();
    }
    public boolean saveHincha(Hincha hincha){
        try {
            hinchaRepository.save(hincha);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public boolean saveArtesano(Artesano artesano){
        try {
            artesanoRepository.save(artesano);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public String savePhoto(MultipartFile photo) throws IOException {
        Path dir = Paths.get(this.pathStatic);
        if(!Files.exists(dir)){
            Files.createDirectories(dir);
        }
        if(photo != null && !photo.isEmpty()){
            byte[] bytes = photo.getBytes();
            Path photoPath = Paths.get(this.pathStatic+ File.separator+photo.getOriginalFilename());
            Files.write(photoPath, bytes);
        }
        return  photo.getOriginalFilename();
    }
}
