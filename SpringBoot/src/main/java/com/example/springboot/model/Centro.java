package com.example.springboot.model;

import java.util.LinkedList;

public class Centro {

    private String id;
    private String nombre;
    private String ciudad;
    private int capacidad;

    private LinkedList<Paquete> paquetesAlamacenados;
    private LinkedList<Mensajero> mensajerosActuales;

    public Centro(String id, String nombre, String ciudad, int capacidad, LinkedList<Paquete> paquetesAlamacenados, LinkedList<Mensajero> mensajerosActuales) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.paquetesAlamacenados = paquetesAlamacenados;
        this.mensajerosActuales = mensajerosActuales;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public LinkedList<Paquete> getPaquetesAlmacenados() {
        return paquetesAlamacenados;
    }

    public void setPaquetesAlamacenados(LinkedList<Paquete> paquetesAlamacenados) {
        this.paquetesAlamacenados = paquetesAlamacenados;
    }

    public LinkedList<Mensajero> getMensajerosActuales() {
        return mensajerosActuales;
    }

    public void setMensajerosActuales(LinkedList<Mensajero> mensajerosActuales) {
        this.mensajerosActuales = mensajerosActuales;
    }

    public Paquete buscarPaquete(String id) {
        for (Paquete p : this.paquetesAlamacenados) {
            if (p.getId().equals(id)) {
                System.out.println("Desde Centro: el paquete se encuentra en el centro " + this.id);
                return p;
            }
        }
        System.out.println("Desde centro: el paquete no se encuentra en el centro " + this.id);
        return null;
    }

    public boolean agregarPaquete(Paquete p) {
        Paquete verificar = buscarPaquete(p.getId());
        // si es nulo, no existe el paquete en la lista -> agrega el paquete
        if (verificar == null) {
            this.paquetesAlamacenados.add(p);
            System.out.println("Desde centro: el paquete se agrego al centro " + this.id);
            return true;
        }
        System.out.println("Desde centro: el paquete ya existe en el centro " + this.id);
        return false;
    }

    public Paquete eliminarPaquete(String id) {
        Paquete verificar = buscarPaquete(id);
        // Si no es nulo: el pquete sigue en el centro -> lo elimina del centro
        if (verificar.getId().equals(id)) {
            Paquete temp = null;
            for (Paquete p : this.paquetesAlamacenados) {
                if (p.getId().equals(id)) {
                    temp = p;
                    this.paquetesAlamacenados.remove(p);
                    System.out.println("Desde centro: el paquete se ha eliminado del centro " + this.id);
                    return temp;
                }
            }
        }
        System.out.println("Desde centro: el paquete no se encuentra en el centro " + this.id);
        return null;
    }

    public Mensajero buscarMensajero(String id) {
        for (Mensajero m : this.mensajerosActuales) {
            if (m.getId().equals(id)) {
                System.out.println("Desde Centro: el mensajero se encuentra en el centro " + this.id);
                return m;
            }
        }
        System.out.println("Desde centro: el mensajero no se encuentra en el centro " + this.id);
        return null;
    }

    public boolean agregarMensajero(Mensajero m) {
        Mensajero verificar = buscarMensajero(m.getId());
        // si es nulo, no existe el paquete en la lista -> agrega el paquete
        if (verificar == null) {
            if (m.getEstadoOperativo().equals("DISPONIBLE")) {
                this.mensajerosActuales.add(m);
                System.out.println("Desde centro: el mensajero ahora se encuentra en el centro " + this.id);
                return true;
            }
            System.out.println("Desde centro " + this.id + " : no es posible agregar al mensajero, su estado es " + m.getEstadoOperativo());
            return false;
        }
        System.out.println("Desde centro: el mensajero ya se encuentra en el centro " + this.id + " su estado es " + m.getEstadoOperativo());
        return false;
    }

    public boolean eliminarMensajero(Mensajero m) {
        Mensajero verificar = buscarMensajero(m.getId());
        if (verificar != null) {
            if (verificar.getId().equals(m.getId())) {
                if (m.getEstadoOperativo().equals("DISPONIBLE")) {
                    this.mensajerosActuales.remove(m);
                    return true;
                }
                System.out.println("Desde centro " + this.id + "No es posible eliminar al mensajero del centro, se encuentra en ruta, su estado es " + m.getEstadoOperativo());
                return false;
            }
        }
        System.out.println("El mensajero no se encuentra en el centro " + this.id);
        return false;
    }
}

