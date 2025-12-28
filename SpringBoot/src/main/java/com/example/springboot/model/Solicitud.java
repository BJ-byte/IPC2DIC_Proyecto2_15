package com.example.springboot.model;

import java.util.LinkedList;

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

    public boolean verificarPaquete(LinkedList<Centro> centros) {
        for (Centro c : centros) {
            LinkedList<Paquete> paquetesCentro = c.getPaquetesAlmacenados();
            for (Paquete p : paquetesCentro) {
                if (p.getId().equals(this.paquete)) {
                    return true;
                }
            }
        }
        System.out.println("Desde solicitud "+this.getId()+": el paquete asignado no se encuentra en el sistema");
        return false;
    }

    public boolean verificarDuplicados(LinkedList<Solicitud> solicitudes) {
        for (Solicitud s : solicitudes) {
            if (s.getId().equals(this.id)) {
                System.out.println("Desde solicitud "+this.getId()+": la solicitud ya se encuentra en el sistema");
                return true;
            }
        }
        return false;
    }
}
