package br.uefs.ecomp.pbl4.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Classe abstrata e de métodos estáticos responsável por desenhar elementos em um componente
 * atráves da interface Graphics deste.
 * 
 * @authors Lucas Rocha, Matheus Sobral
 */
public abstract class MapaDrawer {
	/**
	 * Variável que controlará o diâmetro do círculo que será desenhado
	 */
	private static int tamanhoPonto = 10;
	/**
	 * Variável responsável por controlar quando os elementos de uma rota, que terão dimensões menores
	 * que os do mapa, serão desenhados
	 */
	private static boolean isRota = false;
	
	/**
	 * Array de Color's contendo as cores de cada linha do metrô
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
	 * Desenha um círculo para representar uma estação a partir de uma instância de Graphics, obtida do componente no qual
	 * será desenhado o elemento, das coordenadas deste estação e da linha em que ela está situada
	 * e da linha.<br>
	 * Para este desenho será utilizada a interface Graphics do Component JavaSwing no qual será
	 * desenhado os elementos.
	 * @param g - Instância de Graphics, obtida do Component no qual será feito os desenhos
	 * @param x - Coordenada x da estação
	 * @param y - Coordenada y da estação
	 * @param linha
	 */
	public static void desenharEstacao(Graphics g, int x, int y, int linha){
		Graphics2D g2 = (Graphics2D) g; // Fazendo este cast os métodos para configuração da renderização dos desenhos ficam visíveis
		
		/*Melhora a renderização, e portanto a qualidade, dos desenhos*/
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/* Como não existe linha 6 foi necessária essa verificção para acessar corretamente os elementos
		 * do array de Color's*/
		linha = linha < 7 ? linha - 1 : linha - 2;
		
		g2.setColor(colors[linha]);
		g2.fillOval(x, y, tamanhoPonto, tamanhoPonto);
	}
	
	/**
	 * Desenha um círculo, em um Component JavaSwing, através de sua interface Graphics.
	 * Este círuclo representará uma estação em uma rota no mapa, de diâmetro n - 2, onde n é
	 * o diâmetro do círculo desenhado quando a estação não pertence a uma rota 
	 * @param g - Instância de Graphics, obtida do Component no qual será feito os desenhos
	 * @param x - Coordenada x da estação
	 * @param y - Coordenada y da estação
	 */
	public static void desenharEstacaoEmRota(Graphics g, int x, int y){
		Graphics2D g2 = (Graphics2D) g; // Fazendo este cast os métodos para configuração da renderização dos desenhos ficam visíveis
		
		/*Melhora a renderização, e portanto a qualidade, dos desenhos*/
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.white);
		g2.fillOval(x + 1, y + 1, tamanhoPonto - 2, tamanhoPonto - 2);
	}
	
	/**
	 * Desenha uma reta entre dois pontos, em um Component JavaSwing, através de sua interface Graphics. 
	 * @param g  - Instância de Graphics, obtida do Component no qual será feito os desenhos
	 * @param x1 - Coordenada x da primeira estação
	 * @param y1 - Coordenada y da primeira estação
	 * @param x2 - Coordenada x da segunda estação
	 * @param y2 - Coordenada y da segunda estação
	 */
	public static void desenharLinha(Graphics g, int x1, int y1, int x2, int y2){
		Graphics2D g2 = (Graphics2D) g; // Fazendo este cast os métodos para configuração da renderização dos desenhos ficam visíveis
		
		/*Melhora a renderização, e portanto a qualidade, dos desenhos*/
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/* Como o "centro" dos elementos desenhados são  a extremidade superior esquerda de um ponto, será necessário saber qual o centro
		 * real da figura para que a reta fique exatamente do centro do ponto desenhado*/
		
		int fatorIncremento; // Para este propósito foi criada esta variável
		
		if(!isRota){
			/* Neste caso o tamanho do ponto desenhado é extamente o valor de tamanhoPonto e portanto o centro da elipse será simplesmente o 
			 * valor das coordenadas x e y acrescidas do raio do oval*/
			fatorIncremento= tamanhoPonto / 2;
			g2.setColor(Color.gray);
		}
		
		else{	
			/* Neste caso o tamanho do ponto desenhado é o valor de tamanhoPonto subtraido de duas unidades, logo o centro da elipse será o 
			 * valor das coordenadas x e y acrescidas do raio do oval*/
			fatorIncremento = (tamanhoPonto - 2) / 2; 
			g2.setColor(Color.blue);
		}
		
		/* Define o valor da variável fatorIncremento como a expessura da reta*/
		BasicStroke dashed =   new BasicStroke((float) fatorIncremento); 
		g2.setStroke(dashed);
	    
		/*Desenha a linha exatamente no centro do círculo desenhado para representar as estações*/
		g2.drawLine(x1 + fatorIncremento, y1 + fatorIncremento, x2 + fatorIncremento, y2 + fatorIncremento);
	}

	/**
	 * Define através do estado do booleano argumentado se os elementos a serem desenhados
	 * pertencem a uma rota
	 * @param isRota - Responsável por controlar se os desenhos a serem feitos fazem parte de uma rota
	 */
	public static void setIsRota(boolean isRota) {
		MapaDrawer.isRota = isRota;
	}	
	
}
