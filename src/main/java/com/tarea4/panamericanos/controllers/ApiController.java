package com.tarea4.panamericanos.controllers;

import com.tarea4.panamericanos.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String postHinchas(@RequestParam String region,
                              @RequestParam String comuna,
                              @RequestParam("deportes") List<String> deportes,
                              @RequestParam String transporte,
                              @RequestParam String name,
                              @RequestParam String mail,
                              @RequestParam String phone,
                              @RequestParam String coment) {
        return "Exito";
    }
}
