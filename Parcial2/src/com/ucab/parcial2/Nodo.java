package com.ucab.parcial2;

public class Nodo {

    private String string;
    private Nodo izquierdo;
    private Nodo derecho;

    public Nodo(String i) {
		string = i;
	}

    public String getString() {
        return string;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

}
