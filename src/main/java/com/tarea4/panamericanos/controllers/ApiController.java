package com.tarea4.panamericanos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarea4.panamericanos.bd.*;
import com.tarea4.panamericanos.services.ApiService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Clase que representa el controlador de la API
 */
@RestController
public class ApiController {
    /**
     * Servicio de la API
     */
    private final ApiService apiService;
    /**
     * Constructor de la clase
     * @param apiService servicio de la API
     */
    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }
    /**
     * Método que retorna los datos de los hinchas
     * @return datos de los hinchas
     */
    @GetMapping("/hincha-data")
    public Map<String, Integer> hinchaData() {
        return apiService.getCountDeportes();
    }
    /**
     * Método que retorna los datos de los artesanos
     * @return datos de los artesanos
     */
    @GetMapping("/artesano-data")
    public Map<String, Integer> artesanoData() {
        return apiService.getCountTipoArtesanias();
    }

    @PostMapping("/post-hinchas")
    public ResponseEntity<String> postHinchas(@RequestParam String region,
                                      @RequestParam String comuna,
                                      @RequestParam("deportes") List<String> deportes,
                                      @RequestParam String transporte,
                                      @RequestParam String name,
                                      @RequestParam String mail,
                                      @RequestParam String phone,
                                      @RequestParam String coment) {
        Pair<Boolean, List<String>> errores = apiService.validarHincha(region, comuna, deportes, transporte, name, mail, phone, coment);
        if (errores.a){
            Region region1 = new Region(Integer.parseInt(region),
                    apiService.getRegionNombre(Integer.parseInt(region)));
            Comuna comuna1 = new Comuna((long)Integer.parseInt(comuna),
                    apiService.getComunaNombre(Integer.parseInt(comuna)),
                    region1);
            Hincha hincha = new Hincha(comuna1, transporte, name, mail, phone, coment);
            boolean cargo = apiService.saveHincha(hincha);
            if (cargo){
                return new ResponseEntity<>("{\"mensaje\": \"Exito\"}", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("{\"errores\": " + errores.b.toString() + "}", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/post-artesanos")
    public ResponseEntity<String> postArtesanos(@RequestParam String region,
                                                @RequestParam String comuna,
                                                @RequestParam("artesania") List<String> artesanias,
                                                @RequestParam String descripcion,
                                                @RequestParam("photo") MultipartFile photo,
                                                @RequestParam("photo2") MultipartFile photo2,
                                                @RequestParam("photo3") MultipartFile photo3,
                                                @RequestParam String name,
                                                @RequestParam String mail,
                                                @RequestParam String phone){
        Pair<Boolean, List<String>> errores = apiService.validarArtesano(region, comuna, artesanias, photo, photo2, photo3, name, mail, phone);
        if (errores.a){
            Comuna comuna1 = new Comuna((long)Integer.parseInt(comuna),
                    apiService.getComunaNombre(Integer.parseInt(comuna)),
                    new Region(Integer.parseInt(region),
                            apiService.getRegionNombre(Integer.parseInt(region))));
            Artesano artesano = new Artesano(comuna1, descripcion, name, mail, phone);
            boolean cargo = apiService.saveArtesano(artesano);
            try {
                apiService.savePhoto(photo);
                if (!photo2.isEmpty()){
                    apiService.savePhoto(photo2);
                    if (!photo3.isEmpty()){
                        apiService.savePhoto(photo3);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
                cargo = false;
            }
            if (cargo){
                return new ResponseEntity<>("{\"mensaje\": \"Exito\"}", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("{\"errores\": " + errores.b.toString() + "}", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/buscador")
    public ResponseEntity<Object> buscador(@RequestParam String busqueda,
                                           @RequestParam String hincha,
                                           @RequestParam String artesano){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> result = new HashMap<>();
            if (Objects.equals(hincha, "true")){
                List<Pair<Hincha, Integer>> hinchas = apiService.buscarHincha(busqueda);
                result.put("hinchas", hinchas);
            }
            if (Objects.equals(artesano, "true")){
                List<Pair<Artesano, Integer>> artesanos = apiService.buscarArtesano(busqueda);
                result.put("artesanos", artesanos);
            }
            String json = objectMapper.writeValueAsString(result);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ResponseEntity<>("{\"mensaje\": \"Error\"}", HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/post-comentario-hincha")
    public ResponseEntity<String> postComentarioHincha(@RequestParam String id,
                                                       @RequestParam String name,
                                                       @RequestParam String mail,
                                                       @RequestParam String coment){
        Pair<Boolean, List<String>> errores = apiService.validarComentarioHincha(id, name, mail, coment);
        if (errores.a){
            Hincha hincha1 = apiService.getHincha((long)Integer.parseInt(id));
            boolean cargo = apiService.saveComentarioHincha(hincha1, name, mail, coment);
            if (cargo){
                return new ResponseEntity<>("{\"mensaje\": \"Exito\"}", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("{\"errores\": " + errores.b.toString() + "}", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/post-comentario-artesano")
    public ResponseEntity<String> postComentarioArtesano(@RequestParam String id,
                                                         @RequestParam String name,
                                                         @RequestParam String mail,
                                                         @RequestParam String coment){
        Pair<Boolean, List<String>> errores = apiService.validarComentarioArtesano(id, name, mail, coment);
        if (errores.a){
            Artesano artesano1 = apiService.getArtesano((long)Integer.parseInt(id));
            boolean cargo = apiService.saveComentarioArtesano(artesano1, name, mail, coment);
            if (cargo){
                return new ResponseEntity<>("{\"mensaje\": \"Exito\"}", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("{\"errores\": " + errores.b.toString() + "}", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/get-comentarios-hincha")
    public ResponseEntity<String> getComentariosHincha(@RequestParam String id){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Comentario> comentarios = apiService.getComentariosHinchaid((long)Integer.parseInt(id));
            String json = objectMapper.writeValueAsString(comentarios);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ResponseEntity<>("{\"mensaje\": \"Error\"}", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-comentarios-artesano")
    public ResponseEntity<String> getComentariosArtesano(@RequestParam String id){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Comentario> comentarios = apiService.getComentariosArtesanoid((long)Integer.parseInt(id));
            String json = objectMapper.writeValueAsString(comentarios);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ResponseEntity<>("{\"mensaje\": \"Error\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
