package com.ucab.parcial2;

public class Pregunta3 {

    public int contarNodos(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }

        int conteo = 1;
        conteo += (contarNodos(raiz.getIzquierdo()) + contarNodos(raiz.getDerecho()));  
        return conteo;
    }

    public int contarNodosArbol(Arbol arbol) {
        return contarNodos(arbol.getRaiz());
    }

    public int contarHojas(Nodo raiz) {
        if (raiz == null) {
            return 1;
        }

        int conteo = 0;
        conteo += ((contarHojas(raiz.getIzquierdo()) + contarHojas(raiz.getDerecho())) / 2);  
        return conteo;
    }

    public int contarHojasArbol(Arbol arbol) {
        return contarNodos(arbol.getRaiz());
    }

}
