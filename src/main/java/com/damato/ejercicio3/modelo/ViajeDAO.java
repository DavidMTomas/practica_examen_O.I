package com.damato.ejercicio3.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViajeDAO implements InterfaceDAO<Viaje> {

    private Connection conexion;

    public ViajeDAO() {
        conexion = GestionBD.getConexion();
    }


    @Override
    public int insertarViaje(Viaje viaje) {
        int resultado = 0;
        String sql = "insert into viajes values(?,?,?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, viaje.getOrigen());
            ps.setString(2, viaje.getDestino());
            ps.setFloat(3, viaje.getPrecio());

            resultado = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    @Override
    public List<Viaje> viajes() {
        List<Viaje> lista = new ArrayList<Viaje>();

        String sql = "select * from viajes";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setOrigen(rs.getString("origen"));
                viaje.setDestino(rs.getString("destino"));
                viaje.setPrecio(rs.getFloat("precio"));
                lista.add(viaje);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public List<Viaje> buscarDestinos(String destino) {
        List<Viaje> lista = new ArrayList<>();
        String sql = "select * from viajes where destino=?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, destino);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setOrigen(rs.getString("origen"));
                viaje.setDestino(rs.getString("destino"));
                viaje.setPrecio(rs.getFloat("precio"));
                lista.add(viaje);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public int eliminarViaje(String origen, String destino) {
        int resultado = 0;
        String sql = "delete from viajes where origen=? and destino=?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, origen);
            ps.setString(2, destino);

            resultado = ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }


    @Override
    public int contarViajes() {
        int resultado = 0;
        String sql = "Select count(*) from viajes";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultado = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
