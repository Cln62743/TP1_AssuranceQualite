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

	private String[] clients;

	private double prix;
	private double[] taxe = new double[2];
	public ArrayList<String> erreurCommande;
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

	// obtenir l'heure courante
	Calendar calendar = Calendar.getInstance();
	String date = format.format(calendar.getTime());

	String nomFichier = "";
	int nbreClient = 0;
	BufferedWriter bw = null;
	private ArrayList<String> afficherFacture = new ArrayList<>();

	
	// --- A lire Philippe --- Tu recois le numéro de table et les commande attitré a cette table du coup facture sera appeler plusieur fois
	
	// --- A lire Philippe --- Pour afficher les factures dans l'interface graphique utilise
	// --- la methode ajouterFactureAffichage() avec le nom complet du fichier .txt en parametre le reste devrais se faire tout seule
	public Facture( String nomFacture, String[] clients, double prixM, ArrayList<String> erreur) {
		String nomFichier = "Facture de la table numéro "+ clients[0] +"_"
				+ nbreClient+"_" + date + ".txt";
			File file = new File(nomFichier);
		InterfaceGraphique interfaceGraphique = new InterfaceGraphique();
		interfaceGraphique.ajouterFactureAffichage(nomFichier);
		
		FileWriter fw;
		this.clients = clients;
		this.prix = prixM;
		this.erreurCommande = erreur;
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
	}

	public void afficher() {
		afficherFacture = rentrerVariableList();
		try {
			for (int i = 0; i < afficherFacture.size(); i++) {
				System.out.println(afficherFacture.get(i));
				bw.write(afficherFacture.get(i));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> rentrerVariableList() {
		ArrayList<String> facture = new ArrayList<>();
		
		if (erreurCommande.size() != 0) {
			rentrerListAvecErreur(facture);
		}

		rentrerListSansErreur(facture);

		return facture;
	}

	private void rentrerListAvecErreur(ArrayList<String> facture) {
		facture.add("commande erroné et la raison");

		for (int i = 0; i < erreurCommande.size(); i++) {
			facture.add(erreurCommande.get(i));
		}
		facture.add("\n");
	}
	
	private void rentrerListSansErreur(ArrayList<String> facture) {
		facture.add("Bienvenue chez Barette!");

		facture.add("Factures:");
		facture.add("numéro de table");
		for (int i = 0; i < clients.length; i++) {
			prix =	prixAvecFraisOuNon(prix);
			if (checkValidity(prix)) {
				prix += taxe[0] + taxe[1];
				facture.add(clients[i]);
				facture.add( df.format(prix) + " TPS : " + df.format(calculTps(prix))
						+ " TVQ : " + df.format(calculTvq(prix)) + "");
			}
		}
	}
	
	private double prixAvecFraisOuNon(double prix1) {
		double prixFacture = prix1;
		double prixFraisFacture = 0.00;
		int compteurClient = 0;
		for (int i = 0; i < clients[0].length(); i++) {
			compteurClient++;
		}
		nbreClient = compteurClient;
		if (prixFacture > 100 || compteurClient >= 3 ) {
			prixFraisFacture = prixFacture * 0.15;
		} else {
			prixFraisFacture = prixFacture;
		}
		return prixFraisFacture;
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
