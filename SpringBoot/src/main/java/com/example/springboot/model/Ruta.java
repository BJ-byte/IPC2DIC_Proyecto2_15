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

    public boolean verificarCentros(LinkedList<Centro> centros) {
        boolean validarOrigen = false;
        boolean validarDestino = false;
        for (Centro c : centros) {
            if (c.getId().equals(this.origen)) {
                validarOrigen = true;
            }else if (c.getId().equals(this.destino)) {
                validarDestino = true;
            }
        }
        if (validarOrigen && validarDestino) {
            System.out.println("Desde ruta: los centros de la ruta "+this.getId()+" si existen");
            return true;
        }else if (!validarOrigen) {
            System.out.println("Desde ruta: "+this.getId()+" el centro origen "+this.getOrigen()+ "no existe");
        }else {
            System.out.println("Desde ruta: "+this.getId()+" el centro destino "+this.getDestino()+ "no existe");
        }
        System.out.println("Desde ruta: la funcion fallo. Centro origen "+this.origen+" centro destino "+this.getDestino());
        return false;
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
