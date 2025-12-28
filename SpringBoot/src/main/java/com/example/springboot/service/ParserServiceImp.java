package com.example.springboot.service;

import com.example.springboot.model.*;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.LinkedList;

@Service
public class ParserServiceImp implements ParserService{

    private final LinkedList<Centro> centros = new LinkedList<>();
    private final LinkedList<Ruta> rutas = new LinkedList<>();
    private final LinkedList<Solicitud> solicitudes = new LinkedList<>();

    public ParserServiceImp() {
    }

    @Override
    public ResultadoParser leerXml(InputStream inputStream) {

        try {
            centros.clear();
            rutas.clear();
            solicitudes.clear();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            //centros
            NodeList listaCentros = doc.getElementsByTagName("centro");

            for (int i = 0; i < listaCentros.getLength(); i++) {
                Element centro0 = (Element) listaCentros.item(i);

                String id = centro0.getAttribute("id");
                String nombre = centro0.getElementsByTagName("nombre").item(0).getTextContent();
                String ciudad = centro0.getElementsByTagName("ciudad").item(0).getTextContent();
                String capacidad0 = centro0.getElementsByTagName("capacidad").item(0).getTextContent();
                LinkedList<Paquete> paquetesAlmacenados = new LinkedList<>();
                LinkedList<Mensajero> mensajerosActuales = new LinkedList<>();

                try {
                    int capacidad = Integer.parseInt(capacidad0);

                    //se crea un objeto centro con lo obtenido del xml
                    Centro centro = new Centro(id, nombre, ciudad, capacidad, paquetesAlmacenados, mensajerosActuales);

                    // comprobacion por medio de id de que el centro todavia no se halla almacenado
                    boolean existe = false;
                    for (Centro c : centros) {
                        if (c.getId().equals(id)) {
                            System.out.println("ERROR. Desde parser: el centro con id "+id+" ya fue registrado. El centro con nombre "+nombre+" no fue agregado al sistema\n");
                            existe = true;
                        }
                    }

                    if (!existe) {
                        centros.add(centro);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("ERROR. Desde parser: el atributo capacidad no es un numero. El centro con nombre "+nombre+" no fue agregado al sistema\n");
                }
            }

            //rutas
            NodeList listaRutas = doc.getElementsByTagName("ruta");

            for (int i = 0; i < listaRutas.getLength(); i++) {
                Element ruta0 = (Element) listaRutas.item(i);

                String id = ruta0.getAttribute("id");
                String origen = ruta0.getAttribute("origen");
                String destino = ruta0.getAttribute("destino");
                String distancia0 = ruta0.getAttribute("distancia");

                try {
                    int distancia = Integer.parseInt(distancia0);

                    Ruta ruta = new Ruta(id, origen, destino, distancia);
                    boolean verificarCentros = ruta.verificarCentros(centros);
                    boolean verificarDuplicados = ruta.verificarDuplicados(rutas);

                    if (verificarCentros && verificarDuplicados) {
                        //añade la ruta
                        rutas.add(ruta);
                        System.out.println("Desde parser: la ruta con id "+id+" fue agregada al sistema\n");
                    } else if (!verificarCentros) {
                        System.out.println("ERROR. Desde parser: la ruta con id "+id+" no fue agregada al sistema, verifique los centros existentes en el sistema\n");
                    } else {
                        System.out.println("ERROR. Desde parser: la ruta con id "+id+" no fue agregada al sistema, porque ya existe una ruta con origen "+origen+" y destino "+destino+"\n");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("ERROR. Desde parser: el atributo distancia no es un numero. La ruta con id "+id+" no fue agregada al sistema\n");
                }
            }

            //mensajeros
            NodeList listaMensajeros = doc.getElementsByTagName("mensajero");

            for (int i = 0; i < listaMensajeros.getLength(); i++) {
                Element mensajero0 = (Element) listaMensajeros.item(i);
                String id = mensajero0.getAttribute("id");
                String nombre = mensajero0.getAttribute("nombre");
                String capacidad0 = mensajero0.getAttribute("capacidad");
                String centro = mensajero0.getAttribute("centro");

                try {
                    int capacidad = Integer.parseInt(capacidad0);

                    Mensajero mensajero = new Mensajero(id, nombre, capacidad, centro, "DISPONIBLE");
                    boolean comprobarCentro = mensajero.comprobarCentro(centros);
                    boolean verificarDuplicados = mensajero.verificarDuplicados(centros);

                    if (comprobarCentro && !verificarDuplicados) {
                        for (Centro c : centros) {
                            if (c.getId().equals(centro)){
                                c.agregarMensajero(mensajero);
                                System.out.println("Desde parser: El mensajero fue agregado al centro "+centro);
                            }
                        }
                    } else if (verificarDuplicados) {
                        System.out.println("ERROR. Desde parser: El mensajero "+id+" ya se encuentra registrado en el sistema\n");
                    } else {
                        System.out.println("ERROR. Desde parser: El centro "+centro+" asignado al mensajero "+id+" no esta registrado en el sistema\n");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("ERROR. Desde parser: la capacidad no es un numero. El mensajero con id "+id+" no fue agregado al sistema\n");
                }
            }

            //paquetes
            NodeList listaPaquetes = doc.getElementsByTagName("paquete");

            for (int i = 0; i < listaPaquetes.getLength(); i++) {
                Element paquete0 = (Element) listaPaquetes.item(i);

                String id = paquete0.getAttribute("id");
                String cliente = paquete0.getAttribute("cliente");
                String peso0 = paquete0.getAttribute("peso");
                String destino = paquete0.getAttribute("destino");
                String estado = paquete0.getAttribute("estado");
                String centroActual = paquete0.getAttribute("centroActual");

                try {
                    int peso = Integer.parseInt(peso0);

                    Paquete paquete = new Paquete(id, cliente, peso, destino, estado, centroActual);
                    boolean verificarCentros = paquete.verificarCentros(centros);
                    boolean verificarDuplicados = paquete.verificarDuplicados(centros);

                    if (verificarCentros && !verificarDuplicados) {
                        for (Centro c : centros) {
                            if (c.getId().equals(centroActual)){
                                c.agregarPaquete(paquete);
                                System.out.println("Desde parser: El paquete "+id+" fue agregado al centro "+centroActual);
                            }
                        }
                    } else if (!verificarCentros) {
                        System.out.println("ERROR. Desde parser: verifique si el centro origen "+centroActual+" o el centro destino "+destino+" existen en el sistema. El paquete con id "+id+" no esta registrado en el sistema\n");
                    } else {
                        System.out.println("ERROR. Desde parser: El paquete "+id+" ya se encuentra registrado en el sistema\n");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("ERROR. Desde parser: el peso no es un numero. El paquete con id "+id+" no fue agregado al sistema\n");
                }
            }

            //solicitudes
            NodeList listaSolicitudes = doc.getElementsByTagName("solicitud");
            for (int i = 0; i < listaSolicitudes.getLength(); i++) {
                Element solicitud0 = (Element) listaSolicitudes.item(i);

                String id = solicitud0.getAttribute("id");
                String tipo = solicitud0.getAttribute("tipo");
                String paquete = solicitud0.getAttribute("paquete");
                String prioridad0 = solicitud0.getAttribute("prioridad");

                try {
                    int prioridad = Integer.parseInt(prioridad0);

                    Solicitud solicitud = new Solicitud(id, tipo, paquete, prioridad);
                    boolean verificarDuplicados = solicitud.verificarDuplicados(solicitudes);
                    boolean verificarPaquete = solicitud.verificarPaquete(centros);

                    if (verificarPaquete && !verificarDuplicados) {
                        solicitudes.add(solicitud);
                        System.out.println("Desde parser: la solicitud "+id+" fue agregada a la cola\n");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("ERROR. Desde parser: la prioridad no es un numero. La solicitud con id "+id+" no fue agregado al sistema\n");
                }
            }

            for (Centro c : centros) {
                int totalPaquetes = c.getPaquetesAlmacenados().size();
                System.out.println("Centro id: "+c.getId()+" tiene "+c.getMensajerosActuales().size()+" mensajeros y "+totalPaquetes+" paquetes");
                System.out.println("Los mensajeros del centro son:");
                for (Mensajero m : c.getMensajerosActuales()) {
                    System.out.println("Mensajero id: "+m.getId()+" nombre: "+m.getNombre()+" capacidad: "+m.getCapacidad()+" estado: "+m.getEstadoOperativo());
                }
                System.out.println("Los paquetes del centro son:");
                for (Paquete p : c.getPaquetesAlmacenados()) {
                    System.out.println("Paquete id: "+p.getId()+" cliente: "+p.getCliente()+" peso: "+p.getPeso()+" destino: "+p.getDestino()+" estado: "+p.getEstado());
                }
            }
            for (Ruta r : rutas) {
                System.out.println("Ruta id: "+r.getId()+" de "+r.getOrigen()+" a "+r.getDestino()+" con distancia "+r.getDistancia());
            }
            for (Solicitud s : solicitudes) {
                System.out.println("Solicitud id: "+s.getId()+" tipo "+s.getTipo()+" paquete "+s.getPaquete()+" prioridad "+s.getPrioridad());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultadoParser(this.centros, this.rutas, this.solicitudes);
    }
}
