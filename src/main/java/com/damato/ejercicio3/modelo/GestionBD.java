package com.damato.ejercicio3.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestionBD {

    // Variables de conexion
    private static final String BD = "adt4_practica4";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost/" + BD;

    private static GestionBD db = null;
    private Connection conexion = null;

    // Constructor de clase que se conecta
    // a la base de datos MySQL
    private GestionBD() {

        //Obtener conexión para la Base de Datos
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            System.out.println("Conectado a la base de datos MySQL [" + BD + "]");
        } catch (Exception e) {
            System.err.println("Error conexión [" + BD + "]: " + e.getMessage());
        }

    }

    public static Connection getConexion() {

        if (db == null) {
            db = new GestionBD();
        }
        return db.conexion;

    }

    public static void desconectar() {
        try {
            db.conexion.close();
            System.out.println("Desconectado de la base de datos MySQL [" + BD + "]");
        } catch (SQLException ex) {
            System.err.println("Error desconexión [" + BD + "]: " + ex.getMessage());
        }
    }

}
