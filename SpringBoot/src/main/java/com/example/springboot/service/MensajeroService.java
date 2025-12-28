package com.example.springboot.service;

import com.example.springboot.model.Mensajero;
import java.util.LinkedList;

public interface MensajeroService {

    LinkedList<Mensajero> obtenerMensajeros();

    Mensajero buscarMensajeroPorId(String id);

    void crearMensajero(Mensajero mensajero);

    void cambiarEstadoOperativo(String id, String nuevoEstado);

    void cambiarCentro(String id, String nuevoCentro);

}
