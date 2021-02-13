package com.ucab.Taller8;

public class Arbol {

	private Nodo raiz;
	
	public Arbol (){ //contructor1
		raiz = null;
	}
	
	
	public void insertar(int valorInsertar){
		if(raiz == null)
			 raiz = new Nodo(valorInsertar);
		else
			raiz.insertar(valorInsertar);
	}
	
	
	public void recorridoInorden() {
        ayudanteInorden(raiz);
    }

    private void ayudanteInorden(Nodo raiz) {
        if (raiz == null) {
            return;
        }
        ayudanteInorden(raiz.getHijoI());
        System.out.print(raiz.getDato()+ ",");
        ayudanteInorden(raiz.getHijoD());
    }

    public void recorridoPreorden() {
        ayudantePreorden(raiz);
    }

    private void ayudantePreorden(Nodo raiz) {
        if (raiz == null) 
            return;
        System.out.print(raiz.getDato()+ ",");
        ayudantePreorden(raiz.getHijoI());
        ayudantePreorden(raiz.getHijoD());
    }
	
	public boolean esVacia() {
		boolean vacia;
		if (raiz == null) 
			vacia = true;
		else
			vacia = false;
		
		return vacia;
	}
	
	
	
	public int Altura(Nodo nodo) {
        return LlamadoAltura(nodo);
    }
	
	private int LlamadoAltura(Nodo nodo) {
        int altura = 0;
        
        if ((nodo.getHijoD() != null) || (nodo.getHijoI() != null)){
        	if (nodo.getHijoD() != null) {
        		altura = Math.max(altura, LlamadoAltura(nodo.getHijoD()));
        	}
        	if(nodo.getHijoI() != null) {
        		altura = Math.max(altura, LlamadoAltura(nodo.getHijoI()));
        	}  	
        	
        	altura++;
        }
        
        return altura;
    }
	

	public Nodo NodoAbuscar(int valorABuscar) {
		return BuscarNodo(valorABuscar,raiz);
	}
    
    private Nodo BuscarNodo(int valorABuscar,Nodo nodo) {
    	Nodo aux = null;
    	if (nodo == null) {
    		return null;
    	}
    	else
    	if (nodo.getDato() == valorABuscar) {
    		aux = nodo;
    	}
    	else {
    		aux = BuscarNodo(valorABuscar,nodo.getHijoI());
    		if (aux == null) {
    			aux = BuscarNodo(valorABuscar,nodo.getHijoD());
    		}
    	}
	    return aux;
	}

	public Nodo getRaiz() {
		return raiz;
	}
}
