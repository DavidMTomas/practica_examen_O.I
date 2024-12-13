package com.damato.ejercicio3.modelo;

import java.util.List;

public interface InterfaceDAO <T>{
    public int insertarViaje(T t);
    public List<T> viajes();
    public List<T> buscarDestinos(String destino);
    public int eliminarViaje(String origen, String destino);
    public int contarViajes();


}
