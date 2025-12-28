package com.example.springboot.service;

import com.example.springboot.model.Centro;
import com.example.springboot.model.Ruta;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class RutaServiceImp implements RutaService{
    
    private final LinkedList<Ruta> rutas = new LinkedList<>();
    private final CentroService centroService;

    public RutaServiceImp(CentroService centroService) {
        this.centroService = centroService;
    }

    @Override
    public LinkedList<Ruta> obtenerRutas() {
        return rutas;
    }

    @Override
    public Ruta buscarRutaPorId(String id) {
        for (Ruta r : rutas) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void crearRuta(Ruta ruta) {

        if (buscarRutaPorId(ruta.getId()) != null) {
            throw new IllegalStateException("La ruta ya existe");
        }

        Centro origen = centroService.buscarCentroPorId(ruta.getOrigen());
        Centro destino = centroService.buscarCentroPorId(ruta.getDestino());

        if (origen == null || destino == null) {
            throw new IllegalStateException("Centro origen o destino no existe");
        }

        for (Ruta r : rutas) {
            if (r.getOrigen().equals(ruta.getOrigen())
                && r.getDestino().equals(ruta.getDestino())) {
                throw new IllegalStateException("Ruta duplicada origen-destino");
            }
        }

        rutas.add(ruta);
        System.out.println("Ruta creada: " + ruta.getId());
    }

    @Override
    public void actualizarRuta(String id, Ruta rutaActualizada) {
        Ruta existente = buscarRutaPorId(id);

        if (existente == null) {
            throw new IllegalStateException("Ruta no encontrada");
        }

        existente.setOrigen(rutaActualizada.getOrigen());
        existente.setDestino(rutaActualizada.getDestino());
        existente.setDistancia(rutaActualizada.getDistancia());

    }

    @Override
    public void eliminarRuta(String id) {
        Ruta ruta = buscarRutaPorId(id);

        if (ruta == null) {
            throw new IllegalStateException("Ruta no encontrada");
        }
        rutas.remove(ruta);
    }
}
