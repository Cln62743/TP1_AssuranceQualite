package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import Partie2.Calcul;
import junit.framework.Assert;

class TestPartie3 {
	double tvq;
	double tps;
	double resultatPrix;
	Calcul calcul;

	@Before
	public void AvantChaqueTest() {
		tvq =  0.10;
		tps = 0.05;
		calcul = new Calcul();
		
	}

	@After
	public void ApresChaqueTest() {
		tvq = 0.00;
		tps = 0.00;
		calcul = null;
		calcul.clients = null;
		calcul.prix = null;
	}
	@SuppressWarnings("deprecation")
	@Test
	void testTVQ() {
	     double tauxTvq = 0.05;
		 assertEquals(tvq,tauxTvq);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testTPS() {
	     double tauxTps = 0.05;
		 assertEquals(tvq,tauxTps);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testResultatPrixPossedeUnPrix() {
	     double prix = 10.50;
	     
	   
	    	 calcul.prix = new double[] {10.50,20.75,0.00};
			assertEquals(prix,calcul.prix[0]);
			 
	}
	
	@Test
	void testClientPossedeMemeNom() {
	    String client = "Céline";
	    
	    	calcul.clients = new String[] { "Céline"};
			assertEquals(client,calcul.clients[0]);
		
	}
	
	@Test()
	void testClientPossedeNull() {
	   
	    
			calcul.clients = null;
			assertNull(calcul.clients);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testResultatPrixEgaleZero() {
	     double prix1 = 0.00;
	     for (int i = 0; i < calcul.prix.length; i++) {
			assertEquals(prix1,calcul.prix[i]);
		}	 
	}
	
}
