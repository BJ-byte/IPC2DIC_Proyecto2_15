package com.example.springboot.model;

public class Solicitud {

    private String id;
    private String tipo;
    private String paquete;
    private int prioridad;

    public Solicitud(String id, String tipo, String paquete, int prioridad) {
        this.id = id;
        this.tipo = tipo;
        this.paquete = paquete;
        this.prioridad = prioridad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
