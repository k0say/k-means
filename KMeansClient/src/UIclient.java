
import data.*;
import utility.ArraySet;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.List;
import javax.swing.JSplitPane;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextArea;
import javax.swing.JTable;
import mining.*;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
public class UIclient {

	/**
	 * Questa classe nota come UIClient (abbreviazione di "user interface client") inizializza i componenti dell'interfaccia grafica del client e le loro funzionalità,
	 * nonchè disposizioni all'interno del frame principale.
	 * 
	 * @author Giuseppe Pio Maffia, Giovanni Vittore
	 */
	private JFrame frmKmeansProject;
	private JTextField textField;
	private JTextField textField_1;
	PrintWriter output;
	BufferedReader input;
	MainTest mainTest;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIclient window = new UIclient();
					window.frmKmeansProject.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UIclient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKmeansProject = new JFrame();
		frmKmeansProject.setTitle("KMeans Project");
		frmKmeansProject.setBackground(Color.ORANGE);
		frmKmeansProject.setBounds(100, 100, 913, 421);
		frmKmeansProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnLettura = new JButton("Lettura");
		btnLettura.setEnabled(false);
		btnLettura.setBounds(92, 285, 132, 25);
		btnLettura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					mainTest.Lettura();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		frmKmeansProject.getContentPane().setLayout(null);
		frmKmeansProject.getContentPane().add(btnLettura);
		
		JButton btnScoperta = new JButton("Scoperta");
		btnScoperta.setEnabled(false);
		btnScoperta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					mainTest.Scoperta();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnScoperta.setBounds(92, 323, 132, 25);
		frmKmeansProject.getContentPane().add(btnScoperta);
		
		JLabel lblSelezione = new JLabel("Selezione");
		lblSelezione.setFont(new Font("Verdana Pro Cond", Font.BOLD, 17));
		lblSelezione.setBounds(113, 244, 121, 28);
		frmKmeansProject.getContentPane().add(lblSelezione);
		
		textField = new JTextField();
		textField.setBounds(92, 78, 132, 25);
		frmKmeansProject.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JLabel lblConnessione = new JLabel("Connessione");
		lblConnessione.setFont(new Font("Verdana Pro", Font.BOLD, 16));
		lblConnessione.setBounds(103, 13, 121, 25);
		frmKmeansProject.getContentPane().add(lblConnessione);
		
		textField_1 = new JTextField();
		textField_1.setBounds(92, 145, 132, 25);
		frmKmeansProject.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		JLabel lblIpAddress = new JLabel("Ip Address:");
		lblIpAddress.setBounds(12, 51, 71, 16);
		frmKmeansProject.getContentPane().add(lblIpAddress);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(12, 115, 56, 16);
		frmKmeansProject.getContentPane().add(lblPorta);
		
		JButton btnNewButton_1 = new JButton("Connetti");
		btnNewButton_1.setBounds(103, 206, 97, 25);
		frmKmeansProject.getContentPane().add(btnNewButton_1);
		
		//JTextPane textPane = new JTextPane();
		//textPane.setEditable(false);
		//textPane.setBounds(335, 36, 511, 297);
		//frmKmeansProject.getContentPane().add(textPane);
		
		//ScrollablePane scroll = new ScrollablePane();
		//frmKmeansProject.getContentPane().add(scroll);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(333, 37, 536, 299);
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setBounds(333, 37, 536, 299);
		frmKmeansProject.getContentPane().add(jsp);
		
		
		
		JButton btnCaricaCluster = new JButton("Carica Cluster");
		btnCaricaCluster.setEnabled(false);
		btnCaricaCluster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String testo = mainTest.text;
				//textPane.setText(testo);
				//scroll.setText(testo);
				textArea.setText(testo);
			}
		});
		btnCaricaCluster.setBounds(333, 346, 121, 25);
		frmKmeansProject.getContentPane().add(btnCaricaCluster);
		
		

		//JScrollPane scrollPane;
		//scrollPane = new JScrollPane(textPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String ipAddress = textField.getText();
				String port = textField_1.getText();
				int porta = Integer.parseInt(port);
				try {
					mainTest = new MainTest(ipAddress,porta);
					JOptionPane.showMessageDialog(btnNewButton_1, "Connessione effettuata!", "Connessione",JOptionPane.INFORMATION_MESSAGE);
					btnNewButton_1.setEnabled(false);
					btnLettura.setEnabled(true);
					btnScoperta.setEnabled(true);
					btnCaricaCluster.setEnabled(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				JOptionPane.showMessageDialog(btnNewButton_1, "Connessione rifiutata!", "Connessione",JOptionPane.ERROR_MESSAGE);
				}
				
				
				
				
				
			}
			
			
			
		});
	
	}
}
