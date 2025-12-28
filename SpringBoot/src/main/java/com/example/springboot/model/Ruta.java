package com.example.springboot.model;

import java.util.LinkedList;

public class Ruta {

    private String id;
    private String origen;
    private String destino;
    private int distancia;

    public Ruta(String id, String origen, String destino, int distancia) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public boolean verificarDuplicados(LinkedList<Ruta> rutas) {
        for (Ruta r : rutas) {
            String origen = r.getOrigen();
            String destino = r.getDestino();
            if (origen.equals(this.getOrigen())) {
                if (destino.equals(this.getDestino())) {
                    System.out.println("Desde ruta "+this.getId()+": ya existe una ruta con origen "+origen+ " y destino "+destino);
                    return false;
                }
            }
        }
        System.out.println("Desde ruta "+this.getId()+": no existe una ruta con los mismos centros, origen "+this.origen+", destino "+this.destino);
        return true;
    }
}
