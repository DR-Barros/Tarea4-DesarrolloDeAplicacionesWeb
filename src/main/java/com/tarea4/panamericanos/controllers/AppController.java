package com.tarea4.panamericanos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {
    @GetMapping("/")
    public String indexRoute(){
        return "index";
    }
    @GetMapping("/agregar-hincha")
    public String agregarHinchaRoute(){
        return "agregar-hincha";
    }
    @GetMapping("/agregar-artesano")
    public String agregarArtesanoRoute(){
        return "agregar-artesano";
    }

    @GetMapping("/ver-hinchas")
    public String verHinchaRoute(){
        return "ver-hinchas";
    }
    @GetMapping("/ver-artesanos")
    public String verArtesanoRoute(){
        return "ver-artesanos";
    }
    @GetMapping("/data")
    public String dataRoute(){
        return "data";
    }
}
