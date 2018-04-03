package Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Partie2.Calcul;
import Partie2.Facture;

class testPhilippeChevry {


	@Test
	void testClientsNull() {
		Calcul calcul1 = new Calcul(null);
		assertNull(calcul1.clients);
	}
	
	@Test
	void testCommandeNull() {
	
	Calcul calcul = new Calcul(null);
			assertNull(calcul.getCommandes());	
	}

	@Test
	void testBooleanOuvertureFichier() {
		Calcul calcul2 = new Calcul(null);
		assertTrue(calcul2.ouvertureFichier());	
	}

}
