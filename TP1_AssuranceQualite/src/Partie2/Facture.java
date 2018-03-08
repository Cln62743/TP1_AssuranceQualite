package Partie2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Facture{
	
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.00$");
	
	private String[] clients;
	
	private double[] prix;
	
	File file = new File("FactureSortie.txt");
	BufferedWriter bw = null;
	
	public Facture(String[] clientsM, double[] prixM){
		
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
				bw.write(clients[i] + " " + df.format(prix[i]) + "");
				bw.newLine();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
