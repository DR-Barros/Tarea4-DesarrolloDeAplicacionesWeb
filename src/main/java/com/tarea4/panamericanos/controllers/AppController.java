package com.tarea4.panamericanos.controllers;

import com.tarea4.panamericanos.bd.Comuna;
import com.tarea4.panamericanos.bd.Region;
import com.tarea4.panamericanos.bd.TipoArtesania;
import com.tarea4.panamericanos.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AppController {
    private final AppService appService;
    @Autowired
    public AppController(AppService appService){
        this.appService = appService;
    }
    @GetMapping("/")
    public String indexRoute(){
        return "index";
    }
    @GetMapping("/agregar-hincha")
    public String agregarHinchaRoute(){
        return "agregar-hincha";
    }
    @GetMapping("/agregar-artesano")
    public String agregarArtesanoRoute(Model model){

        List<TipoArtesania> tiposArtesania = appService.getTiposArtesania();
        List<Region> regiones = appService.getRegiones();
        List<Comuna> comunas = appService.getComunas();

        model.addAttribute("tipos", tiposArtesania);
        model.addAttribute("regiones", regiones);
        model.addAttribute("comunas", comunas);

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
