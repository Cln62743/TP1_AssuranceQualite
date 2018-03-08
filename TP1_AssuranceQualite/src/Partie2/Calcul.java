package Partie2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Calcul {
	
	private ArrayList<String> obj = new ArrayList<String>();
	private ArrayList<String> sub = new ArrayList<String>();
	
	public String[] clients ;
	private String[][] plats ;
	private String[][] commandes ;
	
	File file = new File("Facture.txt");
	
	public double[] prix;
	
	public Calcul() {
		
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
		
		int i = 0;
		String temp;
		do{
			
			temp = obj.remove(0);
			
			if(temp.indexOf(':') >= 0 || temp.equals("Fin")) {
				switch(i){
				case 0: break;				
				case 1:
					clients = new String[sub.size()];
					clients = sub.toArray(clients);
					break;
				
				case 2:
					plats = new String[sub.size()][2];
					String[] tempPlat;
					
					for(int j = 0; j < plats.length; j++) {
						tempPlat = sub.get(j).split(" ");
						
						for(int k = 0; k < plats[0].length; k++) {
							plats[j][k] = tempPlat[k];
						}					
					}
					break;
				
				case 3:
					try {
					commandes = new String[sub.size()][3];
					String[] tempCommande;
					
					for(int j = 0; j < commandes.length; j++) {
						tempCommande = sub.get(j).split(" ");
						
						for(int k = 0; k < commandes[0].length; k++) {
							
							for(int l = 0; l < clients.length; l++) {
								if(tempCommande[0] != clients[l] ||  tempCommande[1] != plats[l][0]) {
									throw new Exception();
								}
								commandes[j][k] = tempCommande[k];
							}
						}					
					}
					break;
					}catch (Exception e) {
						System.out.println("Le fichier ne respecte pas le format demandé !");
						break;
					}
				}				
				i++;
				sub.clear();
				
			}else{
				sub.add(temp);
			}
			
		} while (!temp.equals("Fin"));		
		
		prix = new double[clients.length];
		
		faireCalcul();
	}

	public static void main(String[] args) {
		new Calcul();
	}
	
	private void faireCalcul() {
		double resultatPrix = 0;
		
		for(int i = 0; i < clients.length; i++) {
			String client = clients[i];
			
			for(int j = 0; j < commandes.length; j++) {
				
				if(client.equals(commandes[j][0])) {
					String plat = commandes[j][1];
					
					for(int k = 0; k < plats.length; k++) {
						
						if(plat.equals(plats[k][0])) {
							
							try {
								double nb = Double.parseDouble(commandes[j][2]);
								double prixUnit = Double.parseDouble(plats[k][1]);
								resultatPrix = nb * prixUnit;
								
								prix[i] += resultatPrix;
								resultatPrix = 0;
							}catch(NumberFormatException e) {
								System.out.print("Erreur calcul");
							}				
						}
					}
				}
			}			
		}
		new Facture(clients, prix);
	}
}
