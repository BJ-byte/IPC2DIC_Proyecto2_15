package com.example.springboot.service;

import com.example.springboot.model.Solicitud;

import java.util.LinkedList;

public class SolicitudServiceImp implements SolicitudService{
    private final static LinkedList<Solicitud> solicitudes = new LinkedList<>();
}
