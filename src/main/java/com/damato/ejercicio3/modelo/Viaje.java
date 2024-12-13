package com.damato.ejercicio3.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Viaje {
    private String origen;
    private String destino;
    private float precio;
}
