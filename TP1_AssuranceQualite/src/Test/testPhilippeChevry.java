package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Partie2.Calcul;
import Partie2.Facture;

class testPhilippeChevry {


	@Test
	void testListeAffichageAvecErreur() {
		 String[] clientsM1 = {"7","6" ,"5"} ;
		String fichier1 = "";
		 double prixM1 =   43.33;
		 ArrayList<String>erreur1 = new ArrayList<>() ;
		 erreur1.add("format incorrect");
		Facture facture = new Facture(fichier1, clientsM1,  prixM1, erreur1);
		facture.rentrerVariableList();
		if (facture.rentrerVariableList().size() >= 1) {
			assertEquals(1, facture.rentrerVariableList().size());
		}
	}
	
	@Test
	void testCommandeNull() {
	
	Calcul calcul = new Calcul();
			assertNull(calcul.getCommandes());	
	}

	@Test
	void testTableauErreurNull() {
		String fichier = "";
		 String[] clientsM10 = {"4","2" ,"1"} ;
			
		 double prixM10 = 15.45;
		 
		 ArrayList<String>erreur10 = new ArrayList<>() ;
		 erreur10.add(" ");
		Facture facture2 = new Facture(fichier, clientsM10,  prixM10, erreur10);
		facture2.erreurCommande = null;
		assertNull(facture2.erreurCommande);
	}

}
