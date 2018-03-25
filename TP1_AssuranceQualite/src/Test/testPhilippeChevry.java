package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import Partie2.Facture;

class testPhilippeChevry {

	@Test
	void testListeAffichageAvecErreurNonNull() {
		 String[] clientsM1 = {"christian","ddd" ,"ddddde"} ;
		
		 double[] prixM1 =   {15.45,43.33 ,0.00};
		 ArrayList<String>erreur1 = new ArrayList<>() ;
		 erreur1.add("format incorrect");
		Facture facture = new Facture( clientsM1,  prixM1, erreur1);
		facture.rentrerVariableList();
		assertNotNull(facture.rentrerVariableList());
	}
	
	@Test
	void testListeAffichageAucuneErreurNonNull() {
		 String[] clientsM9 = {"christian","david" ,"ddddddde"} ;
		
		 double[] prixM9 =   {15.45,90.33 ,0.00};
		 ArrayList<String>erreur9 = new ArrayList<>() ;
		 erreur9.add("");
		Facture facture1 = new Facture( clientsM9,  prixM9, erreur9);
		facture1.rentrerVariableList();
			assertNotNull(facture1.rentrerVariableList());	
	}

	@Test
	void testTableauErreurNull() {
		
		 String[] clientsM10 = {"christian","ddd" ,"ddddde"} ;
			
		 double[] prixM10 =   {15.45,90.33 ,0.00};
		 ArrayList<String>erreur10 = new ArrayList<>() ;
		 erreur10.add(" ");
		Facture facture2 = new Facture( clientsM10,  prixM10, erreur10);
		facture2.erreurCommande = null;
		assertNull(facture2.erreurCommande);
	}

}
