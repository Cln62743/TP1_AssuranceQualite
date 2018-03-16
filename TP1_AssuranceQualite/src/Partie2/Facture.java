package Partie2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Facture{
	
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.00$");
	
	private String[] clients;
	
	private double[] prix;
	private double[] taxe = new double [2];
	private String[] erreurCommande;  
	  DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
	  
	  
      //obtenir l'heure courante
      Calendar calendar = Calendar.getInstance();
      String date = format.format(calendar.getTime());
      
      String nomFichier = "Facture du "+ date +".txt";
      
	File file = new File(nomFichier);
	BufferedWriter bw = null;
	private ArrayList<String> afficherFacture = new ArrayList<>();
	
	public Facture(String[] clientsM, double[] prixM, String[] erreur){
		
		FileWriter fw;
		this.clients = clientsM;
		this.prix = prixM;	
		this.erreurCommande = erreur;
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
			afficherTerminal();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void afficher(){
		afficherFacture = RentrerVariableList();
		try {
		for (int i = 0; i < afficherFacture.size(); i++) {
			bw.write(afficherFacture.get(i));
			bw.newLine();
		}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void afficherTerminal() {
		afficherFacture = RentrerVariableList();
		for (int i = 0; i < afficherFacture.size();i++) {
			System.out.println(afficherFacture.get(i));
		}
	
	
	
	}
	
	public ArrayList<String> RentrerVariableList() {
		ArrayList<String> facture =  new ArrayList<>();
		facture.add("commande erroné et la raison");
		for (int i = 0; i < erreurCommande.length  ; i++) {
			facture.add(erreurCommande[i]);
		}
		facture.add("Bienvenue chez Barette!");
	
		facture.add("Factures:");
	
		for(int i = 0; i < clients.length; i++) {
			if(checkValidity(prix[i])) {
				prix[i] += taxe[0] + taxe[1];
				facture.add(clients[i] + " " + df.format(prix[i]) + " TPS : " + df.format(calculTps(prix[i])) + " TVQ : " + df.format(calculTvq(prix[i])) + "");
				
			}
			
		}
		
		return facture;
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
