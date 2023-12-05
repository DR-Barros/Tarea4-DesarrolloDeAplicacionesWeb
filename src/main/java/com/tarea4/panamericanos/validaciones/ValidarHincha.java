package com.tarea4.panamericanos.validaciones;


import com.tarea4.panamericanos.bd.Comuna;
import com.tarea4.panamericanos.bd.ComunaRepository;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarHincha {
    private List<String> errores;
    private ComunaRepository comunaRepository;

    public ValidarHincha() {
        this.errores = new ArrayList<>();
    }

    public Pair<Boolean, List<String>> validar(String region, String comuna, List<String> deportes,
                                               String transporte, String nombre, String email,
                                               String telefono, String comentario,
                                               ComunaRepository comunaRepository) {
        this.errores.clear();
        this.comunaRepository = comunaRepository;
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
        //List<Integer> possibleComuna = db.getComuna(conn);
        int regionInt = Integer.parseInt(region);
        int comunaInt = Integer.parseInt(comuna);
        for (int com : possibleComuna) {
            if (com == comunaInt) {
                if (regionInt != com[2]) {
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
}
