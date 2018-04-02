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
	private String nomFacture;
	
	private int maxCLientPerTable = 4;

	public String[][] getCommandes() {return commandes;	}
	
	public Calcul() {}

	public void InitialiserFacture(String nomFacture) {
		this.nomFacture = nomFacture;
		assignationClients();
	}

	// Méthode principale classées par ordre d'utilisation
	public boolean ouvertureFichier() {
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
		
		System.out.println("Fin ouverture de fichier");	
		return true;
	}
	
	private void assignationClients() {
		checkNbTable();
		
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
		
		/*for(int j = 0; j < clients.length; j++) {
			for(int k = 0; k < clients[0].length; k++) {
				System.out.println(clients[j][k]);
			}
		}*/
		
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
		
		/*for(int j = 0; j < plats.length; j++) {
			for(int k = 0; k < plats[0].length; k++) {
				System.out.println(plats[j][k]);
			}
		}*/
		
		System.out.println("Fin assignation plat");
		assignationCommande();
	}

	private void assignationCommande() {
		remplirSub();
		
		ArrayList<String> underSub = new ArrayList<String>();
		do{
			underSub.clear();
			String[] temp = sub.get(0).split(" ");
			int tableActu =	findClientTable(temp[0]);
			underSub.add(sub.remove(0));
			
			String[] clientTable = new String[4];
			
			if(tableActu != -1) {				
				for(int i = 0; i < clientTable.length; i++) {
					clientTable[i] = clients[tableActu][i+1];
				}
			}
			
			int x = 0;
			for(int i = 0; i < sub.size(); i++) {
				boolean trouve = false;
				temp = sub.get(x).split(" ");
				for(int j = 0; j < clientTable.length; j++){				
					if(temp[0].equals(clientTable[j])) {
						underSub.add(sub.remove(x));
						trouve = true;
						break;
					}
				}
				if(!trouve) { x++; }
			}
			
			commandes = new String[underSub.size()][3];
			for(int i = 0; i < commandes.length; i++) {
				temp = underSub.remove(0).split(" ");
				boolean valid = true;
				if (!CheckPlatExist(temp[1])) {
					erreur.add("Le plat choisi n'existe pas.");
					valid = false;
				}
				if (!CheckNbCommandeValid(temp[2])) {
					erreur.add("Le format du nombre de commande n'est pas respecté");
					valid = false;
				}
				
				if(valid) {
					for(int j = 0; j < commandes[0].length; j++) {
						commandes[i][j] = temp[j];
					}
				}
			}
			
			
			for(int j = 0; j < commandes.length; j++) {
				for(int k = 0; k < commandes[0].length; k++) {
					System.out.print(commandes[j][k] + " ");
				}
				System.out.println();
			}
			faireCalcul(tableActu);
		}while(!sub.isEmpty());
		
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
		}while(!temp.contains(":") && !temp.contains("Fin"));
	}
	// Validation
	private String checkClientValidity(String clientM) {
		String clientValidation = " ";
		if (clientM.matches("\\p{javaLetter}+")) {
			clientValidation = clientM;
		}
		return clientValidation;
	}
	
	private void checkNbTable() {
		obj.remove(0);
		String temp = obj.remove(0);
		do {
			sub.add(temp);
			temp = obj.remove(0);
		}while(!temp.contains(":"));
	}

	private int findClientTable(String client) {
		int table = -1;
		
		for(int i = 0; i < clients.length; i++) {
			for(int j = 1; j < clients[0].length; j++) {
				if(client.equals(clients[i][j])) {
					table = i;
					break;
				}
			}
		}
		
		return table;
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
		if (nbCommande.matches("\\d+")) {
			if (Integer.parseInt(nbCommande) > 0) {
				result = true;
			}
		}
		return result;
	}

	/// ------------------------------------------------------------------------------------------------
	private void faireCalcul(int table) {
		double resultatPrix = 0;
		
		for(int i = 0; i < commandes.length; i++) {
			String platCommande = commandes[i][1];
			int nbFois = Integer.parseInt(commandes[i][2]);
			for(String[] plat : plats) {
				if(platCommande.equals(plat[0])) {
					resultatPrix += Double.parseDouble(plat[1]) * nbFois;
				}
			}
		}

		System.out.println("Fin faire calcul");
		new Facture(nomFacture,clients[table], resultatPrix, erreur);
	}

	public static void main(String[] args) {

		new Calcul();
	}
}
	