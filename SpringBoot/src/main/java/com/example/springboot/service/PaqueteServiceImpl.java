package com.example.springboot.service;

import com.example.springboot.model.Centro;
import com.example.springboot.model.Paquete;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class PaqueteServiceImpl implements PaqueteService{

    private final CentroService centroService;

    public PaqueteServiceImpl(CentroService centroService) {
        this.centroService = centroService;
    }

    private boolean estadoValido(String estado) {
        return estado.equals("PENDIENTE")
            || estado.equals("EN_TRANSITO")
            || estado.equals("ENTREGADO");
    }

    @Override
    public LinkedList<Paquete> obtenerPaquetes() {
        LinkedList<Paquete> paquetes = new LinkedList<>();

        for (Centro c : centroService.obtenerCentros()) {
            paquetes.addAll(c.getPaquetesAlmacenados());
        }
        return paquetes;
    }

    @Override
    public Paquete buscarPaquetePorId(String id) {
        for (Centro c : centroService.obtenerCentros()) {
            for (Paquete p : c.getPaquetesAlmacenados()) {
                if (p.getId().equals(id)) {
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public void crearPaquete(Paquete paquete) {
        if (!paquete.getEstado().equals("PENDIENTE")) {
            throw new IllegalArgumentException("Estado de paquete invalido, los paquetes solo pueden crearse con estado PENDIENTE");
        }
        if (buscarPaquetePorId(paquete.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un paquete con el ID proporcionado");
        }

        Centro centroDestino = centroService.buscarCentroPorId(paquete.getDestino());
        Centro centroActual = centroService.buscarCentroPorId(paquete.getCentroActual());

        if (centroDestino == null || centroActual == null) {
            throw new IllegalArgumentException("Centro destino o centro actual no existe");
        }

        centroActual.getPaquetesAlmacenados().add(paquete);
        System.out.println("Paquete creado exitosamente" + paquete.getId());
    }

    @Override
    public void actualizarPaquete(String id, Paquete paqueteActualizado) {
        Paquete paqueteExistente = buscarPaquetePorId(id);
        if (paqueteExistente == null) {
            throw new IllegalArgumentException("Paquete con ID proporcionado no existe");
        }
        if (!estadoValido(paqueteActualizado.getEstado())) {
            throw new IllegalArgumentException("Estado de paquete invalido");
        }

        Centro centroDestino = centroService.buscarCentroPorId(paqueteActualizado.getDestino());
        if (centroDestino == null) {
            throw new IllegalArgumentException("Centro destino no existe");
        }
        paqueteExistente.setCliente(paqueteActualizado.getCliente());
        paqueteExistente.setPeso(paqueteActualizado.getPeso());
        paqueteExistente.setDestino(paqueteActualizado.getDestino());
        paqueteExistente.setEstado(paqueteActualizado.getEstado());
        paqueteExistente.setCentroActual(paqueteActualizado.getCentroActual());
        System.out.println("Paquete actualizado exitosamente: " + id);
    }

    @Override
    public void eliminarPaquete(String id) {
        for (Centro c : centroService.obtenerCentros()) {
            for (Paquete paquete : c.getPaquetesAlmacenados()) {
                if (paquete.getId().equals(id)) {
                    if (paquete.getEstado().equals("ENTREGADO") || paquete.getEstado().equals("EN_TRANSITO")) {
                        throw new IllegalStateException("No se puede eliminar un paquete que ya ha sido entregado o está en tránsito");
                    }
                    c.getPaquetesAlmacenados().remove(paquete);
                    System.out.println("Paquete eliminado exitosamente: " + id);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Paquete con ID proporcionado no existe");
    }
}
