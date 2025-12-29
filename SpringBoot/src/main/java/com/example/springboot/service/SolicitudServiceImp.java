package com.example.springboot.service;

import com.example.springboot.model.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class SolicitudServiceImp implements SolicitudService{

    private final LinkedList<Solicitud> colaSolicitudes = new LinkedList<>();

    private final CentroService centroService;
    private final MensajeroService mensajeroService;
    private final PaqueteService paqueteService;
    private final RutaService rutaService;

    public SolicitudServiceImp(CentroService centroService, MensajeroService mensajeroService, PaqueteService paqueteService, RutaService rutaService) {
        
        this.centroService = centroService;
        this.mensajeroService = mensajeroService;
        this.paqueteService = paqueteService;
        this.rutaService = rutaService;

    }
    
    @Override
    public LinkedList<Solicitud> obtenerSolicitudes() {
        ordenarColaPorPrioridad();
        return colaSolicitudes;
    }

    @Override
    public void crearSolicitud(Solicitud solicitud) {
        
        for (Solicitud s : colaSolicitudes) {
            if (s.getId().equals(solicitud.getId())) {
                throw new IllegalArgumentException("Ya existe una solicitud con este ID: ");
            }
        }

        Paquete paqueteExistente = paqueteService.buscarPaquetePorId(solicitud.getPaquete());
        if (paqueteExistente == null) {
            throw new IllegalArgumentException("No existe un paquete con el ID: " + solicitud.getPaquete());
        }

        Centro centroOrigen = centroService.buscarCentroPorId(paqueteExistente.getCentroActual());
        Centro centroDestino = centroService.buscarCentroPorId(paqueteExistente.getDestino());

        if (centroOrigen == null || centroDestino == null) {
            throw new IllegalArgumentException("El centro de origen o destino del paquete no existe.");
        }

        boolean mensajeroDisponible = false;

        for (Mensajero m : mensajeroService.obtenerMensajeros()) {
            if (m.getCentro().equals(centroOrigen.getId()) && m.getEstadoOperativo().equals("DISPONIBLE") && m.getCapacidad() >= paqueteExistente.getPeso()) {
                mensajeroDisponible = true;
                break;
            }
        }

        if (!mensajeroDisponible) {
            throw new IllegalArgumentException("No hay mensajeros disponibles en el centro de origen para este paquete.");
        }


        boolean rutaExistente = false;

        for (Ruta r : rutaService.obtenerRutas()) {
            if (r.getOrigen().equals(centroOrigen.getId()) && r.getDestino().equals(centroDestino.getId())) {
                rutaExistente = true;
                break;
            }
        }

        if (!rutaExistente) {
            throw new IllegalArgumentException("No existe una ruta desde el centro " + centroOrigen.getId() + " hasta el centro " + centroDestino.getId() + ".");
        }
        
        colaSolicitudes.add(solicitud);
        ordenarColaPorPrioridad();
        System.out.println("Solicitud creada con ID: " + solicitud.getId());
    }

    @Override
    public void eliminarSolicitud(String id) {
        if (colaSolicitudes.isEmpty()) {
            throw new IllegalArgumentException("No hay solicitudes en la cola.");
        }

        for (Solicitud s : colaSolicitudes) {
            if (s.getId().equals(id)) {
                colaSolicitudes.remove(s);
                System.out.println("Solicitud eliminada con ID: " + id);
                return;
            }
        }
        
        throw new IllegalArgumentException("No se encontró una solicitud con el ID: " + id);
    }

    @Override
    public String procesarUnaSolicitud() {
        if (colaSolicitudes.isEmpty()) {
            return "No hay solicitudes para procesar.";
        }

        Solicitud s = colaSolicitudes.removeFirst();
        return procesarSolicitud(s);
    }

    @Override
    public String procesarNsolicitudes(int n) {
        
        if (colaSolicitudes.isEmpty()) {
            return "No hay solicitudes para procesar.";
        }

        StringBuilder resultados = new StringBuilder();

        for (int i = 0; i < n && !colaSolicitudes.isEmpty(); i++) {
            Solicitud s = colaSolicitudes.removeFirst();
            resultados.append(procesarSolicitud(s)).append("\n");
        }

        return resultados.toString();
    }

    private String procesarSolicitud(Solicitud solicitud) {

        Paquete paquete = paqueteService.buscarPaquetePorId(solicitud.getPaquete());
        
        if (paquete == null) {
            return "Paquete con ID " + solicitud.getPaquete() + " no encontrado.";
        }
        if (!paquete.getEstado().equals("PENDIENTE")){
            return "El paquete con ID " + solicitud.getPaquete() + " ya ha sido procesado.";
        }

        Mensajero mensajeroDisponible = null;

        for (Mensajero m : mensajeroService.obtenerMensajeros()) {
            if (m.getEstadoOperativo().equals("DISPONIBLE") && m.getCapacidad() >= paquete.getPeso()) {
                mensajeroDisponible = m;
                break;
            }
        }

        if (mensajeroDisponible == null) {
            return "No hay mensajeros disponibles para procesar la solicitud con ID " + solicitud.getId() + ".";
        }

        Centro centroActual = centroService.buscarCentroPorId(paquete.getCentroActual());
        Centro centroDestino = centroService.buscarCentroPorId(paquete.getDestino());

        boolean rutaExistente = false;

        for (Ruta r : rutaService.obtenerRutas()) {
            if (r.getOrigen().equals(centroActual.getId()) && r.getDestino().equals(centroDestino.getId())) {
                rutaExistente = true;
                break;
            }
        }

        if (!rutaExistente) {
            return "No existe una ruta desde el centro " + centroActual.getId() + " hasta el centro " + centroDestino.getId() + ".";
        }

        mensajeroDisponible.setEstadoOperativo("EN_TRANSITO");
        paquete.setEstado("EN_TRANSITO");

        return "Solicitud con ID " + solicitud.getId() + " procesada exitosamente. Mensajero " + mensajeroDisponible.getId() + " asignado al paquete " + paquete.getId();
    }

    private void ordenarColaPorPrioridad() {
        for (int i = 0; i < colaSolicitudes.size() - 1; i++) {
            for (int j = i + 1; j < colaSolicitudes.size(); j++) {
                if (colaSolicitudes.get(j).getPrioridad() > colaSolicitudes.get(i).getPrioridad()) {
                    Solicitud temp = colaSolicitudes.get(i);
                    colaSolicitudes.set(i, colaSolicitudes.get(j));
                    colaSolicitudes.set(j, temp);
                }
            }
        }
    }
}
