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

	public String[][] getCommandes() {return commandes;	}

	public Calcul() {
		// Appeler methode pour ouvrir le fichier .txt
		ouvertureFichier();
		assignationClients();
		
		faireCalcul();
	}

	// Méthode principale classées par ordre d'utilisation
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
		remplirSub();
		
		//
		String[] temp = sub.get(0).split(" ");
		do {
			sub.remove(0);
			i = 2;
			
			int numTable = Integer.parseInt(temp[0]);
			int actuListPos = -1;
			
			for(int k = 0; k < clients.length; k++) {
				if(numTable == Integer.parseInt(clients[k][0])) {
					actuListPos = k;
					break;
				}
			}
			
			temp[1] = checkClientValidity(temp[1]);
			
			if (!temp[1].equals(" ")) {
				clients[actuListPos][1] = temp[1];
				int left = sub.size()-1;
				int j = 0;
				if(sub.isEmpty()) { break;}
				do {
					
					
					temp = sub.get(j).split(" ");
					
					if(Integer.parseInt(temp[0]) == numTable) {
						
						sub.remove(j);
						clients[actuListPos][i] = temp[1];
						i++;	
					}
					
					left--;
					j++;
				}while(left > 1);
			} else {
				erreur.add("Client non valide");
			}
			temp = sub.get(0).split(" ");
		} while(!sub.isEmpty());
		
		for(int j = 0; j < clients.length; j++) {
			for(int k = 0; k < clients[0].length; k++) {
				System.out.println(clients[j][k]);
			}
		}
		
		System.out.println("Fin assignation client");
		
		assignationPlats();	
	}

	private void assignationPlats() {
		remplirSub();
		
		String[] temp = sub.get(0).split(" ");
		plats = new String[sub.size()][2];
		int i = 0;
		do {
			sub.remove(0);
			for (int j = 0; j < plats[0].length; j++) {
				plats[i][j] = temp[j];
			}
			if(sub.isEmpty()) {break;}
			i++;
			temp = sub.get(0).split(" ");
		}while(!sub.isEmpty());
		
		for(int j = 0; j < plats.length; j++) {
			for(int k = 0; k < plats[0].length; k++) {
				System.out.println(plats[j][k]);
			}
		}
		
		System.out.println("Fin assignation plat");
		
		assignationCommande();
	}

	private void assignationCommande() {
		remplirSub();
		
		commandes = new String[sub.size()][4];
		String[] temp = sub.remove(0).split(" ");
		int i = 0;
		do {
			try {
				// ---------------------------------- A reprendre ici ---------------------------------------------
				// Separe les lignes en trois partie / Client / Commande / nb Commandes
				for (int j = 0; j < commandes.length; j++) {
					temp = sub.get(j).split(" ");
	
					for (int k = 0; k < commandes[0].length; k++) {
						if (!CheckPlatExist(temp[1])) {
							erreur.add("Le plat choisi n'existe pas.");
							break;
						if (!CheckClientExist(temp[0])) {
							erreur.add("Le client choisi n'existe pas.");
							break;
						if (CheckNbCommandeValid(temp[2])) {
							erreur.add("Le format du nombre de commande n'est pas respecté");
							break;
						}
	
						boolean trouve = false;
	
						for (int l = 0; l < clients.length; l++) {
							if (temp[0].equals(clients[l]) && temp[1].equals(plats[k][0])) {
								commandes[j] = temp;
								trouve = true;
								break;
	
							} else if (temp[0].equals(clients[l])) {
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
		}while(!sub.isEmpty());
		
		for(int j = 0; j < plats.length; j++) {
			for(int k = 0; k < plats[0].length; k++) {
				System.out.println(plats[j][k]);
			}
		}
		
		System.out.println("Fin assignation commande");
	};
	
	// Méthode utilitaire
	
	private void remplirSub() {
		sub.clear();
		String temp = obj.remove(0);
		do {
			sub.add(temp);
			if(obj.isEmpty()) {break;}
			temp = obj.remove(0);
		}while(!temp.contains(":"));
	}
	// Validation
	private String checkClientValidity(String clientM) {
		String clientValidation = " ";
		if (clientM.matches("\\p{javaLetter}+")) {
			clientValidation = clientM;
		}
		return clientValidation;
	}
	
	private void checkNumTable() {
		obj.remove(0);
		String temp = obj.remove(0);
		do {
			sub.add(temp);
			temp = obj.remove(0);
		}while(!temp.contains(":"));
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
			for(int j = 1; j < clients[0].length; j++) {
				if (clientM.equals(clients[i][j])) {
					result = true;
					break;
				}
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
			String client = clients[i][0];

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
