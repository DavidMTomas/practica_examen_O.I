package com.damato.ejercicio2;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarea {
    private int id;
    private Tipo tipo;
    private String descripcion;
    private boolean estado;

}
