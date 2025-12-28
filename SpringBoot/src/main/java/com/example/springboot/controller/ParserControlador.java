package com.example.springboot.controller;


import com.example.springboot.model.Centro;
import com.example.springboot.model.ResultadoParser;
import com.example.springboot.service.CentroServiceImp;
import com.example.springboot.service.ParserService;
import com.example.springboot.service.RutaServiceImp;
import com.example.springboot.service.SolicitudServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.LinkedList;

@RestController
@RequestMapping("/api/importar")
public class ParserControlador {

    private final ParserService parserService;
    private CentroServiceImp centroServiceImp;
    private RutaServiceImp rutaServiceImp;
    private SolicitudServiceImp solicitudServiceImp;

    public ParserControlador(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping
    public ResponseEntity<?> leerXml(MultipartFile file) {
        try {
            ResultadoParser resultadoParser = parserService.leerXml(file.getInputStream());



            return ResponseEntity.ok(resultadoParser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error leyendo el archivo XML");
        }
    }
}
