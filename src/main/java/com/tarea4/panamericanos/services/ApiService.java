package com.tarea4.panamericanos.services;

import com.tarea4.panamericanos.bd.*;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que representa el servicio de la API
 */
@Service
public class ApiService {
    /**
     * Lista de errores en la verificación de servicios POST
     */
    private List<String> errores;
    /**
     * Ruta de la carpeta static
     */
    private final String pathStatic = "./src/main/resources/static";
    private final HinchaRepository hinchaRepository;
    private final HinchaDeporteRepository hinchaDeporteRepository;
    private final DeporteRepository deporteRepository;
    private final ArtesanoRepository artesanoRepository;
    private final ArtesanoTipoRepository artesanoTipoRepository;
    private final TipoArtesaniaRepository tipoArtesaniaRepository;
    private final ComunaRepository comunaRepository;
    private final RegionRepository regionRepository;
    private final ComentarioRepository comentarioRepository;
    /**
     * Constructor de la clase
     * @param hinchaRepository repositorio de hinchas
     * @param hinchaDeporteRepository repositorio de hinchas deportes
     * @param deporteRepository repositorio de deportes
     * @param artesanoRepository repositorio de artesanos
     * @param artesanoTipoRepository repositorio de artesanos tipos
     * @param tipoArtesaniaRepository repositorio de tipos de artesanias
     * @param comunaRepository repositorio de comunas
     * @param regionRepository repositorio de regiones
     */
    @Autowired
    public ApiService(HinchaRepository hinchaRepository,
                      HinchaDeporteRepository hinchaDeporteRepository,
                      DeporteRepository deporteRepository,
                      ArtesanoRepository artesanoRepository,
                      ArtesanoTipoRepository artesanoTipoRepository,
                      TipoArtesaniaRepository tipoArtesaniaRepository,
                      ComunaRepository comunaRepository,
                      RegionRepository regionRepository,
                      ComentarioRepository comentarioRepository){
        this.hinchaRepository = hinchaRepository;
        this.hinchaDeporteRepository = hinchaDeporteRepository;
        this.deporteRepository = deporteRepository;
        this.artesanoRepository = artesanoRepository;
        this.artesanoTipoRepository = artesanoTipoRepository;
        this.tipoArtesaniaRepository = tipoArtesaniaRepository;
        this.comunaRepository = comunaRepository;
        this.regionRepository = regionRepository;
        this.comentarioRepository = comentarioRepository;
        this.errores = new ArrayList<>();
    }
    /**
     * Método que retorna una lista de todos los Deportes
     * @return lista de Deportes
     */
    private List<Deporte> getDeportes(){
        return deporteRepository.findAll();
    }

    /**
     * Método que retorna la cantidad de Hinchas de un Deporte
     * @param id identificador del Deporte
     * @return cantidad de Hinchas de un Deporte
     */
    private Integer getCountHinchaDeporte(Integer id){
        return hinchaDeporteRepository.countByDeporteId(id);
    }
    /**
     * Método que retorna una lista de todos los Tipos de Artesanías
     * @return lista de Tipos de Artesanías
     */
    private List<TipoArtesania> getTipoArtesania(){return tipoArtesaniaRepository.findAll();}
    /**
     * Método que retorna la cantidad de Artesanos de un Tipo de Artesanía
     * @param id identificador del Tipo de Artesanía
     * @return cantidad de Artesanos de un Tipo de Artesanía
     */
    private Integer getCountArtesanoTipo(Integer id){return  artesanoTipoRepository.countByTipoArtesaniaId(id);}
    /**
     * Método que retorna un mapa con los nombres de los Deportes y la cantidad de Hinchas de cada uno
     * @return mapa con los nombres de los Deportes y la cantidad de Hinchas de cada uno
     */
    public Map<String, Integer> getCountDeportes(){
        List<Deporte> Deportes = this.getDeportes();
        Map<String, Integer> CountDeportes = new HashMap<>();
        for (Deporte deporte: Deportes){
            CountDeportes.put(deporte.getNombre(), getCountHinchaDeporte(deporte.getId()));
        }
        return  CountDeportes;
    }
    /**
     * Método que retorna un mapa con los nombres de los Tipos de Artesanías y la cantidad de Artesanos de cada uno
     * @return mapa con los nombres de los Tipos de Artesanías y la cantidad de Artesanos de cada uno
     */
    public Map<String, Integer> getCountTipoArtesanias(){
        List<TipoArtesania> tipos = this.getTipoArtesania();
        Map<String, Integer> CountTipos = new HashMap<>();
        for (TipoArtesania tipo: tipos){
            CountTipos.put(tipo.getNombre(), getCountArtesanoTipo(tipo.getId()));
        }
        return  CountTipos;
    }
    //Validaciones
    /**
     * Método que valida los datos de un Hincha antes de su creacion
     * @param region identificador de la región
     * @param comuna identificador de la comuna
     * @param deportes lista de identificadores de los deportes
     * @param transporte transporte del hincha
     * @param nombre nombre del hincha
     * @param email correo electrónico del hincha
     * @param telefono teléfono del hincha
     * @param comentario comentario del hincha
     * @return par con la validación y la lista de errores
     */
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
    /**
     * Método que valida los datos de un Artesano antes de su creacion
     * @param region identificador de la región
     * @param comuna identificador de la comuna
     * @param artesanias lista de identificadores de los tipos de artesanías
     * @param photo foto del artesano
     * @param photo2 segunda foto del artesano
     * @param photo3 tercera foto del artesano
     * @param name nombre del artesano
     * @param mail correo electrónico del artesano
     * @param phone teléfono del artesano
     * @return par con la validación y la lista de errores
     */
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

