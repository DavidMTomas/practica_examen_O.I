package com.damato.ejercicio3.main;

import com.damato.ejercicio3.controlador.ControladorViaje;
import com.damato.ejercicio3.modelo.ViajeDAO;
import com.damato.ejercicio3.vista.JFViaje;

public class App3 {
    public static void main(String[] args) {
        ViajeDAO modelo= new ViajeDAO();
        JFViaje vista = new JFViaje();

        ControladorViaje controlador= new ControladorViaje(modelo,vista);

    }
}
