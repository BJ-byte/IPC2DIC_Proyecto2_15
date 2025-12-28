package com.example.springboot.model;

import java.util.LinkedList;

public class Centro {

    private String id;
    private String nombre;
    private String ciudad;
    private int capacidad;

    private LinkedList<Paquete> paquetesAlamacenados;
    private LinkedList<Mensajero> mensajerosActuales;

    public Centro(String id, String nombre, String ciudad, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.paquetesAlamacenados = new LinkedList<>();
        this.mensajerosActuales = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public LinkedList<Paquete> getPaquetesAlmacenados() {
        return paquetesAlamacenados;
    }

    public LinkedList<Mensajero> getMensajerosActuales() {
        return mensajerosActuales;
    }

    public int getCargaActual() {
        return paquetesAlamacenados.size();
    }

    public double getPorcentajeUso() {
        if (capacidad == 0) return 0;
        return (getCargaActual() * 100.0) / capacidad;
    }


    public Paquete buscarPaquete(String id) {
        for (Paquete p : paquetesAlamacenados) {
            if (p.getId().equals(id)) {
                System.out.println("Desde Centro: el paquete se encuentra en el centro " + this.id);
                return p;
            }
        }
        System.out.println("Desde centro: el paquete no se encuentra en el centro " + this.id);
        return null;
    }

    public boolean agregarPaquete(Paquete p) {
        if (buscarPaquete(p.getId())== null) {
            this.paquetesAlamacenados.add(p);
            //System.out.println("Desde centro: el paquete se agrego al centro " + this.id);
            return true;
        }
        //System.out.println("Desde centro: el paquete ya existe en el centro " + this.id);
        return false;
    }

    public Paquete eliminarPaquete(String id) {
        Paquete p = buscarPaquete(id);
        if (p != null) {
            paquetesAlamacenados.remove(p);
            System.out.println("Desde centro: el paquete se ha eliminado del centro " + this.id);
            }
        return p;
    }

    public Mensajero buscarMensajero(String id) {
        for (Mensajero m : mensajerosActuales) {
            if (m.getId().equals(id)) {
                System.out.println("Desde Centro: el mensajero se encuentra en el centro " + this.id);
                return m;
            }
        }
        System.out.println("Desde centro: el mensajero no se encuentra en el centro " + this.id);
        return null;
    }

    public boolean agregarMensajero(Mensajero m) {
        if (buscarMensajero(m.getId()) != null) {
            return false;
        }
        if (!m.getEstadoOperativo().equals("DISPONIBLE")) {
            return false;
        }
        mensajerosActuales.add(m);
        return true;
    }

    public boolean eliminarMensajero(Mensajero m) {
        if (!m.getEstadoOperativo().equals("DISPONIBLE")) {
            return false;
        }
        return mensajerosActuales.remove(m);
    }
}