    /**
     * Método que valida la región, si no es valida agrega el error a la lista de errores
     * @param region identificador de la región
     * @return true si la región es válida, false en caso contrario
     */
    private boolean validarRegion(String region) {
        int regionInt = Integer.parseInt(region);
        if (regionInt < 1 || regionInt > 16) {
            this.errores.add("region");
            return false;
        }
        return true;
    }
    /**
     * Método que valida la comuna, si no es valida agrega el error a la lista de errores
     * @param region identificador de la región
     * @param comuna identificador de la comuna
     * @return true si la comuna es válida, false en caso contrario
     */
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

    /**
     * Método que valida los deportes, si no son validos agrega el error a la lista de errores
     * @param deportes lista de identificadores de los deportes
     * @return true si los deportes son válidos, false en caso contrario
     */
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

    /**
     * Método que valida el transporte, si no es valido agrega el error a la lista de errores
     * @param transporte transporte del hincha
     * @return true si el transporte es válido, false en caso contrario
     */
    private boolean validarTransporte(String transporte) {
        if (!transporte.equals("particular") && !transporte.equals("locomoción pública")) {
            this.errores.add("transporte");
            return false;
        }
        return true;
    }

    /**
     * Método que valida el nombre, si no es valido agrega el error a la lista de errores
     * @param nombre nombre del hincha
     * @return true si el nombre es válido, false en caso contrario
     */
    private boolean validarNombre(String nombre) {
        if (nombre.length() < 3 || nombre.length() > 80) {
            this.errores.add("nombre");
            return false;
        }
        return true;
    }

