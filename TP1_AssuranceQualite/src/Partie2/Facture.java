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

	private double[] prix;
	private double[] taxe = new double[2];
	public ArrayList<String> erreurCommande;
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

	// obtenir l'heure courante
	Calendar calendar = Calendar.getInstance();
	String date = format.format(calendar.getTime());

	String nomFichier = "Facture du " + date + ".txt";

	File file = new File(nomFichier);
	BufferedWriter bw = null;
	private ArrayList<String> afficherFacture = new ArrayList<>();

	public Facture(String[] clientsM, double[] prixM, ArrayList<String> erreur) {

		FileWriter fw;
		this.clients = clientsM;
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
			afficherTerminal();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void afficher() {
		afficherFacture = rentrerVariableList();
		try {
			for (int i = 0; i < afficherFacture.size(); i++) {
				bw.write(afficherFacture.get(i));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void afficherTerminal() {
		afficherFacture = rentrerVariableList();
		for (int i = 0; i < afficherFacture.size(); i++) {
			System.out.println(afficherFacture.get(i));
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

	private void rentrerListSansErreur(ArrayList<String> facture) {
		facture.add("Bienvenue chez Barette!");

		facture.add("Factures:");

		for (int i = 0; i < clients.length; i++) {
			if (checkValidity(prix[i])) {
				prix[i] += taxe[0] + taxe[1];
				facture.add(clients[i] + " " + df.format(prix[i]) + " TPS : " + df.format(calculTps(prix[i]))
						+ " TVQ : " + df.format(calculTvq(prix[i])) + "");
			}
		}
	}

	private void rentrerListAvecErreur(ArrayList<String> facture) {
		Calcul calcul = new Calcul();

		facture.add("commande erron� et la raison");

		for (int i = 0; i < calcul.getCommandes().length; i++) {
			for (int j = 0; j < calcul.getCommandes()[i].length; j++) {
				erreurCommande.add(calcul.getCommandes()[i][j]);
			}
		}

		for (int i = 0; i < erreurCommande.size(); i++) {
			facture.add(erreurCommande.get(i));
		}
		facture.add("\n");

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
