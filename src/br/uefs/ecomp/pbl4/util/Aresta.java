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
	 * Acessa o v�rtice u da aresta.
	 * 
	 * @return Uma refer�ncia para o v�rtice u
	 */
	public Vertice getU() {
		return u;
	}

	/**
	 * Acessa o v�rtice v da aresta.
	 *  
	 * @return  Uma refer�ncia para o v�rtice v
	 */
	public Vertice getV() {
		return v;
	}

	/**
	 * Tomando o v�rtice argumentado como origem retorna o outro v�rtice presenta na aresta, que ser�
	 * o destino.
	 * 
	 * @param vertex - Vertice que ser� tomado como origem
	 * @return - Uma refer�ncia para o vertice de "destino"
	 */
	public Vertice getDestino(Vertice vertex){
		return v == vertex ? u:v;
	}

	/**
	 * Acessa o peso da aresta.
	 * 
	 * @return - Um double que ser� o peso da aresta
	 */
	public double getPeso() {
		return peso;
	}
}
