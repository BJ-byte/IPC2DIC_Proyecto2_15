package com.example.springboot.service;

import com.example.springboot.model.Paquete;
import java.util.LinkedList;

public interface PaqueteService {

    LinkedList<Paquete> obtenerPaquetes();

    Paquete buscarPaquetePorId(String id);

    void crearPaquete(Paquete paquete);

    void actualizarPaquete(String id, Paquete paqueteActualizado);

    void eliminarPaquete(String id);

}
