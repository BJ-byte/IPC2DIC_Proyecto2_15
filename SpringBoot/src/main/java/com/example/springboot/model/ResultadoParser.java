package com.example.springboot.model;

import java.util.LinkedList;

public class ResultadoParser {

    private LinkedList<Centro> centros;
    private LinkedList<Ruta> rutas;
    private LinkedList<Solicitud> solicitudes;

    public ResultadoParser(LinkedList<Centro> centros, LinkedList<Ruta> rutas, LinkedList<Solicitud> solicitudes) {
        this.centros = centros;
        this.rutas = rutas;
        this.solicitudes = solicitudes;
    }

    public LinkedList<Centro> getCentros() {
        return centros;
    }

    public void setCentros(LinkedList<Centro> centros) {
        this.centros = centros;
    }

    public LinkedList<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(LinkedList<Ruta> rutas) {
        this.rutas = rutas;
    }

    public LinkedList<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(LinkedList<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }
}
