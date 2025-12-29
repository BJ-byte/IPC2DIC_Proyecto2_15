package com.example.springboot.controller;

import com.example.springboot.model.Ruta;
import com.example.springboot.service.RutaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;




@RestController
@RequestMapping("/api/rutas")
public class RutaController {
    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping
    public ResponseEntity<LinkedList<Ruta>> obtenerRutas() {
        return ResponseEntity.ok(rutaService.obtenerRutas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerRutaPorId(@PathVariable String id) {
        Ruta ruta = rutaService.buscarRutaPorId(id);
        if (ruta != null) {
            return ResponseEntity.ok(ruta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La ruta con id " + id + " no existe");
        }
    }

    @PostMapping
    public ResponseEntity<?> crearRuta(@RequestBody Ruta ruta) {
        try {
            rutaService.crearRuta(ruta);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ruta creada exitosamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRuta(@PathVariable String id, @RequestBody Ruta rutaActualizada) {
        try {
            rutaService.actualizarRuta(id, rutaActualizada);
            return ResponseEntity.ok("Ruta actualizada exitosamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRuta(@PathVariable String id) {
        try {
            rutaService.eliminarRuta(id);
            return ResponseEntity.ok("Ruta eliminada exitosamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
