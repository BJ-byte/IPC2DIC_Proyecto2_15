package com.example.springboot.controller;

import com.example.springboot.model.Solicitud;
import com.example.springboot.service.SolicitudService;
import java.util.LinkedList;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public ResponseEntity<LinkedList<Solicitud>> obtenerSolicitudes() {
        return ResponseEntity.ok(solicitudService.obtenerSolicitudes());
    }

    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody Solicitud solicitud) {
        try {
            solicitudService.crearSolicitud(solicitud);
            return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud creada con ID: " + solicitud.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSolicitud(@PathVariable String id) {
        try {
            solicitudService.eliminarSolicitud(id);
            return ResponseEntity.ok("Solicitud eliminada con ID: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarUnaSolicitud() {
        return ResponseEntity.ok(solicitudService.procesarUnaSolicitud());
    }
    
    @PostMapping("procesar/{n}")
    public ResponseEntity<?> procesarNsolicitudes(@PathVariable int n) {
        return ResponseEntity.ok(solicitudService.procesarNsolicitudes(n));
    }
}
