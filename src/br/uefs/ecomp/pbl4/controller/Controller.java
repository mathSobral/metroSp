package br.uefs.ecomp.pbl4.controller;

import java.io.*;
import java.text.Collator;
import java.util.Arrays;
import java.util.Stack;

import br.uefs.ecomp.pbl4.model.Estacao;
import br.uefs.ecomp.pbl4.util.*;

/**
 * Classe respons�vel por fazer a liga��o entre as requisi��es do usu�rio, obtidas a partir da camada view, e as opera��es 
 * l�gicas definidas na camada model.
 * <br>
 * Por sempre estar sendo utilizada na realiza��o de todas as opera��es do sistema, a estrutura de dados respons�vel por
 * armazenar os dados durante a execu��o do sistema ser�o instanciadas nesta entidade.
 *
 * @authors Lucas Rocha, Matheus Sobral
 */
public class Controller {
	/**
	 * Grafo que relacionar� as esta��es obtidas a partir da leitura dos arquivos durante a execu��o do programa
	 */
	private Grafo mapaMetro;
	/**
	 * Array de Strings que armazenar� o nome das esta��es no grafo, j� ordenadas alfabeticamente
	 */
	private String[] nomesEstacoes;
	
	/**
	 * Construtor da classe
	 */
	public Controller(){
		mapaMetro = new Grafo();
	}
	
	/**
	 * M�todo respons�vel por efetuar a leitura de um arquivo no formato csv, contendo o nome das
	 * esta��es e as coordenadas destas para uma imagem de dimens�es 1440 x 1080, e armazenar estes
	 * dados em um v�rtice do grafo.
	 * <br>
	 * As linhas iniciadas com o caractere '#' ser�o desconsideradas
	 * @param path - Caminho do arquivo
	 * @throws IOException - Quando o arquivo n�o for encontrado
	 */
	public void carregarCoordenadasEstacoes(String path) throws IOException{
		FileReader arquivo = new FileReader(path);
		BufferedReader leitorArquivo = new BufferedReader(arquivo);
		
		String linha = null;

		while((linha = leitorArquivo.readLine()) != null){//La�o que percorre todas as linhas do arquivo armazenando o conte�do de cada uma destas na vari�vel linha
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
	 * M�todo respons�vel por efetuar a leitura de um arquivo separado por virgulas contendo os nomes de
	 * duas esta��es e o tempo do percurso entre elas.
	 * Estes dados ser�o modelados como arestas do grafo mapaMetro.
	 * <br>
	 * As linhas iniciadas com o caractere '#' ser�o desconsideradas
	 * @param path - Caminho do arquivo
	 * @throws IOException - Quando o arquivo n�o for encontrado
	 */
	public void carregarMapaMetro(String path) throws IOException{
		FileReader arquivo = new FileReader(path);
		BufferedReader leitorArquivo = new BufferedReader(arquivo);
		
		String linha = null;

		while((linha = leitorArquivo.readLine()) != null){//La�o que percorre todas as linhas do arquivo armazenando o conte�do de cada uma destas na vari�vel linha
			if(linha.charAt(0) != '#'){
				String[] atributosCsv = linha.split(",");
				mapaMetro.addAresta(atributosCsv[0], atributosCsv[1], Double.parseDouble(atributosCsv[2]));
			}
		}
		
		leitorArquivo.close();
		arquivo.close();
	}
	
	/**
	 * Retorna um array de String contendo o nome das esta��es ordenados alfabeticamente
	 * @return - Um array de String
	 */
	public String[] getNomeEstacoes(){
		return nomesEstacoes;
	}
	
	/**
	 * Calcula a menor dist�ncia entre duas esta��es. Este c�lculo ser� feito a partir do tempo necess�rio
	 * para realizar o percurso entre cada esta��o do m�tro.
	 * @param origem - String contendo o nome da esta��o de origem
	 * @param destino - String contendo o nome da esta��o de destino
	 * @return - Uma Stack (pilha) com Entrada's contendo o caminho entre os dois pontos
	 */
	public Stack<Entrada> calcularMenorCaminho(String origem, String destino){
		return mapaMetro.encontrarMenorCaminho(origem, destino);
	}
	
	/**
	 * M�todo para acessar as esta��es do m�tro.
	 * @return - Uma inst�ncia de Iterable contendo as esta��es do m�tro.
	 */
	public Iterable<Vertice> getEstacoes(){
		return mapaMetro.getVertices();
	}
	
}
