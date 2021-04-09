package br.uefs.ecomp.pbl4.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashSet;

import javax.swing.JPanel;

import br.uefs.ecomp.pbl4.controller.Controller;
import br.uefs.ecomp.pbl4.model.Estacao;
import br.uefs.ecomp.pbl4.util.*;

/**
 * Classe que especializa a classe JPanel afim de comportar uma imagem contendo os nomes das estações
 * do mapa e de realizar desenhos destas estações, das linhas que as ligam e eventualmente das rotas
 * entre duas estações.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
@SuppressWarnings("serial")
public class JPanelMapa extends JPanel{
	/**
	 * Referência para o centralizador das operações da aplicação
	 */
	private Controller controller;
	/**
	 * Booleano que controlará quando serão desenhados no painel as rotas de menor caminho
	 */
	private boolean desenharRota;
	/**
	 * Instância de Iterable contendo as Entrada's do caminho mínimo entre duas
	 * estações
	 */
	private Iterable<Entrada> rotaMenorCaminho;
	
	/**
	 * Construtor da classe
	 * @param controller - Uma referência para o centralizador das operações da aplicação
	 */
	public JPanelMapa(Controller controller){
		this.controller = controller;
		desenharRota = false;
	}
	
	@Override
	public void paintComponent(Graphics g){
		Toolkit tkit = Toolkit.getDefaultToolkit( );
		Image img = tkit.getImage(".\\img\\nomesMapa.jpg");
		
		g.drawImage(img, 0, 0, this);
		
		desenharLinhas((Graphics2D) g);
		
		if(desenharRota){
			desenharRota(g);
		}
	}
	
	/**
	 * Desenha as linhas e as estações do mapa.
	 * 
	 * @param g - Uma instância do Graphics deste JPanel
	 */
	private void desenharLinhas(Graphics2D g){
		HashSet<Aresta> arestasVisitadas = new HashSet<>(); // Este HashSet servirá para controlar se as arestas já foram visitadas
		
		for(Vertice v: controller.getEstacoes()){
			Estacao estacao = (Estacao) v;
			
			for(Aresta a: estacao.getArestasIncidentes()){ // Percorre as arestas incidentes a Estacao
				if(!arestasVisitadas.contains(a)){ // E se ela não tiver sido desenhada, o desenho é feito
					arestasVisitadas.add(a);
					MapaDrawer.desenharLinha(g, (int) ( ((Estacao)a.getU()).getX()  ), (int) ( ((Estacao)a.getU()).getY() )  ,
							                    (int) ( ((Estacao)a.getV()).getX()  ), (int) ( ((Estacao)a.getV()).getY() )  );
				
				}
			}
			
			/*A estação é desenhada sobre as arestas*/
			MapaDrawer.desenharEstacao(g, (int) (estacao.getX() ), (int) (estacao.getY() ), estacao.getLinha());			
		}
		
	}
	
	/**
	 * Recebe uma instância de Iterable contendo todas as Entrada's do caminho entre duas estações e 
	 * as representa graficamente as desenhando.
	 * 
	 * @param rotaMenorCaminho - Instância de Iterable contendo as Entrada's do caminho mínimo entre duas
	 * estações
	 */
	public void desenharRota(Iterable<Entrada> rotaMenorCaminho){
		this.rotaMenorCaminho = rotaMenorCaminho;
		desenharRota = true;
		repaint();
	}
	
	/**
	 * Utiliza a instância de Iterable contendo as entradas do menor caminho para desenhar na tela
	 * o caminho entre as duas estações.
	 * 
	 * @param g - Uma instância do Graphics deste JPanel
	 */
	private void desenharRota(Graphics g){
		MapaDrawer.setIsRota(true); // Define que os elementos a serem desenhados serão uma rota
		
		for(Entrada atual :  rotaMenorCaminho){	
			Estacao cur = (Estacao) atual.atual;
			Estacao prev = (Estacao) atual.anterior;
			
			if(cur != null && prev != null) // Se não for a nem primeira nem a ultima entrada, que só tem um vértice
				MapaDrawer.desenharLinha(g, cur.getX(), cur.getY(), prev.getX(), prev.getY()); // Será traçada uma reta entre os dois vértices
			
			MapaDrawer.desenharEstacaoEmRota(g, cur.getX(), cur.getY()); // Após o desenho de cada reta, será desenhado um ponto sobre estas
		}
		
		MapaDrawer.setIsRota(false);
	}
	
	/**
	 * Redesenha as estações e as linhas que as ligam sem a rota de menor caminho.
	 */
	public void limparRota(){
		desenharRota = false;
		repaint();
	}

}
