package com.example.springboot.service;

import com.example.springboot.model.Centro;
import com.example.springboot.model.ResultadoParser;

import java.io.InputStream;
import java.util.LinkedList;

public interface ParserService {
    ResultadoParser leerXml(InputStream inputStream);
}
