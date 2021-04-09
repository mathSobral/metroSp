package br.uefs.ecomp.pbl4.util;

import java.util.*;
/**
 * Esta classe implementa um grafo, que � uma importante ferramenta matem�tica para modelagem de
 * rela��es entre entes individuais.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Grafo {
	/**
	 * Tabela hash contendo os v�rtices do grafo.
	 */
	private HashMap<String, Vertice> vertices;

	/**
	 * M�todo construtor
	 */
	public Grafo(){
		vertices = new HashMap<>();
	}

	/**
	 * Adciona um v�rtice ao grafo.
	 * 
	 * @param u - Uma inst�ncia da interface Vertice
	 */
	public void addVertice(Vertice u){
		if(!vertices.containsKey(u.getNome()))
			vertices.put(u.getNome(), u);
	}

	/**
	 * Adciona uma aresta ponderada ao grafo.
	 * 
	 * @param src - Nome do v�rtice de "origem"
	 * @param dest - Nome do v�rtice de "destino"
	 * @param pesos - Peso da aresta
	 */
	public void addAresta(String src, String dest, double pesos){
		Vertice u = vertices.get(src);
		Vertice v = vertices.get(dest);

		Aresta novaAresta = new Aresta(u, v, pesos);

		u.getArestasIncidentes().add(novaAresta);
		v.getArestasIncidentes().add(novaAresta);
	}

	/**
	 * Calcula o menor caminho do vertice argumentado como par�metro a todos
	 * os outros v�rtices do grafo.
	 * 
	 * @param v - O v�rtice de origem
	 * @return Uma refer�ncia para um HashMap com chave Vertice e valor Entrada.<br>
	 * Nessas entradas estar�o o Vertice de destino, o Vertice anterior, na rota do caminho m�nimo,
	 * e a dist�ncia entre o destino e a origem.
	 */
	private HashMap<Vertice,Entrada> menorCaminho(Vertice v){
		HashMap<Vertice, Entrada> menorCaminho = new HashMap<>(); // Tabela que armazenar� o menor caminho
		PriorityQueue<Entrada> filaPrioridade = new PriorityQueue<Entrada>(); // Fila de prioridade que armazenar� os diversos caminhos

		filaPrioridade.add(new Entrada(v, null, 0)); // � adicionada a primeira entrada com o caminho da origem at� a origem na fila

		while (!filaPrioridade.isEmpty()){ // A cada intera��o a entrada com o menor caminho at� a origem ficar� no topo da fila de prioridade
			Entrada e = filaPrioridade.poll(); // A pr�xima entrada � retirada da fila
			v = e.atual;

			if(!menorCaminho.containsKey(v)){ // Se n�o houver um elemento com chave v na tabela hash
				menorCaminho.put(v, e); // Encontrou-se o menor caminho para este v�rtice ent�o ele � adcionado na tabela de menores caminhos

				for(Vertice u : v.getVerticesAdjacentes()){ // Ser�o percorridos os v�rtices adjacentes

					double dist = v.getAresta(u).getPeso();
					dist = dist + e.distance; // A distancia total � a soma do peso da �ltima aresta com a dist�ncia do menor caminho do v�rtice anterior
					
					/* � procurada uma entrada com o v�rtice u na tabela de menor caminho, esta verifica��o garantir� que ap�s encontrado o menor 
					 * caminho at� um v�rtice n�o ser�o feitas mais verifica��es para este v�rtice*/
					Entrada t = menorCaminho.get(u);

					if(t == null || dist < t.distance) // Se n�o h� nenhuma entrada para o v�rtice na tabela de menor caminho
						filaPrioridade.add(new Entrada(u, v, dist)); // Uma entrada com a tabela de menor caminho � adcionada na fila de prioridade

				}
			}
		}
		return menorCaminho;
	} 

	/**
	 * Retorna uma pilha com Entrada's contendo todas arestas do menor caminho entre dois pontos
	 * 
	 * @param origem  - Nome do v�rtice de origem
	 * @param destino - Nome do v�rtice de destino
	 * @return - Uma pilha com o menor caminho entre os dois pontos
	 */
	public Stack<Entrada> encontrarMenorCaminho(String origem, String destino) {
		Stack<Entrada> stack = new Stack<Entrada>();

		Vertice ini = vertices.get(origem);
		Vertice fim = vertices.get(destino);

		HashMap<Vertice, Entrada> paths = menorCaminho(ini);

		while(!ini.equals(fim)){
			stack.push(paths.get(fim));
			fim = stack.peek().anterior;
		}

		stack.push(paths.get(fim));
		return stack;
	} 

	/**
	 * Retorna uma inst�ncia de Iterable para os v�rtices do grafo.
	 * 
	 * @return Os values da tabela hash de vertices
	 */
	public Iterable<Vertice> getVertices() {
		return vertices.values();
	}

	/**
	 * Retorna uma refer�ncia para um vertice ap�s uma busca deste pelo seu nome na tabela
	 * de v�rtices do grafo.
	 * 
	 * @param vert - Uma String com o nome do v�rtice procurado
	 * @return Uma ref�rencia para o v�rtice de nome argumentado
	 */
	public Vertice getVertice(String vert){
		return vertices.get(vert);
	}

	/**
	 * Retorna o n�mero de v�rtices do grafo.
	 * 
	 * @return Um inteiro com o n�mero de vertices do grafo
	 */
	public int size(){
		return vertices.size();
	}
}
