package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Partie2.Facture;

class FactureTest_Charles {

	double prix = 20.0;

	// Test de Facture
	@Test
	public final void testCheckTPS() {
		double tps = 5.0;
		assertEquals(tps,Facture.CalculTps(prix * 5));		
	}
	
	@Test
	public final void testCheckTVQ() {
		double tvq = 10.0;
		assertEquals(tvq,Facture.CalculTvq(prix * 5));
	}
	
	@Test
	public final void testCheckValidity() {
		assertTrue(Facture.CheckValidity(prix));
	}
}
