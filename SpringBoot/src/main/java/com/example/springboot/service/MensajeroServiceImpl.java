package com.example.springboot.service;

import com.example.springboot.model.Centro;
import com.example.springboot.model.Mensajero;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MensajeroServiceImpl implements MensajeroService {
    
    private final CentroService centroService;
    
    public MensajeroServiceImpl(CentroService centroService) {
        this.centroService = centroService;
    }

    @Override
    public LinkedList<Mensajero> obtenerMensajeros() {
        LinkedList<Mensajero> mensajeros = new LinkedList<>();

        for (Centro c : centroService.obtenerCentros()) {
            mensajeros.addAll(c.getMensajerosActuales());
        }
        return mensajeros;
    }

    @Override
    public Mensajero buscarMensajeroPorId(String id) {
        for (Centro c : centroService.obtenerCentros()) {
            for (Mensajero m : c.getMensajerosActuales()) {
                if (m.getId().equals(id)) {
                    return m;
                }
            }
        }
        return null;
    }

    @Override
    public void crearMensajero(Mensajero mensajero) {
        
        if (buscarMensajeroPorId(mensajero.getId()) != null) {
            throw new IllegalStateException("El mensajero ya existe");
        }
        Centro centro = centroService.buscarCentroPorId(mensajero.getCentro());
        if (centro == null) {
            throw new IllegalStateException("El centro asignado no existe");
        }
        if (!mensajero.getEstadoOperativo().equals("DISPONIBLE") && !mensajero.getEstadoOperativo().equals("EN_TRANSITO")) {
            throw new IllegalStateException("Estado operativo invalido");
        }

        centro.getMensajerosActuales().add(mensajero);
        System.out.println("Mensajero creado: " + mensajero.getId());
    }

    @Override
    public void cambiarEstadoOperativo(String id, String nuevoEstado) {
        
        Mensajero mensajero = buscarMensajeroPorId(id);
        if (mensajero == null) {
            throw new IllegalStateException("El mensajero no existe");
        }
        if (!nuevoEstado.equals("DISPONIBLE") && !nuevoEstado.equals("EN_TRANSITO")) {
            throw new IllegalStateException("Estado operativo invalido");
        }
        mensajero.setEstadoOperativo(nuevoEstado);
        System.out.println("Estado operativo del mensajero " + id + " cambiado a " + nuevoEstado);
    }

    @Override
    public void cambiarCentro(String id, String nuevoCentro) {
        
        Mensajero mensajero = buscarMensajeroPorId(id);
        if (mensajero == null) {
            throw new IllegalStateException("El mensajero no existe");
        }

        if (mensajero.getEstadoOperativo().equals("EN_TRANSITO")) {
            throw new IllegalStateException("No se puede cambiar el centro de un mensajero en transito");
        }
        
        Centro centroDestino = centroService.buscarCentroPorId(nuevoCentro);
        Centro centroActual = centroService.buscarCentroPorId(mensajero.getCentro());

        if (centroDestino == null) {
            throw new IllegalStateException("El centro destino no existe");
        }
        
        centroActual.getMensajerosActuales().remove(mensajero);
        mensajero.setCentro(nuevoCentro);
        centroDestino.getMensajerosActuales().add(mensajero);
        System.out.println("Centro del mensajero " + id + " cambiado a " + nuevoCentro);
        }
}
