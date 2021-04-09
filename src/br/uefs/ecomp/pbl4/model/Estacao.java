package br.uefs.ecomp.pbl4.model;

import java.util.ArrayList;
import java.util.Iterator;

import br.uefs.ecomp.pbl4.util.Aresta;
import br.uefs.ecomp.pbl4.util.Vertice;

/**
 * Classe que implementa a interface Vertice e servir� para representar uma esta��o do m�tro.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Estacao implements Vertice{
	/**
	 * Nome da esta��o
	 */
	private final String nome;
	/**
	 * Lista de adjac�ncia contendo as arestas
	 */
	private ArrayList<Aresta> arestas;
	/**
	 * Coordenada x da esta��o, dado util para representa��o gr�fica desta esta��o.
	 */
	private int x;
	/**
	 * Coordenada y da esta��o, dado util para representa��o gr�fica desta esta��o.
	 */
	private int y;
	/**
	 * Linha a qual a situa��o pertence, dado util para representa��o gr�fica desta esta��o.
	 */
	private int linha;
	
	/**
	 * Construtor da classe
	 * @param nome - Nome da esta��o
	 */
	public Estacao(String nome){
		this.nome = nome;
		arestas = new ArrayList<Aresta>();
	}
	
	public String getNome(){
		return nome;
	}
	
	public ArrayList<Aresta> getArestasIncidentes(){
		return arestas;
	}
	
	/**
	 * Acessa a coordenada x da esta��o.
	 * 
	 * @return - A coordenada x da esta��o
	 */
	public int getX() {
		return x;
	}

	/**
	 * Seta o valor da coordenada x da esta��o.
	 * 
	 * @param x - Inteiro que representar� a coordenada x da esta��o.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Acessa a coordenada y da esta��o.
	 * 
	 * @return - A coordenada y da esta��o
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Seta o valor da coordenada y da esta��o.
	 * 
	 * @param y - Inteiro que representar� a coordenada y da esta��o.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Acessa a linha em que a esta��o est� situada.
	 * 
	 * @return - A linha da esta��o
	 */
	public int getLinha() {
		return linha;
	}

	/**
	 * Seta o valor da linha da esta��o.
	 * 
	 * @param linha - Inteiro que representar� a linha em que a esta��o est� situada
	 */
	public void setLinha(int linha) {
		this.linha = linha;
	}

	public String toString(){
		return nome;
	}

	@Override
	public Aresta getAresta(Vertice v) {
		for(Aresta e: arestas){ // for - each que percorrer� lista de arestas da esta��o
			Vertice o = e.getDestino(this); // Tomando a esta��o atual como origem, ser� retornado o vertice que n�o seja este presente na aresta
			if(v.equals(o)) // Encontrou o Vertice v
				return e; // Retorna a Aresta que liga os dois vertices
		}
		return null; // N�o encontrou a aresta, logo ela n�o existe
	}
	
	@Override
	public Iterable<Vertice> getVerticesAdjacentes() {
		Vertice estaEstacao = this; // Refer�ncia para esta esta��o
		
		return new Iterable<Vertice>(){ // Classe an�nima da interface Iterable contendo  o m�todo iterator que retornar� uma inst�ncia de IteradorVerticesAdjacentes 
			
			@Override
			public Iterator<Vertice> iterator(){
				return new IteradorVerticesAdjacentes(estaEstacao);
			}
			
		};
	}
	
	/**
	 * Classe privada para iterar sobre as esta��es adjacentes a esta.
	 *
	 * @authors Lucas Rocha, Matheus Sobral
	 */
	private class IteradorVerticesAdjacentes implements Iterator<Vertice>{
		private int i; // Variavel auxiliar para percorrer a lista de arestas do v�rtice
		private Vertice estaEstacao; // Refer�ncia para esta��o que instancia esta classe
		
		/**
		 * Construtor da classe
		 * @param estaEstacao - Uma refer�ncia para a Estacao que instancia esta classe
		 */
		public IteradorVerticesAdjacentes(Vertice estaEstacao){
			i = 0;
			this.estaEstacao = estaEstacao;
		}
		
		@Override
		public boolean hasNext() {
			return i < arestas.size();
		}
		
		@Override
		public Vertice next() {
			Aresta e = arestas.get(i++); // Encontra a pr�xima aresta
			return e.getDestino(estaEstacao); // Retorna o v�rtice de destino, tomando o v�rtice que inst�ncia esta classe como origem
		}
	}

}
