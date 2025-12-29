package com.example.springboot.controller;

import com.example.springboot.model.Paquete;
import com.example.springboot.service.PaqueteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.LinkedList;


@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {
    
    private final PaqueteService paqueteService;

    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }

    @GetMapping
    public ResponseEntity<LinkedList<Paquete>> obtenerPaquetes() {
        return ResponseEntity.ok(paqueteService.obtenerPaquetes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPaquetePorId(@PathVariable String id) {
        Paquete paquete = paqueteService.buscarPaquetePorId(id);
        if (paquete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Paquete no encontrado");
        }
        return ResponseEntity.ok(paquete);
    }
    
    @PostMapping
    public ResponseEntity<?> crearPaquete(@RequestBody Paquete paquete) {
        try {
            paqueteService.crearPaquete(paquete);
            return ResponseEntity.status(HttpStatus.CREATED).body("Paquete creado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPaquete(@PathVariable String id, @RequestBody Paquete paqueteActualizado) {
        try {
            paqueteService.actualizarPaquete(id, paqueteActualizado);
            return ResponseEntity.ok("Paquete actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaquete(@PathVariable String id) {
        try {
            paqueteService.eliminarPaquete(id);
            return ResponseEntity.ok("Paquete eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
