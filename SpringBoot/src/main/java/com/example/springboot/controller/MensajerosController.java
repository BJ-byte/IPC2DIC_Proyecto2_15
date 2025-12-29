package com.example.springboot.controller;

import com.example.springboot.model.Mensajero;
import com.example.springboot.service.MensajeroService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.LinkedList;

@RestController
@RequestMapping("/api/mensajeros")
public class MensajerosController {
    
    private final MensajeroService mensajeroService;

    public MensajerosController(MensajeroService mensajeroService) {
        this.mensajeroService = mensajeroService;
    }

    @GetMapping
    public ResponseEntity<LinkedList<Mensajero>> obtenerMensajeros() {
        return ResponseEntity.ok(mensajeroService.obtenerMensajeros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMensajeroPorId(@PathVariable String id) {
        Mensajero mensajero = mensajeroService.buscarMensajeroPorId(id);
        if (mensajero != null) {
            return ResponseEntity.ok(mensajero);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El mensajero con id " + id + " no existe");
        }
    }

    @PostMapping
    public ResponseEntity<?> crearMensajero(@RequestBody Mensajero mensajero) {
        try {
            mensajeroService.crearMensajero(mensajero);
            return ResponseEntity.status(HttpStatus.CREATED).body("Mensajero creado exitosamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoOperativo(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            mensajeroService.cambiarEstadoOperativo(id, nuevoEstado);
            return ResponseEntity.ok("Estado operativo actualizado exitosamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/centro")
    public ResponseEntity<?> cambiarCentro(@PathVariable String id, @RequestParam String nuevoCentro) {
        try {
            mensajeroService.cambiarCentro(id, nuevoCentro);
            return ResponseEntity.ok("Centro asignado actualizado exitosamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
