package com.example.springboot.service;

import com.example.springboot.model.Solicitud;
import java.util.LinkedList;

public interface SolicitudService {

    LinkedList<Solicitud> obtenerSolicitudes();

    String procesarUnaSolicitud();

    String procesarNsolicitudes(int n);

    void crearSolicitud(Solicitud solicitud);

    void eliminarSolicitud(String id);

}
