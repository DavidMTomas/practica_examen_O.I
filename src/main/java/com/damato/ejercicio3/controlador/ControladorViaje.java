package com.damato.ejercicio3.controlador;

import com.damato.ejercicio3.modelo.Viaje;
import com.damato.ejercicio3.modelo.ViajeDAO;
import com.damato.ejercicio3.vista.JFViaje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControladorViaje implements ActionListener {
    private ViajeDAO modelo;
    private JFViaje vista;

    public ControladorViaje(ViajeDAO modelo, JFViaje vista) {
        this.modelo = modelo;
        this.vista = vista;


        vista.btnContar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnInsertar.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnContar) {
            contarElementos();
        } else if (e.getSource() == vista.btnEliminar) {
            eliminarViaje();
        } else if (e.getSource() == vista.btnInsertar) {
            insertarViaje();
        }

    }

    private void insertarViaje() {
        String origen = vista.tfOrigen.getText();
        String destino = vista.tfDestino.getText();
        float precio = Float.parseFloat(vista.tfPrecio.getText());
        Viaje v = new Viaje(origen, destino, precio);

        int resultado = modelo.insertarViaje(v);
        if (resultado == 1) {
            JOptionPane.showMessageDialog(vista, "Viaje insertado");
        }
        limpiar();
    }

    private void limpiar() {
        vista.tfOrigen.setText("");
        vista.tfDestino.setText("");
        vista.tfPrecio.setText("");

        actualizar();
    }

    private void actualizar() {
        DefaultTableModel modeloT = new DefaultTableModel();
        vista.table1.setModel(modeloT);

        modeloT.addColumn("origen");
        modeloT.addColumn("destino");
        modeloT.addColumn("precio");

        Object[] columna = new Object[3];

        List<Viaje> lista = modelo.viajes();

        int numRegistros =lista.size();

        for (int i = 0; i < numRegistros; i++) {
            columna[0] = lista.get(i).getOrigen();
            columna[1] = lista.get(i).getDestino();
            columna[2] = lista.get(i).getPrecio();
            modeloT.addRow(columna);
        }

    }

    private void eliminarViaje() {
        String origen = vista.tfOrigen.getText();
        String destino = vista.tfDestino.getText();

        int resultado = modelo.eliminarViaje(origen, destino);

        if (resultado == 1) {
            JOptionPane.showMessageDialog(vista, "Viaje eliminado");
        } else if (resultado == 0) JOptionPane.showMessageDialog(vista, "Viaje no eliminado");
        else if (resultado > 1) JOptionPane.showMessageDialog(vista, "Se elimino mas de nu viaje");
        else JOptionPane.showMessageDialog(vista, "Error al eliminar el viaje");

        limpiar();
    }

    private void contarElementos() {
        JOptionPane.showMessageDialog(null, "Numero de viajes en el sistema: " + modelo.contarViajes());
    }
}
