package Partie2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InterfaceGraphique extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<ArrayList<String>> ListFacture = new ArrayList<ArrayList<String>>();
	int idAffichage = 0;
	
	private JTextArea affichageFact = new JTextArea("",25, 50);
	private JScrollPane scrollAffichage = new JScrollPane(affichageFact);
	private JButton btnAffichageGauche = new JButton();
	private JButton btnAffichageDroit = new JButton();
	
	private JTextField nomFactureCustom = new JTextField("Facture", 30);
	
	private JButton btnLireFichier = new JButton("Lire le fichier");
	private JButton btnProduireFact = new JButton("Produire la facture");
	
	private Calcul calcul = new Calcul(this);
	
	public InterfaceGraphique() {
		super("Logiciel de facturation de chezBarette");
		setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon(InterfaceGraphique.class.getResource("../Image/Icone.jpg"));
		setIconImage(img.getImage());
		this.setContentPane(new JLabel(new ImageIcon(InterfaceGraphique.class.getResource("../Image/Background.jpg"))));
		this.setLayout(new FlowLayout());
		setResizable(false);
		this.pack();
		
		// Initialiser l'interface
		initialiserInterface();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initialiserInterface() {
		// Panneau affichage des factures
		JPanel panneauAffichageFact = new JPanel();
		// Text area qui affiche les factures
		affichageFact.setEditable(false);
		affichageFact.setBackground(new Color( 255, 255, 255, 150));
		scrollAffichage.setOpaque(false);
		
		ImageIcon imgIcon1 = new ImageIcon(InterfaceGraphique.class.getResource("../Image/IconeDeplacementGauche.png"));
		Image img1 = imgIcon1.getImage();
		img1 = img1.getScaledInstance(25, 40, java.awt.Image.SCALE_SMOOTH);
		btnAffichageGauche.setIcon(imgIcon1 = new ImageIcon(img1));
		btnAffichageGauche.addActionListener(this);
		btnAffichageGauche.setEnabled(false);
		
		ImageIcon imgIcon2 = new ImageIcon(InterfaceGraphique.class.getResource("../Image/IconeDeplacementDroit.png"));
		Image img2 = imgIcon2.getImage();
		img2 = img2.getScaledInstance(25, 40, java.awt.Image.SCALE_SMOOTH);
		btnAffichageDroit.setIcon(imgIcon2 = new ImageIcon(img2));
		btnAffichageDroit.addActionListener(this);
		btnAffichageDroit.setEnabled(false);
		
		panneauAffichageFact.add(btnAffichageGauche);
		panneauAffichageFact.add(scrollAffichage);
		panneauAffichageFact.add(btnAffichageDroit);
		// 2 boutons qui permette de se déplacer entre les factures de table
		
		
		// Panneau des informations custom
		JPanel panneauInfoCustom = new JPanel();
		
		// Label pour identifier le field
		JLabel txtNomFactCustom = new JLabel("Nom des factures : ");
		panneauInfoCustom.add(txtNomFactCustom);
		
		// TextField qui sert a saisir un nom custom pour les fichiers factures
		panneauInfoCustom.add(nomFactureCustom);
		
		JLabel txtNomDefault = new JLabel("_[numéroTable]_[nombreClient]_[date].txt");
		panneauInfoCustom.add(txtNomDefault);
		
		// Panneau des boutons
		JPanel panneauBouton = new JPanel();
		
		panneauBouton.add(btnLireFichier);
		panneauBouton.add(btnProduireFact);
		
		btnLireFichier.addActionListener(this);
		btnProduireFact.addActionListener(this);
		
		btnProduireFact.setEnabled(false);
		
		
		//
		panneauAffichageFact.setOpaque(false);
		panneauInfoCustom.setBackground(new Color( 255, 255, 255, 150));
		panneauBouton.setOpaque(false);
		
		// Ajout des panneaux a l'interface
		this.add(panneauAffichageFact, BorderLayout.CENTER);
		this.add(panneauInfoCustom);
		this.add(panneauBouton, BorderLayout.SOUTH);
		}
	
	// Methode utilitaire
	private String getNomFactureCustom() {return nomFactureCustom.getText();}
	
	public void ajouterFactureAffichage(String nomFichier) {
		try {
			FileInputStream file = new FileInputStream(nomFichier);
			ArrayList<String> facture = new ArrayList<String>();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(file));
			String line = null;

			while ((line = br.readLine()) != null) {
				facture.add(line);
			}
			
			ListFacture.add(facture);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RefreshAffichageFact();
	}

	private void RefreshAffichageFact() {
		affichageFact.setText("");
		
		if(ListFacture.size() < 1) {
			affichageFact.setBackground(new Color( 255, 255, 255, 150));
		}else {
			affichageFact.setBackground(new Color( 255, 255, 255));
		}
		for(String line: ListFacture.get(idAffichage)) {
			affichageFact.append(line + "\n");
		}	
	}

	public static void main(String[] args) {
		InterfaceGraphique f = new InterfaceGraphique();
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnLireFichier) {
			
			calcul.ouvertureFichier();
			btnProduireFact.setEnabled(true);
		
		}else if (arg0.getSource() == btnProduireFact){
			
			calcul.InitialiserFacture(getNomFactureCustom());
			btnAffichageGauche.setEnabled(true);
			btnAffichageDroit.setEnabled(true);
			btnProduireFact.setEnabled(false);
		
		}else if (arg0.getSource() == btnAffichageGauche) {
			
			if(idAffichage == 0) {
				idAffichage = ListFacture.size() - 1;
			
			}else {
				idAffichage --;
			}
			RefreshAffichageFact();
		}else if (arg0.getSource() == btnAffichageDroit) {
			
			if(idAffichage == ListFacture.size() - 1) {
				idAffichage = 0;
			
			}else {
				idAffichage ++;
			}
			RefreshAffichageFact();
		}
	}

}
	