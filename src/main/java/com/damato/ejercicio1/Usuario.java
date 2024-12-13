package com.damato.ejercicio1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String email;



}
