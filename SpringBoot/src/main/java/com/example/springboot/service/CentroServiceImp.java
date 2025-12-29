package com.example.springboot.service;

import com.example.springboot.model.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class CentroServiceImp implements CentroService{

    private LinkedList<Centro> centros = new LinkedList<>();

    @Override
    public void registrarCentro(Centro centro) {
        if (buscarCentroPorId(centro.getId()) == null) {
            centros.add(centro);
            System.out.println("Centro registrado: " + centro.getId());
        } else {
            System.out.println("El centro con ID " + centro.getId() + " ya está registrado.");
        }
    }

    @Override
    public Centro buscarCentroPorId(String idCentro) {
        for (Centro c : centros) {
            if (c.getId().equals(idCentro)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public LinkedList<Centro> obtenerCentros() {
        return centros;
    }

    @Override
    public void agregarPaquete(String idCentro, Paquete paquete) {
        Centro centro = buscarCentroPorId(idCentro);
        if (centro == null) {
            throw new IllegalStateException("Centro no encontrado");
        }
        if (!paquete.getEstado().equals("PENDIENTE")) {
            throw new IllegalArgumentException("Estado de paquete invalido, los paquetes solo pueden crearse con estado PENDIENTE");
        }
        for (Paquete p : centro.getPaquetesAlmacenados()) {
            if (p.getId().equals(paquete.getId())) {
                throw new IllegalArgumentException("Paquete duplicado en el centro");
            }
        }
        centro.agregarPaquete(paquete);
    }

    @Override
    public void agregarMensajero(String idCentro, Mensajero mensajero) {
        Centro centro = buscarCentroPorId(idCentro);
        if (centro == null) {
            throw new IllegalStateException("Centro no encontrado");
        }
        for(Mensajero m : centro.getMensajerosActuales()) {
            if (m.getId().equals(mensajero.getId())) {
                throw new IllegalArgumentException("Mensajero duplicado en el centro");
            }
        }
        if (!mensajero.getEstadoOperativo().equals("DISPONIBLE") && !mensajero.getEstadoOperativo().equals("EN_TRANSITO")) {
            throw new IllegalArgumentException("Estado operativo invalido");
        }
        centro.agregarMensajero(mensajero);
    }
}