package jogo.cliente;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jogo.Lado;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela {

	/**
	 * Launch the application.
	 */
	
	private JFrame tela = new JFrame("Ímpar ou Par");
	private JLabel lbSuaEscolha = new JLabel("Sua Escolha");
	private JLabel lbEscolhaAdversario = new JLabel("Escolha do adversário");
	private JTextField tfSuaEscolha = new JTextField();
	private JTextField tfEscolhaAdversario = new JTextField();
	private JLabel lbSeuLado = new JLabel("Seu lado");
	private JLabel lbLadoAdversario = new JLabel("Lado do adversário");
	private JTextField tfLadoAdversario = new JTextField();
	private JTextField tfEstado = new JTextField();
	private JButton btJogar = new JButton("Jogar");
	private JComboBox cbEscolherLado = new JComboBox();
	private boolean jogadaRealizada = false;

	/**
	 * Create the application.
	 */
	public Tela() {
		initialize();
	}
	
	public void setVisible(boolean b) {
		this.tela.setVisible(b);
	}
	
	public void fechar() {
		this.tela.dispose();
	}
	
	public void setTextoEstado(String texto) {
		this.tfEstado.setText(texto);
	}
	
	public Lado getLadoEscolhido() {
		int valor = this.cbEscolherLado.getSelectedIndex();
		if (valor < 0 || valor > 1) {
			return null;
		}
		return Lado.values()[valor];
	}
	
	public void limparSelecaoLado() {
		this.cbEscolherLado.setSelectedIndex(2);
	}
	
	public void configurarCamposInicioJogo() {
		this.cbEscolherLado.setSelectedIndex(2);
		this.cbEscolherLado.setEnabled(true);
		
		this.tfEscolhaAdversario.setText("");
		
		this.jogadaRealizada = false;
		this.tfSuaEscolha.setText("");
		this.tfSuaEscolha.setEditable(false);
		
		this.tfEscolhaAdversario.setText("");
		this.tfEstado.setText("");
	}
	
	public void liberaEscolhaLado(boolean l) {
		this.cbEscolherLado.setEnabled(l);
	}
	
	public void habilitaEscolha(boolean j) {
		this.tfSuaEscolha.setEnabled(j);
		this.tfSuaEscolha.setEditable(j);
	}
	
	public void limpaEscolha() {
		this.jogadaRealizada = false;
		this.tfSuaEscolha.setText("");
		this.habilitaEscolha(true);
	}
	
	public void setLadoAdversaria(int lado) {
		this.tfLadoAdversario.setText(""+lado);
	}
	
	public boolean getJogadaRealizada() {
		return this.jogadaRealizada;
	}
	
	public String getValorJogada() {
		return this.tfSuaEscolha.getText();
	}
	
	public void mostraInformação(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void mostraErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		tfEstado.setEditable(false);
		tfEstado.setBounds(10, 230, 214, 20);
		tfEstado.setColumns(10);
		tfLadoAdversario.setEditable(false);
		tfLadoAdversario.setEnabled(false);
		tfLadoAdversario.setBounds(10, 143, 105, 20);
		tfLadoAdversario.setColumns(10);
		tfSuaEscolha.setEnabled(false);
		tfSuaEscolha.setEditable(false);
		tfSuaEscolha.setBounds(10, 87, 105, 20);
		tfSuaEscolha.setColumns(10);
		
		tela.getContentPane().setEnabled(false);
		tela.setBounds(100, 100, 250, 300);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.getContentPane().setLayout(null);
		lbSuaEscolha.setBounds(10, 62, 105, 14);
		
		tela.getContentPane().add(lbSuaEscolha);
		lbEscolhaAdversario.setEnabled(false);
		lbEscolhaAdversario.setBounds(10, 174, 105, 14);
		
		tela.getContentPane().add(lbEscolhaAdversario);
		tela.getContentPane().add(tfSuaEscolha);
		tfEscolhaAdversario.setEditable(false);
		tfEscolhaAdversario.setEnabled(false);
		tfEscolhaAdversario.setColumns(10);
		tfEscolhaAdversario.setBounds(10, 196, 105, 20);
		
		tela.getContentPane().add(tfEscolhaAdversario);
		lbSeuLado.setBounds(10, 11, 105, 14);
		
		tela.getContentPane().add(lbSeuLado);
		lbLadoAdversario.setEnabled(false);
		lbLadoAdversario.setBounds(10, 118, 105, 14);
		
		tela.getContentPane().add(lbLadoAdversario);
		tela.getContentPane().add(tfLadoAdversario);
		tela.getContentPane().add(tfEstado);
		
		btJogar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jogadaRealizada = true;
				tfSuaEscolha.setEditable(false);	
			}
		});
		
		btJogar.setBounds(135, 11, 89, 205);
		
		tela.getContentPane().add(btJogar);
		cbEscolherLado.setEnabled(false);
		cbEscolherLado.setModel(new DefaultComboBoxModel(new String[] {"Ímpar", "Par", " "}));
		cbEscolherLado.setSelectedIndex(2);
		cbEscolherLado.setBounds(10, 36, 105, 20);
		
		tela.getContentPane().add(cbEscolherLado);
		
	}
}
