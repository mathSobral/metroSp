package br.uefs.ecomp.pbl4.util;

import java.util.*;

/**
 * Interface para representar um n� de um grafo.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public interface Vertice{
	/**
	 *  M�todo para acessar o nome de um v�rtice.
	 *  
	 * @return - String com o nome do v�rtice
	 */
	public String getNome();
	
	/**
	 * Retorna as arestas incidentes ao v�rtice.
	 * 
	 * @return - Uma inst�ncia de Collection contendo a lista de arestas incidentes ao v�rtice
	 */
	public Collection<Aresta> getArestasIncidentes();
	
	/**
	 * Retorna os v�rtices adjacentes ao v�rtice.
	 * 
	 * @return - Uma inst�ncia de Iterable contendo os v�rtices adjacentes a este.
	 */
	public Iterable<Vertice> getVerticesAdjacentes();
	
	/**
	 * Retorna a aresta entre este v�rtice e o v�rtice argumentado.
	 * 
	 * @param v - Vertice de destino
	 * 
	 * @return - Uma refer�ncia para Aresta entre os dois v�rtices 
	 */
	public Aresta getAresta(Vertice v);
	
}
