package com.ucab.Taller8;

public class Nodo {
	private int dato;
	private Nodo hijoD;
	private Nodo hijoI;
	
	
	public Nodo(int num) {
		dato = num;
		hijoD = null;
		hijoI = null;
	}
	
	public int getDato() {
		return dato;
	}
	public void setDato(int dato) {
		this.dato = dato;
	}
	public Nodo getHijoD() {
		return hijoD;
	}
	public void setHijoD(Nodo hijoD) {
		this.hijoD = hijoD;
	}
	public Nodo getHijoI() {
		return hijoI;
	}
	public void setHijoI(Nodo hijoI) {
		this.hijoI = hijoI;
	}
	
	public void insertar(int valorInsertar) {
		if(dato > valorInsertar){
			if (hijoI == null)
				hijoI = new Nodo(valorInsertar);
			else
			  hijoI.insertar(valorInsertar);
		}
		else if (dato < valorInsertar){
				if(hijoD == null)
					hijoD = new Nodo(valorInsertar);
				else
					hijoD.insertar(valorInsertar);
			}
	}
	
}
