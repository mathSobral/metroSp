package br.uefs.ecomp.pbl4.view;

import java.io.IOException;
import java.util.Stack;

import javax.swing.border.EmptyBorder;

import br.uefs.ecomp.pbl4.controller.Controller;
import br.uefs.ecomp.pbl4.model.Estacao;
import br.uefs.ecomp.pbl4.util.Entrada;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.EventQueue;

/**
 * Classe que especializa JFrame a fim de comportar o frame principal da aplicação
 *
 * @authors Lucas Rocha, Matheus Sobral
 */
@SuppressWarnings("serial")
public class Principal extends JFrame {
	private JPanel contentPane;
	private Controller controller;
	private JTextField txtEspera;
	private JComboBox<String> cbOrigem;
	private JComboBox<String> cbDestino;
	private JLabel labelPercurso;
	private JPanelMapa panelMapa;
	private JScrollPane scrollPane;
	private JLabel labelParada;
	private JLabel labelTotal;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 650); // Define o tamanho da janela 750 x 650 p 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);  // Define que a janela não pode ser redimensionada
		setTitle(".:: Qual menor caminho - Mêtro São Paulo ::.");
		
		controller = new Controller();
		lerArquivos();

		inicializarScrollPane();
		instanciarComponentes();
	}

	/**
	 * Efetua a leitura dos arquivos contendo as informações das estações
	 */
	private void lerArquivos(){
		try {
			controller.carregarCoordenadasEstacoes(".\\inputs\\coordenadas.csv");
			controller.carregarMapaMetro(".\\inputs\\MapaMetro.txt");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instancia os JLabel's e o TextField
	 */
	private void instanciarComponentes(){
		JLabel lblEstaoDeOrigem = new JLabel("Esta\u00E7\u00E3o de origem: ");
		lblEstaoDeOrigem.setBounds(10, 517, 140, 14);
		contentPane.add(lblEstaoDeOrigem);
		
		JLabel lblEstaoDeDestino = new JLabel("Esta\u00E7\u00E3o de destino: ");
		lblEstaoDeDestino.setBounds(10, 562, 149, 14);
		contentPane.add(lblEstaoDeDestino);
		
		JLabel lblTempoEspera = new JLabel("Tempo de espera em cada esta\u00E7\u00E3o:");
		lblTempoEspera.setBounds(435, 517, 203, 14);
		contentPane.add(lblTempoEspera);
		
		JLabel lblTotalViagem = new JLabel("Tempo total de viagem: ");
		lblTotalViagem.setBounds(487, 47, 161, 14);
		contentPane.add(lblTotalViagem);
		
		JLabel lblTempoDePara = new JLabel("Tempo de parada nas esta\u00E7\u00F5es:");
		lblTempoDePara.setBounds(436, 29, 203, 14);
		contentPane.add(lblTempoDePara);
		
		JLabel lblTempoGastoNo = new JLabel("Tempo gasto no percurso:");
		lblTempoGastoNo.setBounds(469, 11, 161, 14);
		contentPane.add(lblTempoGastoNo);
		
		labelPercurso = new JLabel(" -");
		labelPercurso.setBounds(648, 11, 86, 14);
		contentPane.add(labelPercurso);
		
		labelParada = new JLabel(" -");
		labelParada.setBounds(648, 29, 86, 14);
		contentPane.add(labelParada);
		
		labelTotal = new JLabel(" -");
		labelTotal.setBounds(648, 47, 86, 14);
		contentPane.add(labelTotal);
		
		txtEspera = new JTextField();
		txtEspera.setBounds(648, 514, 86, 20);
		contentPane.add(txtEspera);
		txtEspera.setColumns(10);
		
		inicializarComboboxes();
		intanciarBotaoMenorRota();
	}
	
	/**
	 * Instacia o botão de menor rota
	 */
	private void intanciarBotaoMenorRota(){
		JButton btnMenorRota = new JButton("Tra\u00E7ar menor rota");
		
		btnMenorRota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cliqueBotaoMenorRota();
			}
		});
		
		btnMenorRota.setBounds(585, 558, 149, 23);
		contentPane.add(btnMenorRota);
	}
	
	/**
	 * Enveto de clique no botão de menor rota
	 */
	private void cliqueBotaoMenorRota(){
		try{
			double tempoEspera = Double.parseDouble(txtEspera.getText());
			
			Stack<Entrada> menorCaminho = controller.calcularMenorCaminho((String) cbOrigem.getSelectedItem(), 
					  												      (String) cbDestino.getSelectedItem());
	
			panelMapa.desenharRota(menorCaminho);	
	
			tempoEspera = (menorCaminho.size()  - 2) * tempoEspera;
			
			if(tempoEspera < 0)
				tempoEspera = 0;
			
			double tempoPercurso = menorCaminho.get(0).distance;
			
			labelPercurso.setText(tempoPercurso + " min");
			labelParada.setText(tempoEspera  + " min");
			labelTotal.setText( (tempoPercurso + tempoEspera) + " min");
			
			Estacao primeira = (Estacao) menorCaminho.peek().atual;
			Estacao ultima   = (Estacao) menorCaminho.get(0).atual;
			
			int x = primeira.getX() < ultima.getX() ? primeira.getX() : ultima.getX();
			int y = primeira.getY() < ultima.getY() ? primeira.getY() : ultima.getY(); 
			
			moverScrollPane(x, y);
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "O tempo de espera informado não é um valor numérico válido", "Erro", JOptionPane.ERROR_MESSAGE);
			txtEspera.requestFocus();
		}
	}
	
	/**
	 * Move o scroll pane para as coordenadas argumentadas
	 * @param x - Coordenada x
	 * @param y - Coordenada y
	 */
	private void moverScrollPane(int x, int y){
		JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
		JScrollBar vertical   = scrollPane.getVerticalScrollBar();
		
		horizontal.setValue(x - 30);
		vertical.setValue(y - 30);
	}
	
	/**
	 * Instancia os comboboxes e define os eventos de ActionListener
	 */
	private void inicializarComboboxes(){
		cbOrigem = new JComboBox<>();
		cbOrigem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				limparCampos();
				repopularCbDestino();
			}
			
		});
		
		cbOrigem.setBounds(148, 514, 180, 20);
		contentPane.add(cbOrigem);
		
		
		cbDestino = new JComboBox<>();
		cbDestino.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				limparCampos();
				repopularCbOrigem();
			}
			
		});
		
		cbDestino.setBounds(148, 559, 180, 20);
		contentPane.add(cbDestino);
		
		cbOrigem.setModel(new DefaultComboBoxModel<String>(controller.getNomeEstacoes()));
		cbDestino.setModel(new DefaultComboBoxModel<String>(controller.getNomeEstacoes()));
		
		cbOrigem.removeItemAt(1);
		cbDestino.removeItemAt(0);
	}
	
	private void limparCampos(){
		scrollPane.getHorizontalScrollBar().setValue(362);
		scrollPane.getVerticalScrollBar().setValue(0);
		
		panelMapa.limparRota();
		labelPercurso.setText("  -");
		labelTotal.setText   ("  -");
		labelParada.setText  ("  -");
	}
	
	/**
	 * Repopula o ComboBox de origem com todos os elementos exceto o selecionado no ComboBox de destino
	 */
	private void repopularCbOrigem(){
		Object antigaSelecao = cbOrigem.getSelectedItem();
		cbOrigem.removeAllItems();
		
		for(String s: controller.getNomeEstacoes())
			if(!s.equals(cbDestino.getSelectedItem()))
				cbOrigem.addItem(s);
		
		
		cbOrigem.setSelectedItem(antigaSelecao);
	}
	
	/**
	 * Repopula o ComboBox de destino com todos os elementos exceto o selecionado no ComboBox de origem
	 */
	private void repopularCbDestino(){
		Object antigaSelecao = cbDestino.getSelectedItem();
		cbDestino.removeAllItems();
		
		for(String s: controller.getNomeEstacoes())
			if(!s.equals(cbOrigem.getSelectedItem()))
				cbDestino.addItem(s);
		
		cbDestino.setSelectedItem(antigaSelecao);
	}
	
	/**
	 * Inicializa o ScrollPane bem como adciona um JPanel contendo o mapa das estações nele
	 */
	private void inicializarScrollPane(){
		panelMapa = new JPanelMapa(controller);
		panelMapa.setPreferredSize(new Dimension(1440, 1080));

		scrollPane = new JScrollPane(panelMapa);
		scrollPane.setBounds(10, 72, 724, 414);
		contentPane.add(scrollPane);
	}
}