package br.uefs.ecomp.pbl4.controller;

import java.io.*;
import java.text.Collator;
import java.util.Arrays;
import java.util.Stack;

import br.uefs.ecomp.pbl4.model.Estacao;
import br.uefs.ecomp.pbl4.util.*;

/**
 * Classe responsável por fazer a ligação entre as requisições do usuário, obtidas a partir da camada view, e as operações 
 * lógicas definidas na camada model.
 * <br>
 * Por sempre estar sendo utilizada na realização de todas as operações do sistema, a estrutura de dados responsável por
 * armazenar os dados durante a execução do sistema serão instanciadas nesta entidade.
 *
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Controller {
	/**
	 * Grafo que relacionará as estações obtidas a partir da leitura dos arquivos durante a execução do programa
	 */
	private Grafo mapaMetro;
	/**
	 * Array de Strings que armazenará o nome das estações no grafo, já ordenadas alfabeticamente
	 */
	private String[] nomesEstacoes;
	
	/**
	 * Construtor da classe
	 */
	public Controller(){
		mapaMetro = new Grafo();
	}
	
	/**
	 * Método responsável por efetuar a leitura de um arquivo no formato csv, contendo o nome das
	 * estações e as coordenadas destas para uma imagem de dimensões 1440 x 1080, e armazenar estes
	 * dados em um vértice do grafo.
	 * <br>
	 * As linhas iniciadas com o caractere '#' serão desconsideradas
	 * @param path - Caminho do arquivo
	 * @throws IOException - Quando o arquivo não for encontrado
	 */
	public void carregarCoordenadasEstacoes(String path) throws IOException{
		FileReader arquivo = new FileReader(path);
		BufferedReader leitorArquivo = new BufferedReader(arquivo);
		
		String linha = null;

		while((linha = leitorArquivo.readLine()) != null){//Laço que percorre todas as linhas do arquivo armazenando o conteúdo de cada uma destas na variável linha
			if(linha.charAt(0) != '#'){
				String[] atributosCsv = linha.split(";");
			
				Estacao nova = new Estacao(atributosCsv[0]);
				
				nova.setX((int) Double.parseDouble(atributosCsv[1]));
				nova.setY((int) Double.parseDouble(atributosCsv[2]));
				nova.setLinha(Integer.parseInt(atributosCsv[3]));
			
				mapaMetro.addVertice(nova);
				
			}
		}
		
		leitorArquivo.close();
		arquivo.close();
		
		nomesEstacoes = new String[mapaMetro.size()];
		int i = 0;
		
		for(Vertice e: mapaMetro.getVertices())
			nomesEstacoes[i++] = e.getNome();
		
		Arrays.sort(nomesEstacoes, Collator.getInstance());
	}
	
	/**
	 * Método responsável por efetuar a leitura de um arquivo separado por virgulas contendo os nomes de
	 * duas estações e o tempo do percurso entre elas.
	 * Estes dados serão modelados como arestas do grafo mapaMetro.
	 * <br>
	 * As linhas iniciadas com o caractere '#' serão desconsideradas
	 * @param path - Caminho do arquivo
	 * @throws IOException - Quando o arquivo não for encontrado
	 */
	public void carregarMapaMetro(String path) throws IOException{
		FileReader arquivo = new FileReader(path);
		BufferedReader leitorArquivo = new BufferedReader(arquivo);
		
		String linha = null;

		while((linha = leitorArquivo.readLine()) != null){//Laço que percorre todas as linhas do arquivo armazenando o conteúdo de cada uma destas na variável linha
			if(linha.charAt(0) != '#'){
				String[] atributosCsv = linha.split(",");
				mapaMetro.addAresta(atributosCsv[0], atributosCsv[1], Double.parseDouble(atributosCsv[2]));
			}
		}
		
		leitorArquivo.close();
		arquivo.close();
	}
	
	/**
	 * Retorna um array de String contendo o nome das estações ordenados alfabeticamente
	 * @return - Um array de String
	 */
	public String[] getNomeEstacoes(){
		return nomesEstacoes;
	}
	
	/**
	 * Calcula a menor distância entre duas estações. Este cálculo será feito a partir do tempo necessário
	 * para realizar o percurso entre cada estação do mêtro.
	 * @param origem - String contendo o nome da estação de origem
	 * @param destino - String contendo o nome da estação de destino
	 * @return - Uma Stack (pilha) com Entrada's contendo o caminho entre os dois pontos
	 */
	public Stack<Entrada> calcularMenorCaminho(String origem, String destino){
		return mapaMetro.encontrarMenorCaminho(origem, destino);
	}
	
	/**
	 * Método para acessar as estações do mêtro.
	 * @return - Uma instância de Iterable contendo as estações do mêtro.
	 */
	public Iterable<Vertice> getEstacoes(){
		return mapaMetro.getVertices();
	}
	
}