    /**
     * Método que valida el correo electrónico, si no es valido agrega el error a la lista de errores
     * @param email correo electrónico del hincha
     * @return true si el correo electrónico es válido, false en caso contrario
     */
    private boolean validarMail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            this.errores.add("mail");
            return false;
        }
        return true;
    }
    /**
     * Método que valida el teléfono, si no es valido agrega el error a la lista de errores
     * @param telefono teléfono del hincha
     * @return true si el teléfono es válido, false en caso contrario
     */
    private boolean validarCelular(String telefono) {
        Pattern pattern = Pattern.compile("^(\\+569|9)\\d{8}$");
        Matcher matcher = pattern.matcher(telefono);
        if (!matcher.matches()) {
            this.errores.add("celular");
            return false;
        }
        return true;
    }
    /**
     * Método que valida el comentario, si no es valido agrega el error a la lista de errores
     * @param comentario comentario del hincha
     * @return true si el comentario es válido, false en caso contrario
     */
    private boolean validarComentario(String comentario) {
        if (comentario.length() > 80) {
            this.errores.add("comentario");
            return false;
        }
        return true;
    }
    /**
     * Método que valida los tipos de artesanías, si no son validos agrega el error a la lista de errores
     * @param artesanias lista de identificadores de los tipos de artesanías
     * @return true si los tipos de artesanías son válidos, false en caso contrario
     */
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
    /**
     * Método que valida la foto, si no es valida agrega el error a la lista de errores
     * @param photo foto del artesano
     * @return true si la foto es válida, false en caso contrario
     */
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
    /**
     * Método que retorna la extensión de un archivo
     * @param file archivo
     * @return extensión del archivo
     */
    private String obtenerExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }
    /**
     * Método que retorna el nombre de una región
     * @param id identificador de la región
     * @return nombre de la región
     */
    public String getRegionNombre(Integer id){
        return regionRepository.findFirstById(id).getNombre();
    }
    /**
     * Método que retorna el nombre de una comuna
     * @param id identificador de la comuna
     * @return nombre de la comuna
     */
    public String getComunaNombre(Integer id){
        return  comunaRepository.findFirstById(id).getNombre();
    }
    /**
     * Método que guarda un hincha en la base de datos
     * @param hincha hincha a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean saveHincha(Hincha hincha){
        try {
            hinchaRepository.save(hincha);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    /**
     * Método que guarda un artesano en la base de datos
     * @param artesano artesano a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean saveArtesano(Artesano artesano){
        try {
            artesanoRepository.save(artesano);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    /**
     * Método que guarda una foto en la carpeta static
     * @param photo foto a guardar
     * @return nombre del archivo de la foto
     * @throws IOException
     */
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
    /**
     * Método que retorna una lista de hinchas que contienen un string en su nombre y la cantidad de comentarios que tienen
     * @param str string a buscar
     * @return lista de hinchas que contienen un string en su nombre y la cantidad de comentarios que tienen
     */
    public List<Pair<Hincha, Integer>> buscarHincha(String str){
        List<Hincha> hinchas = hinchaRepository.findAll();
        List<Pair<Hincha, Integer>> hinchasEncontrados = new ArrayList<>();
        for (Hincha hincha: hinchas){
            if (hincha.getNombre().toLowerCase().contains(str.toLowerCase())){
                Integer comentarios = comentarioRepository.countByHincha(hincha);
                hinchasEncontrados.add(new Pair<>(hincha, comentarios));
            }
        }
        return hinchasEncontrados;
    }
    /**
     * Método que retorna una lista de artesanos que contienen un string en su nombre y la cantidad de comentarios que tienen
     * @param str string a buscar
     * @return lista de artesanos que contienen un string en su nombre y la cantidad de comentarios que tienen
     */
    public List<Pair<Artesano, Integer>>buscarArtesano(String str){
        List<Artesano> artesanos = artesanoRepository.findAll();
        List<Pair<Artesano, Integer>> artesanosEncontrados = new ArrayList<>();
        for (Artesano artesano: artesanos){
            if (artesano.getNombre().toLowerCase().contains(str.toLowerCase())){
                Integer comentarios = comentarioRepository.countByArtesano(artesano);
                artesanosEncontrados.add(new Pair<>(artesano, comentarios));
            }
        }
        return artesanosEncontrados;
    }
    public Pair<Boolean, List<String>> validarComentarioHincha(String hincha, String nombre, String email, String comentario){
        this.errores.clear();
        boolean validacion = validarHincha(hincha) && validarNombre(nombre) && validarMail(email) && validarComentario(comentario);
        return new Pair<>(validacion, this.errores);
    }
    public Pair<Boolean, List<String>> validarComentarioArtesano(String artesano, String nombre, String email, String comentario){
        this.errores.clear();
        boolean validacion = validarArtesano(artesano) && validarNombre(nombre) && validarMail(email) && validarComentario(comentario);
        return new Pair<>(validacion, this.errores);
    }
    public Boolean validarHincha(String hincha){
        try {
            Long n = (long) Integer.valueOf(hincha);
            Hincha hincha1 = hinchaRepository.findFirstById(n);
            if (hincha1 == null){
                this.errores.add("hincha");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            this.errores.add("hincha");
            return false;
        }
    }
    public Boolean validarArtesano(String artesano){
        try {
            Long n = (long) Integer.valueOf(artesano);
            Artesano artesano1 = artesanoRepository.findFirstById(n);
            if (artesano1 == null){
                this.errores.add("artesano");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            this.errores.add("artesano");
            return false;
        }
    }
    public Hincha getHincha(Long id){
        return hinchaRepository.findFirstById(id);
    }
    public Artesano getArtesano(Long id){
        return artesanoRepository.findFirstById(id);
    }
    public boolean saveComentarioHincha(Hincha hincha, String nombre, String email, String comentario){
        try {
            Comentario comentario1 = new Comentario(nombre, email, LocalDateTime.now(), comentario, hincha, null);
            comentarioRepository.save(comentario1);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public boolean saveComentarioArtesano(Artesano artesano, String nombre, String email, String comentario){
        try {
            Comentario comentario1 = new Comentario(nombre, email, LocalDateTime.now(), comentario, null, artesano);
            comentarioRepository.save(comentario1);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public List<Comentario> getComentariosHinchaid(Long id){
        Hincha hincha = hinchaRepository.findFirstById(id);
        Sort sort = Sort.by(Sort.Direction.DESC, "fecha");
        return comentarioRepository.findAllByHincha(hincha, sort);
    }
    public List<Comentario> getComentariosArtesanoid(Long id){
        Artesano artesano = artesanoRepository.findFirstById(id);
        Sort sort = Sort.by(Sort.Direction.DESC, "fecha");
        return comentarioRepository.findAllByArtesano(artesano, sort);
    }
}
