package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Partie2.Facture;

class testPhilippeChevry {

	@Test
	void testListeAffichageNonNull() {
		 String[] clientsM1 = {"christian","ddd" ,"ddddde"} ;
		
		 double[] prixM1 =   {15.45,43.33 ,0.00};
		 String[] erreur1 = {" format incorrect"} ;
		Facture facture = new Facture( clientsM1,  prixM1, erreur1);
		facture.RentrerVariableList();
		assertNotNull(facture.RentrerVariableList());
	}
	
	


}
