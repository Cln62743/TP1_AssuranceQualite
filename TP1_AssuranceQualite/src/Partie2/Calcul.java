package Partie2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Calcul {

	private ArrayList<String> obj = new ArrayList<String>();
	private ArrayList<String> sub = new ArrayList<String>();
	private ArrayList<String> erreur = new ArrayList<String>();

	public String[][] clients;
	private String[][] plats;
	private String[][] commandes;
	
	private int maxCLientPerTable = 4;

	public String[][] getCommandes() {
		return commandes;
	}

	public Calcul() {

		// Appeler methode pour ouvrir le fichier .txt
		ouvertureFichier();

		assignationClients();
		
		faireCalcul();
	}

	private void ouvertureFichier() {
		File file = new File("Facture.txt");
		try {

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				obj.add(line);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void assignationClients() {
		checkNumTable();
		
		//
		clients = new String[sub.size()][maxCLientPerTable + 1];
		int i = 0;
		for(String table : sub) {
			clients[i][0] = table;
			i++;
		}
		
		//
		String temp = obj.remove(0);
		do {
			sub.add(temp);
		}while(!temp.contains(":"));
		
		//
		String[] tempEntry;
		do {
			i = 2;	
			tempEntry = sub.remove(0).split(" ");
			int numTable = Integer.parseInt(tempEntry[0]); 
			
			tempEntry[1] = checkClientValidity(tempEntry[1]);
			
			if (!tempEntry[1].equals(" ")) {
				clients[numTable][1] = temp;
				for(int j = 0; j < sub.size(); j++) {
					tempEntry = sub.get(j).split(" ");
					if(Integer.parseInt(tempEntry[0]) == numTable) {
						sub.remove(j);
						clients[numTable][i] = temp;
						i++;
					}
				}
			} else {
				erreur.add("Client non valide");
			}
		}while(!temp.contains(":"));
		
		for(int j = 0; j < clients.length; j++) {
			for(int k = 0; k < clients[0].length; k++) {
				System.out.println(clients[j][k]);
			}
		}
		
		assignationPlats();
	}
	
	private void checkNumTable() {
		String temp = obj.remove(0);
		do {
			sub.add(temp);
		}while(!temp.contains(":"));
	}

	private void assignationPlats() {
		plats = new String[sub.size()][2];
		String[] tempPlat;

		for (int j = 0; j < plats.length; j++) {
			tempPlat = sub.get(j).split(" ");
			for (int k = 0; k < plats[0].length; k++) {
				plats[j][k] = tempPlat[k];
			}
		}
		
		assignationCommande();
	}

	private void assignationCommande() {
		try {
			commandes = new String[sub.size()][3];
			String[] tempCommande;

			// Separe les lignes en trois partie / Client / Commande / nb Commandes
			for (int j = 0; j < commandes.length; j++) {
				tempCommande = sub.get(j).split(" ");

				for (int k = 0; k < commandes[0].length; k++) {
					if (!CheckPlatExist(tempCommande[1])) {
						erreur.add("Le plat choisi n'existe pas.");
						break;
					} else if (!CheckClientExist(tempCommande[0])) {
						erreur.add("Le client choisi n'existe pas.");
						break;
					} else if (CheckNbCommandeValid(tempCommande[2])) {
						erreur.add("Le format du nombre de commande n'est pas respecté");
						break;
					}

					boolean trouve = false;

					for (int l = 0; l < clients.length; l++) {
						if (tempCommande[0].equals(clients[l]) && tempCommande[1].equals(plats[k][0])) {
							commandes[j] = tempCommande;
							trouve = true;
							break;

						} else if (tempCommande[0].equals(clients[l])) {
							break;

						}
					}
					if (trouve) {
						break;
					}
				}
			}
		} catch (Exception e) {
			erreur.add("Le format de commande ne respecte pas le format demandé.");
		}
	};

	// Validation
	private String checkClientValidity(String clientM) {
		String clientValidation = " ";
		if (clientM.matches("\\p{javaLetter}+")) {
			clientValidation = clientM;
		}
		return clientValidation;
	}

	private boolean CheckPlatExist(String platM) {
		boolean result = false;
		for (int i = 0; i < plats.length; i++) {
			if (platM.equals(plats[i][0])) {
				result = true;
				break;
			}
		}
		return result;
	}

	private boolean CheckClientExist(String clientM) {
		boolean result = false;
		for (int i = 0; i < clients.length; i++) {
			if (clientM.equals(clients[i])) {
				result = true;
				break;
			}
		}
		return result;
	}

	private boolean CheckNbCommandeValid(String nbCommande) {
		boolean result = false;
		if (nbCommande.matches("[0-9]")) {
			if (Integer.parseInt(nbCommande) > 0) {
				result = true;
			}
		}
		return result;
	}

	/// ------------------------------------------------------------------------------------------------
	private void faireCalcul() {
		double[] prix = new double[clients.length];
		double resultatPrix = 0;

		for (int i = 0; i < clients.length; i++) {
			String client = clients[i];

			for (int j = 0; j < commandes.length; j++) {
				if (client.equals(commandes[j][0])) {
					String plat = commandes[j][1];

					for (int k = 0; k < plats.length; k++) {
						if (plat.equals(plats[k][0])) {

							try {
								double nb = Double.parseDouble(commandes[j][2]);
								double prixUnit = Double.parseDouble(plats[k][1]);
								resultatPrix = nb * prixUnit;

								prix[i] += resultatPrix;
								resultatPrix = 0;
							} catch (NumberFormatException e) {
								System.out.print("Erreur calcul");
							}
						}
					}
				}
			}
		}

		new Facture(clients, prix, erreur);
	}

	public static void main(String[] args) {

		new Calcul();
	}
}
