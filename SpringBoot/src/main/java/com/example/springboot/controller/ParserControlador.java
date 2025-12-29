package com.example.springboot.controller;

import com.example.springboot.model.ResultadoParser;
import com.example.springboot.service.ParserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/importar")
public class ParserControlador {

    private final ParserService parserService;

    public ParserControlador(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping
    public ResponseEntity<?> leerXml(@RequestParam("file")MultipartFile file) {
        try {

            ResultadoParser resultadoParser = parserService.leerXml(file.getInputStream());
            return ResponseEntity.ok(resultadoParser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error leyendo el archivo XML");
        }
    }
}
