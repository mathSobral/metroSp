package br.uefs.ecomp.pbl4.model;

import java.util.ArrayList;
import java.util.Iterator;

import br.uefs.ecomp.pbl4.util.Aresta;
import br.uefs.ecomp.pbl4.util.Vertice;

/**
 * Classe que implementa a interface Vertice e servirá para representar uma estação do mêtro.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Estacao implements Vertice{
	/**
	 * Nome da estação
	 */
	private final String nome;
	/**
	 * Lista de adjacência contendo as arestas
	 */
	private ArrayList<Aresta> arestas;
	/**
	 * Coordenada x da estação, dado util para representação gráfica desta estação.
	 */
	private int x;
	/**
	 * Coordenada y da estação, dado util para representação gráfica desta estação.
	 */
	private int y;
	/**
	 * Linha a qual a situação pertence, dado util para representação gráfica desta estação.
	 */
	private int linha;
	
	/**
	 * Construtor da classe
	 * @param nome - Nome da estação
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
	 * Acessa a coordenada x da estação.
	 * 
	 * @return - A coordenada x da estação
	 */
	public int getX() {
		return x;
	}

	/**
	 * Seta o valor da coordenada x da estação.
	 * 
	 * @param x - Inteiro que representará a coordenada x da estação.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Acessa a coordenada y da estação.
	 * 
	 * @return - A coordenada y da estação
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Seta o valor da coordenada y da estação.
	 * 
	 * @param y - Inteiro que representará a coordenada y da estação.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Acessa a linha em que a estação está situada.
	 * 
	 * @return - A linha da estação
	 */
	public int getLinha() {
		return linha;
	}

	/**
	 * Seta o valor da linha da estação.
	 * 
	 * @param linha - Inteiro que representará a linha em que a estação está situada
	 */
	public void setLinha(int linha) {
		this.linha = linha;
	}

	public String toString(){
		return nome;
	}

	@Override
	public Aresta getAresta(Vertice v) {
		for(Aresta e: arestas){ // for - each que percorrerá lista de arestas da estação
			Vertice o = e.getDestino(this); // Tomando a estação atual como origem, será retornado o vertice que não seja este presente na aresta
			if(v.equals(o)) // Encontrou o Vertice v
				return e; // Retorna a Aresta que liga os dois vertices
		}
		return null; // Não encontrou a aresta, logo ela não existe
	}
	
	@Override
	public Iterable<Vertice> getVerticesAdjacentes() {
		Vertice estaEstacao = this; // Referência para esta estação
		
		return new Iterable<Vertice>(){ // Classe anônima da interface Iterable contendo  o método iterator que retornará uma instância de IteradorVerticesAdjacentes 
			
			@Override
			public Iterator<Vertice> iterator(){
				return new IteradorVerticesAdjacentes(estaEstacao);
			}
			
		};
	}
	
	/**
	 * Classe privada para iterar sobre as estações adjacentes a esta.
	 *
	 * @authors Lucas Rocha, Matheus Sobral
	 */
	private class IteradorVerticesAdjacentes implements Iterator<Vertice>{
		private int i; // Variavel auxiliar para percorrer a lista de arestas do vértice
		private Vertice estaEstacao; // Referência para estação que instancia esta classe
		
		/**
		 * Construtor da classe
		 * @param estaEstacao - Uma referência para a Estacao que instancia esta classe
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
			Aresta e = arestas.get(i++); // Encontra a próxima aresta
			return e.getDestino(estaEstacao); // Retorna o vértice de destino, tomando o vértice que instância esta classe como origem
		}
	}

}
