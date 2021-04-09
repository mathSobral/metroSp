package br.uefs.ecomp.pbl4.util;

/**
 * Classe auxiliar no processo de encontrar o menor caminho.<br>
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Entrada implements Comparable<Entrada> {
	public double distance;
	public Vertice atual;
	public Vertice anterior;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param u - Vertice atual
	 * @param v - Vertice anterior
	 * @param d - distância até o Vertice de origem
	 */
	public Entrada(Vertice u, Vertice v, double d){
		this.atual = u;
		this.anterior = v;
		this.distance = d;
	}
	
	public int compareTo(Entrada o){
		Entrada e = (Entrada) o;
		if (distance < e.distance)
			return -1;
		else if (distance > e.distance)
			return 1;
		else
			return 0;
	}
	
}