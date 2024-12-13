package com.damato.ejercicio3.vista;

import javax.swing.*;

public class JFViaje extends JFrame {
    public JPanel pnPrincipal;
    public JTextField tfOrigen;
    public JTextField tfDestino;
    public JTextField tfPrecio;
    public JButton btnInsertar;
    public JButton btnEliminar;
    public JButton btnContar;
    public JTable table1;


    public JFViaje() {
        setContentPane(pnPrincipal);
        setTitle("Viajes");
        pack();
        // setsize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
