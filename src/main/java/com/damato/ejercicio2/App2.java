package com.damato.ejercicio2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class App2 {
    public static void main(String[] args) {
        // CREAR ARBOL DOM
        ArrayList<Tarea> lista = new ArrayList<>();
        lista.add(new Tarea(1, Tipo.CITA, "Coche taller", false));
        lista.add(new Tarea(2, Tipo.CUMPLE, "peque", false));
        lista.add(new Tarea(3, Tipo.REUNION, "trabajo", false));
        lista.add(new Tarea(4, Tipo.CITA, "medico", true));
        lista.add(new Tarea(5, Tipo.REUNION, "familia", false));

        try {
        XmlCtrlDom xmlCtrlDom = new XmlCtrlDom();
        // CREAMOS LE ARBOL DOM A APRTIR DEL ARRAY

        //  crearXml(xmlCtrlDom, lista);

        //  GUARDAMOS LA TAREA CON EL ID 5 Y LA ELIMINAMOS DE TAREAS PENDIENTES
        //modificar tarea 5 a completada
          Tarea tarea =  modificarEstadoElementoXml(xmlCtrlDom);

          // AGREGAMOS LA TAREA 5 A TAREAS COMPLETADAS
          agragarTareaACompletada(xmlCtrlDom,tarea);

        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException |
                 TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    private static void agragarTareaACompletada(XmlCtrlDom xmlCtrlDom, Tarea t) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        File archivo = new File("./src/main/resources/reuniones.xml");
        Document doc= xmlCtrlDom.instanciarDocument(archivo);

        Element root = doc.getDocumentElement();

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        Node completadas = (Node) xpath.evaluate("//tareascompletadas",doc,XPathConstants.NODE);


        Element tarea = doc.createElement("tarea");
        tarea.setAttribute("id", String.valueOf(t.getId()));

        Element tipo = doc.createElement("tipo");
        tipo.setTextContent(t.getTipo().toString());
        tarea.appendChild(tipo);

        Element descripcion = doc.createElement("descripcion");
        descripcion.setTextContent(t.getDescripcion());
        tarea.appendChild(descripcion);

        t.setEstado(true);
        Element estado = doc.createElement("estado");
        estado.setTextContent((t.isEstado()?"completado":"pendiente"));
        tarea.appendChild(estado);

        completadas.appendChild(tarea);

        xmlCtrlDom.escribirDocumentAXmlPantalla(doc);
        xmlCtrlDom.escribirDocumentAXmlFichero(doc,archivo);

    }

    private static Tarea  modificarEstadoElementoXml(XmlCtrlDom xmlCtrlDom) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        // Lectura dom
        File archivo = new File("./src/main/resources/reuniones.xml");
        Document doc = xmlCtrlDom.instanciarDocument(archivo);

        Element root = doc.getDocumentElement();

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        Node modificar = (Node) xpath.evaluate("//*[@id='5']", doc, XPathConstants.NODE);

        NodeList hijos = modificar.getChildNodes();

        Tarea   tarea = new Tarea();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node nodo = hijos.item(i);

            tarea.setId(5);
            if (nodo instanceof Element) {
                switch (nodo.getNodeName()) {
                    case "tipo":
                        tarea.setTipo(Tipo.valueOf(((Element) nodo).getTextContent()));
                        break;
                    case "descripcion":
                        tarea.setDescripcion(((Element) nodo).getTextContent());
                        break;
                    case "estado":
                        tarea.setEstado(Boolean.parseBoolean(((Element) nodo).getTextContent()));
                        break;
                }
            }
        }

        //eliminamos le nodo de tareas pendientes
        modificar.getParentNode().removeChild(modificar);

        xmlCtrlDom.escribirDocumentAXmlPantalla(doc);

            return tarea;


    }

    private static void crearXml(XmlCtrlDom xmlCtrlDom, ArrayList<Tarea> lista) {
        try {
            Document doc = xmlCtrlDom.instanciarDocument();

            Element tareas = doc.createElement("tareas");
            doc.appendChild(tareas);

            Element tareaspendientes = doc.createElement("tareaspendientes");
            tareas.appendChild(tareaspendientes);

            Element tareascompletadas = doc.createElement("tareascompletadas");
            tareas.appendChild(tareascompletadas);


            for (Tarea t : lista) {
                Element tarea = doc.createElement("tarea");
                tarea.setAttribute("id", String.valueOf(t.getId()));

                Element tipo = doc.createElement("tipo");
                tipo.setTextContent(t.getTipo().toString());
                tarea.appendChild(tipo);

                Element descripcion = doc.createElement("descripcion");
                descripcion.setTextContent(t.getDescripcion());
                tarea.appendChild(descripcion);

                Element estado = doc.createElement("estado");
                estado.setTextContent((t.isEstado() ? "completado" : "pendiente"));
                tarea.appendChild(estado);

                if (t.isEstado()) tareascompletadas.appendChild(tarea);
                else tareaspendientes.appendChild(tarea);

            }

            // Indica la ruta del archivo. El método transform de la clase XML se encarga de escribir el contenido XML
            // en el archivo. Si el archivo no existe, lo creará automáticamente; si ya existe, lo sobrescribirá.
            File archivo = new File("./src/main/resources/reuniones.xml");


            xmlCtrlDom.escribirDocumentAXmlPantalla(doc);
            //xmlCtrlDom.escribirDocumentAXmlFichero(doc, archivo);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
