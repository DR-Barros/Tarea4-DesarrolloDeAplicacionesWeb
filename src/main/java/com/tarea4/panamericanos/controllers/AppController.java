package com.tarea4.panamericanos.controllers;

import com.tarea4.panamericanos.bd.*;
import com.tarea4.panamericanos.services.AppService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * Clase que representa el controlador de la aplicación
 */
@Controller
public class AppController {
    /** Servicio de la aplicación */
    private final AppService appService;
    /**
     * Constructor de la clase
     * @param appService servicio de la aplicación
     */
    @Autowired
    public AppController(AppService appService){
        this.appService = appService;
    }
    /**
     * Método que retorna la ruta del index
     * @return ruta del index
     */
    @GetMapping("/")
    public String indexRoute(){
        return "index";
    }
    /**
     * Método que retorna la ruta de agregar hincha
     * @return ruta de agregar hincha
     */
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
    /**
     * Método que retorna la ruta de agregar artesano
     * @return ruta de agregar artesano
     */
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
    /**
     * Método que retorna la ruta de agregar deporte
     * @return ruta de agregar deporte
     */
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
    /**
     * Método que retorna la ruta de agregar deporte
     * @return ruta de agregar deporte
     */
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
    /**
     * Método que retorna la ruta de agregar deporte
     * @return ruta de agregar deporte
     */
    @GetMapping("/ver-artesanos")
    public String verArtesanoRoute(Model model){
        List<Artesano> artesanos = appService.getArtesanos(0);
        Map<Artesano, Pair<List<Foto>, List<TipoArtesania>>> artesanosFull = appService.getFullArtesanos(artesanos);
        Long cant = appService.countArtesanos();
        model.addAttribute("artesanos", artesanosFull);
        model.addAttribute("next", cant > 5);
        model.addAttribute("page", 0);
        return "ver-artesanos";
    }
    /**
     * Método que retorna la ruta de agregar deporte
     * @return ruta de agregar deporte
     */
    @GetMapping("/ver-artesanos{num}")
    public String verArtesanoRoute(@PathVariable("num") String num, Model model){
        try {
            Integer n = Integer.valueOf(num);
            List<Artesano> artesanos = appService.getArtesanos(n);
            Map<Artesano, Pair<List<Foto>, List<TipoArtesania>>> artesanosFull = appService.getFullArtesanos(artesanos);
            Long cant = appService.countArtesanos();
            model.addAttribute("artesanos", artesanosFull);
            model.addAttribute("next", cant > 5+ (long) n*5);
            model.addAttribute("page", n);
            return "ver-artesanos";
        } catch (NumberFormatException e) {
            return "redirect:/";
        }

    }
    /**
     * Método que retorna la ruta de informacion de un hincha en especifico
     * @return ruta de informacion de un hincha
     */
    @GetMapping("/informacion-hincha-{id}")
    public String informacionHinchaRoute(@PathVariable("id") String id, Model model){
        try {
            Long n = (long) Integer.valueOf(id);
            Hincha hincha = appService.getHincha(n);
            Region region = appService.getRegionbyHincha(hincha);
            List<Deporte> deportes = appService.getDeportesByHincha(hincha);
            model.addAttribute("hincha", hincha);
            model.addAttribute("region", region);
            model.addAttribute("deportes", deportes);
            return "informacion-hincha";
        } catch (NumberFormatException e) {
            return "redirect:/";
        }
    }
    /**
     * Método que retorna la ruta de informacion de un artesano en especifico
     * @return ruta de informacion de un artesano
     */
    @GetMapping("/informacion-artesano-{id}")
    public String informacionArtesanoRoute(@PathVariable("id") String id, Model model){
        try {
            Long n = (long) Integer.valueOf(id);
            Artesano artesano = appService.getArtesano(n);
            Comuna comuna = appService.getComunabyArtesano(artesano);
            Region region = appService.getRegionbyComuna(comuna);
            List<TipoArtesania> tipos = appService.getTiposArtesaniaByArtesano(artesano);
            List<Foto> fotos = appService.getFotosByArtesano(artesano);
            model.addAttribute("artesano", artesano);
            model.addAttribute("comuna", comuna);
            model.addAttribute("region", region);
            model.addAttribute("tipos", tipos);
            model.addAttribute("fotos", fotos);
            return "informacion-artesano";
        } catch (NumberFormatException e) {
            return "redirect:/";
        }
    }
    /**
     * Método que retorna la ruta de agregar deporte
     * @return ruta de agregar deporte
     */
    @GetMapping("/data")
    public String dataRoute(){
        return "data";
    }
}
