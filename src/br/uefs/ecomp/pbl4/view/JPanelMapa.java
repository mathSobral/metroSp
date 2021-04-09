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
 * Classe que especializa a classe JPanel afim de comportar uma imagem contendo os nomes das esta��es
 * do mapa e de realizar desenhos destas esta��es, das linhas que as ligam e eventualmente das rotas
 * entre duas esta��es.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
@SuppressWarnings("serial")
public class JPanelMapa extends JPanel{
	/**
	 * Refer�ncia para o centralizador das opera��es da aplica��o
	 */
	private Controller controller;
	/**
	 * Booleano que controlar� quando ser�o desenhados no painel as rotas de menor caminho
	 */
	private boolean desenharRota;
	/**
	 * Inst�ncia de Iterable contendo as Entrada's do caminho m�nimo entre duas
	 * esta��es
	 */
	private Iterable<Entrada> rotaMenorCaminho;
	
	/**
	 * Construtor da classe
	 * @param controller - Uma refer�ncia para o centralizador das opera��es da aplica��o
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
	 * Desenha as linhas e as esta��es do mapa.
	 * 
	 * @param g - Uma inst�ncia do Graphics deste JPanel
	 */
	private void desenharLinhas(Graphics2D g){
		HashSet<Aresta> arestasVisitadas = new HashSet<>(); // Este HashSet servir� para controlar se as arestas j� foram visitadas
		
		for(Vertice v: controller.getEstacoes()){
			Estacao estacao = (Estacao) v;
			
			for(Aresta a: estacao.getArestasIncidentes()){ // Percorre as arestas incidentes a Estacao
				if(!arestasVisitadas.contains(a)){ // E se ela n�o tiver sido desenhada, o desenho � feito
					arestasVisitadas.add(a);
					MapaDrawer.desenharLinha(g, (int) ( ((Estacao)a.getU()).getX()  ), (int) ( ((Estacao)a.getU()).getY() )  ,
							                    (int) ( ((Estacao)a.getV()).getX()  ), (int) ( ((Estacao)a.getV()).getY() )  );
				
				}
			}
			
			/*A esta��o � desenhada sobre as arestas*/
			MapaDrawer.desenharEstacao(g, (int) (estacao.getX() ), (int) (estacao.getY() ), estacao.getLinha());			
		}
		
	}
	
	/**
	 * Recebe uma inst�ncia de Iterable contendo todas as Entrada's do caminho entre duas esta��es e 
	 * as representa graficamente as desenhando.
	 * 
	 * @param rotaMenorCaminho - Inst�ncia de Iterable contendo as Entrada's do caminho m�nimo entre duas
	 * esta��es
	 */
	public void desenharRota(Iterable<Entrada> rotaMenorCaminho){
		this.rotaMenorCaminho = rotaMenorCaminho;
		desenharRota = true;
		repaint();
	}
	
	/**
	 * Utiliza a inst�ncia de Iterable contendo as entradas do menor caminho para desenhar na tela
	 * o caminho entre as duas esta��es.
	 * 
	 * @param g - Uma inst�ncia do Graphics deste JPanel
	 */
	private void desenharRota(Graphics g){
		MapaDrawer.setIsRota(true); // Define que os elementos a serem desenhados ser�o uma rota
		
		for(Entrada atual :  rotaMenorCaminho){	
			Estacao cur = (Estacao) atual.atual;
			Estacao prev = (Estacao) atual.anterior;
			
			if(cur != null && prev != null) // Se n�o for a nem primeira nem a ultima entrada, que s� tem um v�rtice
				MapaDrawer.desenharLinha(g, cur.getX(), cur.getY(), prev.getX(), prev.getY()); // Ser� tra�ada uma reta entre os dois v�rtices
			
			MapaDrawer.desenharEstacaoEmRota(g, cur.getX(), cur.getY()); // Ap�s o desenho de cada reta, ser� desenhado um ponto sobre estas
		}
		
		MapaDrawer.setIsRota(false);
	}
	
	/**
	 * Redesenha as esta��es e as linhas que as ligam sem a rota de menor caminho.
	 */
	public void limparRota(){
		desenharRota = false;
		repaint();
	}

}
