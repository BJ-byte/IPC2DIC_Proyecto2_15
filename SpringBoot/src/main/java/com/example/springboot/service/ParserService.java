package com.example.springboot.service;

import com.example.springboot.model.ResultadoParser;

import java.io.InputStream;

public interface ParserService {

    ResultadoParser leerXml(InputStream inputStream);

}
