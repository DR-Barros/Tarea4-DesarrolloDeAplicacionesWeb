package com.tarea4.panamericanos.controllers;

import com.tarea4.panamericanos.bd.*;
import com.tarea4.panamericanos.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

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
    public String agregarHinchaRoute(Model model){
        List<Deporte> deportes = appService.getDeportes();
        List<Region> regiones = appService.getRegiones();
        List<Comuna> comunas = appService.getComunas();
        model.addAttribute("deportes", deportes);
        model.addAttribute("regiones", regiones);
        model.addAttribute("comunas", comunas);
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
    public String verHinchaRoute(Model model){
        List<Hincha> hinchasAux = appService.getHinchas(0);
        Map<Hincha, List<Deporte>> hinchas =appService.getDeporteByHinchas(hinchasAux);
        Long cant = appService.countHinchas();
        model.addAttribute("hinchas", hinchas);
        model.addAttribute("next", cant > 5);
        model.addAttribute("page", 0);
        return "ver-hinchas";
    }
    @GetMapping("/ver-hinchas{num}")
    public String verHinchaRoute(@PathVariable("num") String num, Model model){
        try {
            Integer n = Integer.valueOf(num);
            List<Hincha> hinchasAux = appService.getHinchas(n);
            Map<Hincha, List<Deporte>> hinchas =appService.getDeporteByHinchas(hinchasAux);
            Long cant = appService.countHinchas();
            model.addAttribute("hinchas", hinchas);
            model.addAttribute("next", cant > 5+ (long) n*5);
            model.addAttribute("page", n);
            return "ver-hinchas";
        } catch (NumberFormatException e) {
            return "redirect:/";
        }

    }
    @GetMapping("/ver-artesanos")
    public String verArtesanoRoute(){
        //List<Artesano> artesanos = appService.getArtesanos();
        // model.addAttribute("artesanos", artesanos);
        return "ver-artesanos";
    }
    @GetMapping("/data")
    public String dataRoute(){
        return "data";
    }
}
