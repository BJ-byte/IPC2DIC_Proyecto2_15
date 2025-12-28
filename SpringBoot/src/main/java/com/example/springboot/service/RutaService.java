package com.example.springboot.service;

import com.example.springboot.model.Ruta;

import java.util.LinkedList;

public interface RutaService {

    LinkedList<Ruta> obtenerRutas();

    Ruta buscarRutaPorId(String id);

    void crearRuta(Ruta ruta);

    void actualizarRuta(String id, Ruta rutaActualizada);

    void eliminarRuta(String id);

}