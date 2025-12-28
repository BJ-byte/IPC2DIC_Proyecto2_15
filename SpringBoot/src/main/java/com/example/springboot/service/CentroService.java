package com.example.springboot.service;

import com.example.springboot.model.Centro;
import com.example.springboot.model.Mensajero;
import com.example.springboot.model.Paquete;
import java.util.LinkedList;

public interface CentroService {

    void registrarCentro(Centro centro);

    Centro buscarCentroPorId(String idCentro);

    LinkedList<Centro> obtenerCentros();

    void agregarPaquete(String idCentro, Paquete paquete);

    void agregarMensajero(String idCentro, Mensajero mensajero);

}
