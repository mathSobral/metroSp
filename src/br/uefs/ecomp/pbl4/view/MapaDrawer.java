package br.uefs.ecomp.pbl4.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Classe abstrata e de m�todos est�ticos respons�vel por desenhar elementos em um componente
 * atr�ves da interface Graphics deste.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public abstract class MapaDrawer {
	/**
	 * Vari�vel que controlar� o di�metro do c�rculo que ser� desenhado
	 */
	private static int tamanhoPonto = 10;
	/**
	 * Vari�vel respons�vel por controlar quando os elementos de uma rota, que ter�o dimens�es menores
	 * que os do mapa, ser�o desenhados
	 */
	private static boolean isRota = false;
	
	/**
	 * Array de Color's contendo as cores de cada linha do metr�
	 */
	private static Color[] colors = {
										new Color(0,83,160),	//Linha 1 - Azul
										new Color(0,128,97),	//Linha 2										
										new Color(239,64,53),	//Linha 3
										new Color(255,201,7),	//Linha 4
										new Color(122,78,160),	//Linha 5
										new Color(159,24,102),	//Linha 7
										new Color(158,158,148),	//Linha 8
										new Color(0,168,142),	//Linha 9
										new Color(0,125,143),	//Linha 10
										new Color(240,78,35),	//Linha 11
										new Color(240,78,35)	//Linha 11 (Expresso)
	};
	
	/**
	 * Desenha um c�rculo para representar uma esta��o a partir de uma inst�ncia de Graphics, obtida do componente no qual
	 * ser� desenhado o elemento, das coordenadas deste esta��o e da linha em que ela est� situada
	 * e da linha.<br>
	 * Para este desenho ser� utilizada a interface Graphics do Component JavaSwing no qual ser�
	 * desenhado os elementos.
	 * @param g - Inst�ncia de Graphics, obtida do Component no qual ser� feito os desenhos
	 * @param x - Coordenada x da esta��o
	 * @param y - Coordenada y da esta��o
	 * @param linha
	 */
	public static void desenharEstacao(Graphics g, int x, int y, int linha){
		Graphics2D g2 = (Graphics2D) g; // Fazendo este cast os m�todos para configura��o da renderiza��o dos desenhos ficam vis�veis
		
		/*Melhora a renderiza��o, e portanto a qualidade, dos desenhos*/
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/* Como n�o existe linha 6 foi necess�ria essa verific��o para acessar corretamente os elementos
		 * do array de Color's*/
		linha = linha < 7 ? linha - 1 : linha - 2;
		
		g2.setColor(colors[linha]);
		g2.fillOval(x, y, tamanhoPonto, tamanhoPonto);
	}
	
	/**
	 * Desenha um c�rculo, em um Component JavaSwing, atrav�s de sua interface Graphics.
	 * Este c�ruclo representar� uma esta��o em uma rota no mapa, de di�metro n - 2, onde n �
	 * o di�metro do c�rculo desenhado quando a esta��o n�o pertence a uma rota 
	 * @param g - Inst�ncia de Graphics, obtida do Component no qual ser� feito os desenhos
	 * @param x - Coordenada x da esta��o
	 * @param y - Coordenada y da esta��o
	 */
	public static void desenharEstacaoEmRota(Graphics g, int x, int y){
		Graphics2D g2 = (Graphics2D) g; // Fazendo este cast os m�todos para configura��o da renderiza��o dos desenhos ficam vis�veis
		
		/*Melhora a renderiza��o, e portanto a qualidade, dos desenhos*/
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.white);
		g2.fillOval(x + 1, y + 1, tamanhoPonto - 2, tamanhoPonto - 2);
	}
	
	/**
	 * Desenha uma reta entre dois pontos, em um Component JavaSwing, atrav�s de sua interface Graphics. 
	 * @param g  - Inst�ncia de Graphics, obtida do Component no qual ser� feito os desenhos
	 * @param x1 - Coordenada x da primeira esta��o
	 * @param y1 - Coordenada y da primeira esta��o
	 * @param x2 - Coordenada x da segunda esta��o
	 * @param y2 - Coordenada y da segunda esta��o
	 */
	public static void desenharLinha(Graphics g, int x1, int y1, int x2, int y2){
		Graphics2D g2 = (Graphics2D) g; // Fazendo este cast os m�todos para configura��o da renderiza��o dos desenhos ficam vis�veis
		
		/*Melhora a renderiza��o, e portanto a qualidade, dos desenhos*/
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/* Como o "centro" dos elementos desenhados s�o  a extremidade superior esquerda de um ponto, ser� necess�rio saber qual o centro
		 * real da figura para que a reta fique exatamente do centro do ponto desenhado*/
		
		int fatorIncremento; // Para este prop�sito foi criada esta vari�vel
		
		if(!isRota){
			/* Neste caso o tamanho do ponto desenhado � extamente o valor de tamanhoPonto e portanto o centro da elipse ser� simplesmente o 
			 * valor das coordenadas x e y acrescidas do raio do oval*/
			fatorIncremento= tamanhoPonto / 2;
			g2.setColor(Color.gray);
		}
		
		else{	
			/* Neste caso o tamanho do ponto desenhado � o valor de tamanhoPonto subtraido de duas unidades, logo o centro da elipse ser� o 
			 * valor das coordenadas x e y acrescidas do raio do oval*/
			fatorIncremento = (tamanhoPonto - 2) / 2; 
			g2.setColor(Color.blue);
		}
		
		/* Define o valor da vari�vel fatorIncremento como a expessura da reta*/
		BasicStroke dashed =   new BasicStroke((float) fatorIncremento); 
		g2.setStroke(dashed);
	    
		/*Desenha a linha exatamente no centro do c�rculo desenhado para representar as esta��es*/
		g2.drawLine(x1 + fatorIncremento, y1 + fatorIncremento, x2 + fatorIncremento, y2 + fatorIncremento);
	}

	/**
	 * Define atrav�s do estado do booleano argumentado se os elementos a serem desenhados
	 * pertencem a uma rota
	 * @param isRota - Respons�vel por controlar se os desenhos a serem feitos fazem parte de uma rota
	 */
	public static void setIsRota(boolean isRota) {
		MapaDrawer.isRota = isRota;
	}	
	
}
