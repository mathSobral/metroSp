package br.uefs.ecomp.pbl4.util;

/**
 * Classe para representar uma aresta.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Aresta {
	public final Vertice u;
	public final Vertice v;
	public final double peso;

	/**
	 * Construtor da classe.
	 * @param u - Vertice u
	 * @param v - Vertice v
	 * @param peso - O peso desta aresta
	 */
	public Aresta(Vertice u, Vertice v, double peso){
		this.u = u;
		this.v = v;
		this.peso = peso;
	}

	/**
	 * Acessa o vértice u da aresta.
	 * 
	 * @return Uma referência para o vértice u
	 */
	public Vertice getU() {
		return u;
	}

	/**
	 * Acessa o vértice v da aresta.
	 *  
	 * @return  Uma referência para o vértice v
	 */
	public Vertice getV() {
		return v;
	}

	/**
	 * Tomando o vértice argumentado como origem retorna o outro vértice presenta na aresta, que será
	 * o destino.
	 * 
	 * @param vertex - Vertice que será tomado como origem
	 * @return - Uma referência para o vertice de "destino"
	 */
	public Vertice getDestino(Vertice vertex){
		return v == vertex ? u:v;
	}

	/**
	 * Acessa o peso da aresta.
	 * 
	 * @return - Um double que será o peso da aresta
	 */
	public double getPeso() {
		return peso;
	}
}
