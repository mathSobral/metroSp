package br.uefs.ecomp.pbl4.util;

import java.util.*;
/**
 * Esta classe implementa um grafo, que é uma importante ferramenta matemática para modelagem de
 * relações entre entes individuais.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Grafo {
	/**
	 * Tabela hash contendo os vértices do grafo.
	 */
	private HashMap<String, Vertice> vertices;

	/**
	 * Método construtor
	 */
	public Grafo(){
		vertices = new HashMap<>();
	}

	/**
	 * Adciona um vértice ao grafo.
	 * 
	 * @param u - Uma instância da interface Vertice
	 */
	public void addVertice(Vertice u){
		if(!vertices.containsKey(u.getNome()))
			vertices.put(u.getNome(), u);
	}

	/**
	 * Adciona uma aresta ponderada ao grafo.
	 * 
	 * @param src - Nome do vértice de "origem"
	 * @param dest - Nome do vértice de "destino"
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
	 * Calcula o menor caminho do vertice argumentado como parâmetro a todos
	 * os outros vértices do grafo.
	 * 
	 * @param v - O vértice de origem
	 * @return Uma referência para um HashMap com chave Vertice e valor Entrada.<br>
	 * Nessas entradas estarão o Vertice de destino, o Vertice anterior, na rota do caminho mínimo,
	 * e a distância entre o destino e a origem.
	 */
	private HashMap<Vertice,Entrada> menorCaminho(Vertice v){
		HashMap<Vertice, Entrada> menorCaminho = new HashMap<>(); // Tabela que armazenará o menor caminho
		PriorityQueue<Entrada> filaPrioridade = new PriorityQueue<Entrada>(); // Fila de prioridade que armazenará os diversos caminhos

		filaPrioridade.add(new Entrada(v, null, 0)); // É adicionada a primeira entrada com o caminho da origem até a origem na fila

		while (!filaPrioridade.isEmpty()){ // A cada interação a entrada com o menor caminho até a origem ficará no topo da fila de prioridade
			Entrada e = filaPrioridade.poll(); // A próxima entrada é retirada da fila
			v = e.atual;

			if(!menorCaminho.containsKey(v)){ // Se não houver um elemento com chave v na tabela hash
				menorCaminho.put(v, e); // Encontrou-se o menor caminho para este vértice então ele é adcionado na tabela de menores caminhos

				for(Vertice u : v.getVerticesAdjacentes()){ // Serão percorridos os vértices adjacentes

					double dist = v.getAresta(u).getPeso();
					dist = dist + e.distance; // A distancia total é a soma do peso da última aresta com a distância do menor caminho do vértice anterior
					
					/* É procurada uma entrada com o vértice u na tabela de menor caminho, esta verificação garantirá que após encontrado o menor 
					 * caminho até um vértice não serão feitas mais verificações para este vértice*/
					Entrada t = menorCaminho.get(u);

					if(t == null || dist < t.distance) // Se não há nenhuma entrada para o vértice na tabela de menor caminho
						filaPrioridade.add(new Entrada(u, v, dist)); // Uma entrada com a tabela de menor caminho é adcionada na fila de prioridade

				}
			}
		}
		return menorCaminho;
	} 

	/**
	 * Retorna uma pilha com Entrada's contendo todas arestas do menor caminho entre dois pontos
	 * 
	 * @param origem  - Nome do vértice de origem
	 * @param destino - Nome do vértice de destino
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
	 * Retorna uma instância de Iterable para os vértices do grafo.
	 * 
	 * @return Os values da tabela hash de vertices
	 */
	public Iterable<Vertice> getVertices() {
		return vertices.values();
	}

	/**
	 * Retorna uma referência para um vertice após uma busca deste pelo seu nome na tabela
	 * de vértices do grafo.
	 * 
	 * @param vert - Uma String com o nome do vértice procurado
	 * @return Uma refêrencia para o vértice de nome argumentado
	 */
	public Vertice getVertice(String vert){
		return vertices.get(vert);
	}

	/**
	 * Retorna o número de vértices do grafo.
	 * 
	 * @return Um inteiro com o número de vertices do grafo
	 */
	public int size(){
		return vertices.size();
	}
}
