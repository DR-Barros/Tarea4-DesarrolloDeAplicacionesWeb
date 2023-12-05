package com.tarea4.panamericanos.controllers;

import com.tarea4.panamericanos.bd.Artesano;
import com.tarea4.panamericanos.bd.Comuna;
import com.tarea4.panamericanos.bd.Hincha;
import com.tarea4.panamericanos.bd.Region;
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
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {
    private final ApiService apiService;
    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/hincha-data")
    public Map<String, Integer> hinchaData() {
        return apiService.getCountDeportes();
    }

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
            Artesano artesano = new Artesano((long)Integer.parseInt(comuna), descripcion, name, mail, phone);
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
                return new ResponseEntity<>("",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("{\"errores\": " + errores.b.toString() + "}", HttpStatus.BAD_REQUEST);
    }
}
