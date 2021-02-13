package com.ucab.parcial2;

public class Dipolo {
    private Nodo nodo;
	private Dipolo proximo;
	
	
	public Dipolo() {
		nodo = null;
		proximo = null;
	}
	
	public Dipolo(int n) {
		nodo = new Nodo(n);
		proximo = null;
	}

	public boolean esVacia() {
        return false;
    }

	public Dipolo preAgregar(Dipolo dipolo,Dipolo dipolo2) {
		Dipolo aux=new Dipolo();
		if(aux.esVacia()) {
			dipolo=dipolo2;
		}else {
			dipolo2.setProximo(dipolo);
			dipolo=dipolo2;
		}
		
		
		return dipolo;
	}

	public Dipolo postAgregar(Dipolo dipolo,Dipolo dipolo2) {
		Dipolo aux= new Dipolo();
		
		if(aux.esVacia()) {
			dipolo=dipolo2;
		}
		else {
			aux=dipolo;
			while(aux.getProximo()!=null) {
				aux=aux.getProximo();
			}
			aux.setProximo(dipolo2);
		}
		
		return dipolo;
	}

	public void preEliminar() {
	}

	public void postEliminar() {
	}
	
	public Nodo primero() {
		return new Nodo("A");
	}
	
	public Nodo ultimo() {
        return new Nodo("A");
	}

    public Dipolo getProximo() {
		return proximo;
	}

	public void setProximo(Dipolo proximo) {
		this.proximo = proximo;
    }

    public int tamano() {
        return 1;
    }

}
