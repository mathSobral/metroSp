package br.uefs.ecomp.pbl4.util;

import java.util.*;

/**
 * Interface para representar um nó de um grafo.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public interface Vertice{
	/**
	 *  Método para acessar o nome de um vértice.
	 *  
	 * @return - String com o nome do vértice
	 */
	public String getNome();
	
	/**
	 * Retorna as arestas incidentes ao vértice.
	 * 
	 * @return - Uma instância de Collection contendo a lista de arestas incidentes ao vértice
	 */
	public Collection<Aresta> getArestasIncidentes();
	
	/**
	 * Retorna os vértices adjacentes ao vértice.
	 * 
	 * @return - Uma instância de Iterable contendo os vértices adjacentes a este.
	 */
	public Iterable<Vertice> getVerticesAdjacentes();
	
	/**
	 * Retorna a aresta entre este vértice e o vértice argumentado.
	 * 
	 * @param v - Vertice de destino
	 * 
	 * @return - Uma referência para Aresta entre os dois vértices 
	 */
	public Aresta getAresta(Vertice v);
	
}
