package com.example.springboot.model;

import java.util.LinkedList;

public class Paquete {

    private String id;
    private String cliente;
    private int peso;
    private String destino;
    private String estado;
    private String centroActual;

    public Paquete(String id, String cliente, int peso, String destino, String estado, String centroActual) {
        this.id = id;
        this.cliente = cliente;
        this.peso = peso;
        this.destino = destino;
        this.estado = estado;
        this.centroActual = centroActual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCentroActual() {
        return centroActual;
    }

    public void setCentroActual(String centroActual) {
        this.centroActual = centroActual;
    }

    public boolean verificarCentros(LinkedList<Centro> centros) {
        boolean validarOrigen = false;
        boolean validarDestino = false;
        for (Centro c : centros) {
            if (c.getId().equals(this.centroActual)) {
                validarOrigen = true;
            }else if (c.getId().equals(this.destino)) {
                validarDestino = true;
            }
        }
        if (validarOrigen && validarDestino) {
            System.out.println("Desde paquete: los centros del paquete "+this.getId()+" si existen");
            return true;
        }else if (!validarOrigen) {
            System.out.println("Desde paquete: "+this.getId()+" el centro origen "+this.getCentroActual()+ "no existe");
        }else {
            System.out.println("Desde paquete: "+this.getId()+" el centro destino "+this.getDestino()+ "no existe");
        }
        System.out.println("Desde paquete: la funcion fallo. Centro origen "+this.getCentroActual()+" centro destino "+this.getDestino());
        return false;
    }

    public boolean verificarDuplicados(LinkedList<Centro> centros) {
        for (Centro c : centros) {
            LinkedList<Paquete> paquetesAlmacenados = c.getPaquetesAlmacenados();
            for (Paquete p : paquetesAlmacenados) {
                if (p.getId().equals(this.id)) {
                    return true;
                }
            }
        }
        return false;
    }
}
