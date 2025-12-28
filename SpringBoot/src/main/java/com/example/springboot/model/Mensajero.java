package com.example.springboot.model;

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
}
