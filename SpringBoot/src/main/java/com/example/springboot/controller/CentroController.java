package com.example.springboot.controller;

import com.example.springboot.model.*;
import com.example.springboot.service.CentroService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.List;




@RestController
@RequestMapping("/api/centros")
public class CentroController {
    private final CentroService centroService;

    public CentroController(CentroService centroService) {
        this.centroService = centroService;
    }

    @GetMapping
    public ResponseEntity<List<Centro>> obtenerCentros() {
        return ResponseEntity.ok(centroService.obtenerCentros());
    }

    @GetMapping("/{idCentro}")
    public ResponseEntity<?> obtenerCentro(@PathVariable String idCentro) {
        try {
        Centro centro = centroService.buscarCentroPorId(idCentro);
        if (centro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Centro no encontrado");
}
        return ResponseEntity.ok(centro);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{idCentro}/paquetes")
    public ResponseEntity<?> obtenerPaquetes(@PathVariable String idCentro) {
        try {
        Centro centro = centroService.buscarCentroPorId(idCentro);
        if (centro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Centro no encontrado");
        }
        return ResponseEntity.ok(centro.getPaquetesAlmacenados());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{idCentro}/mensajeros")
    public ResponseEntity<?> obtenerMensajeros(@PathVariable String idCentro) {
        try {
        Centro centro = centroService.buscarCentroPorId(idCentro);
        if (centro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Centro no encontrado");
        }
        return ResponseEntity.ok(centro.getMensajerosActuales());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
