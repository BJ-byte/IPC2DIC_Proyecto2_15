package com.example.springboot.model;

import java.util.LinkedList;

public class Mensajero {

    private String id;
    private String nombre;
    private int capacidad;
    private String centro;
    private String estadoOperativo;

    public Mensajero(String id, String nombre, int capacidad, String centro, String estadoOperativo) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.centro = centro;

        this.estadoOperativo = estadoOperativo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(String estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public boolean comprobarCentro(LinkedList<Centro> centros) {
        for (Centro c : centros) {
            if (c.getId().equals(this.getCentro())) {
                System.out.println("Desde mensajero "+this.getId()+" el centro asignado si existe");
                return true;
            }
        }
        System.out.println("Desde mensajero "+this.getId()+" el centro asignado no existe");
        return false;
    }

    public boolean verificarDuplicados(LinkedList<Centro> centros) {
        for (Centro c : centros) {
            LinkedList<Mensajero> mensajerosActuales = c.getMensajerosActuales();
            for (Mensajero m : mensajerosActuales) {
                if (m.getId().equals(this.id)) {
                    return true;
                }
            }
        }
        return false;
    }
}
