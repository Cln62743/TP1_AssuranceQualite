package Partie2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Facture{
	
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.00$");
	
	private String[] clients;
	
	private double[] prix;
	private double[] taxe = new double [2];
	  
	
	File file = new File("FactureSortie.txt");
	BufferedWriter bw = null;
	
	public Facture(String[] clientsM, double[] prixM, String[] erreur){
		
		FileWriter fw;
		this.clients = clientsM;
		this.prix = prixM;	
		
		if(!file.exists()) {
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
	
	public void afficher(){
		
		try {
			bw.write("Bienvenue chez Barette!");
			bw.newLine();
			bw.write("Factures:");
			bw.newLine();
			
			for(int i = 0; i < clients.length; i++) {
				if(checkValidity(prix[i])) {
					prix[i] += taxe[0] + taxe[1];
					bw.write(clients[i] + " " + df.format(prix[i]) + " TPS : " + df.format(calculTps(prix[i])) + " TVQ : " + df.format(calculTvq(prix[i])) + "");
					bw.newLine();
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Check if price is higher than 0
	public static boolean checkValidity(double prixLigne) {
		boolean result = false;
		if(prixLigne != 0) {
			result = true;
		}	
		return result;
	}
	
	public static double calculTps (double prix) {return prix * .05;};
	public static double calculTvq (double prix) {return prix * .10;};
}
