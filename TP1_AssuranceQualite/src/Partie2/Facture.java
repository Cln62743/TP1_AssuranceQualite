package Partie2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Facture {

	java.text.DecimalFormat df = new java.text.DecimalFormat("0.00$");

	BufferedWriter bw = null;
	
	private String[] clients;
	String nomFichier = "";

	private double prix;
	private double[] taxe = new double[2];
	int nbClients = 0;
	
	private ArrayList<String> erreurCommande;
	private ArrayList<String> afficherFacture = new ArrayList<>();
	
	// obtenir l'heure courante
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance();
	String date = format.format(calendar.getTime());

	// Interface graphique
	InterfaceGraphique interfaceGraphique;

	
	// --- A lire Philippe --- Tu recois le numéro de table et les clients attitré a cette table du coup facture sera appeler plusieur fois
	
	// --- A lire Philippe --- Pour afficher les factures dans l'interface graphique utilise
	// --- la methode ajouterFactureAffichage() avec le nom complet du fichier .txt en parametre le reste devrais se faire tout seule
	public Facture( String nomFacture, String[] clients, double prixM, ArrayList<String> erreur, InterfaceGraphique interfaceGraphique) {
		this.interfaceGraphique = interfaceGraphique;
		this.clients = clients;
		this.prix = prixM;
		this.erreurCommande = erreur;
		
		if(!checkValidity(this.prix)) {
			return;
		}
		
		for (int i = 1; i < clients.length; i++) {	
			if(clients[i] != null) {
				nbClients++;
			}	
		}
		
		this.nomFichier = nomFacture + "_" + clients[0] + "_" + nbClients + "_" + date + ".txt";
		
		File file = new File(nomFichier);
				
		FileWriter fw;
		
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {

			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			afficher();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		interfaceGraphique.ajouterFactureAffichage(nomFichier);
	}

	public void afficher() {
		rentrerVariableList();
		
		try {
			for (int i = 0; i < afficherFacture.size(); i++) {
				//System.out.println(afficherFacture.get(i));
				bw.write(afficherFacture.get(i));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public void rentrerVariableList() {
		if (erreurCommande.size() != 0) {
			rentrerListAvecErreur();
		}

		rentrerListSansErreur();
	}

	private void rentrerListAvecErreur() {
		afficherFacture.add("commande erroné : ");

		for (int i = 0; i < erreurCommande.size(); i++) {
			afficherFacture.add(erreurCommande.get(i));
		}
		afficherFacture.add("\n");
	}
	
	private void rentrerListSansErreur() {
		double fraisService = 0;
		
		afficherFacture.add("Bienvenue chez Barette!");

		afficherFacture.add("Factures de la table " + clients[0] + " : ");
		afficherFacture.add("");
		afficherFacture.add("Liste des clients de la table : ");
		
		for (int i = 1; i <= nbClients; i++) {
			afficherFacture.add(clients[i]);
		}
		
		afficherFacture.add("");
		afficherFacture.add("Montant : " + df.format(prix));
		
		if(prix > 100.0 || nbClients >= 3) {
			prix =	prixAvecFrais(prix, nbClients);
			fraisService = prix * 0.15;
			afficherFacture.add("Frais de service : " + df.format(fraisService));
		}
		
		taxe[0] = calculTps(prix);
		taxe[1] = calculTvq(prix);
		prix += taxe[0] + taxe[1];
		
		afficherFacture.add("Tps : " +  df.format(taxe[0]));
		afficherFacture.add("Tvq : " + df.format(taxe[1]));
		
		afficherFacture.add("Montant total: " + df.format(prix));
	}
	
	private double prixAvecFrais(double prix, int nbClients) {
		double resultat = 0;
		resultat = prix + (prix * 0.15);
		
		return resultat;
	}
	
	// Check if price is higher than 0
	public static boolean checkValidity(double prixLigne) {
		boolean result = false;
		if (prixLigne != 0) {
			result = true;
		}
		return result;
	}

	public static double calculTps(double prix) {
		return prix * .05;
	};

	public static double calculTvq(double prix) {
		return prix * .10;
	}
}
