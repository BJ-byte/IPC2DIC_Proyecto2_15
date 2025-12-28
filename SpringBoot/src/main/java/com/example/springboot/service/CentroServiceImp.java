package com.example.springboot.service;

import com.example.springboot.model.Centro;
import com.example.springboot.model.Mensajero;
import com.example.springboot.model.Paquete;
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
            //System.out.println("Centro con ID " + idCentro + " no encontrado.");
            throw new IllegalStateException("Centro no encontrado");
        }
        centro.agregarPaquete(paquete);
    }

    @Override
    public void agregarMensajero(String idCentro, Mensajero mensajero) {
        Centro centro = buscarCentroPorId(idCentro);
        if (centro == null) {
            //System.out.println("Centro con ID " + idCentro + " no encontrado.");
            throw new IllegalStateException("Centro no encontrado");
        }
        centro.agregarMensajero(mensajero);
    }

    public void limpiarCentros() {
        centros.clear();
    }
}