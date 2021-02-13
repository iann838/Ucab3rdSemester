package com.ucab.parcial2;

public class Pregunta1 {
    // Pregunta 1
    // Suponga que tiene almacenado una secuencia de caracteres en un dipolo,
    // y se desea saber si dicha secuencia es palíndromo. Realice una función
    // o programa principal que teniendo un objeto del tipo DIPOLO que almacena
    // una palabra, usando solo las operaciones de la clase DIPOLO devuelva
    // Verdad si es palíndromo falso caso contrario.

    public boolean palindromo(Dipolo d) {
        int parada = d.tamano() / 2;
        for (int i = 0; i < parada; ++i) {
            Nodo primero = d.primero();
            Nodo ultimo = d.ultimo();
            if (!(primero.getString().equals(ultimo.getString()))) {
                return false;
            }
            d.preEliminar();
            d.postEliminar();
        }
        return true;
    }
}
