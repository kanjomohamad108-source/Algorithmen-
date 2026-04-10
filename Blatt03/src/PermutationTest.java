import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=1;
	
	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}
	

	@Test
	void testPermutation() {
		initialize();
		// TODO
		assertTrue(p1.original.length == n1 , "Wrong length");
		assertTrue(p2.original.length == n2, "Wrong length");
		assertTrue(p1.allDerangements != null, "Wrong derangements");
		assertTrue(p2.allDerangements != null, "Wrong derangements");

		for (int i = 0 ; i < n1 ; i++){
			for (int j = i+1 ; j < n1 ; j++){
				assertFalse(p1.original[i] == p1.original[j], "Same elements");
			}
		}
		for (int i = 0 ; i < n2 ; i++){
			for (int j = i+1 ; j < n2 ; j++){
				assertFalse(p2.original[i] == p2.original[j], "Same elements");
			}
		}



	}

	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
		p1.derangements();
		p2.derangements();

		assertFalse(p1.allDerangements.isEmpty(), "p1: keine Derangements ");
		assertFalse(p2.allDerangements.isEmpty(), "p2: keine Derangements ");

		for (int[] Derangments : p1.allDerangements) {
			assertEquals(n1, Derangments.length, "p1: Falsche Länge im Derangement");
			for (int i = 0; i < n1; i++) {
				assertNotEquals(p1.original[i], Derangments[i],
						"Fixpunkt in p1 bei Index " + i + " (Wert: " + Derangments[i] + ")");
			}
		}

		for (int[] Derangments : p2.allDerangements) {
			assertEquals(n2, Derangments.length, "p2: Falsche Länge im Derangement");
			for (int i = 0; i < n2; i++) {
				assertNotEquals(p2.original[i], Derangments[i],
						"Fixpunkt in p2 bei Index " + i + " (Wert: " + Derangments[i] + ")");
			}
		}

	}


	@Test
	void testsameElements() {
		initialize();
		fixConstructor();
		p1.derangements();
		p2.derangements();

		assertFalse(p1.allDerangements.isEmpty(), "p1: Liste ist leer");
		assertFalse(p2.allDerangements.isEmpty(), "p2: Liste ist leer");

		int[] sortedOriginal1 = Arrays.copyOf(p1.original, p1.original.length);
		int[] sortedOriginal2 = Arrays.copyOf(p2.original, p2.original.length);
		Arrays.sort(sortedOriginal1);
		Arrays.sort(sortedOriginal2);

		for (int[] derangement : p1.allDerangements) {
			assertEquals(p1.original.length, derangement.length, "p1: Falsche Länge im Derangement");
			int[] sortedDerangment = Arrays.copyOf(derangement, derangement.length);
			Arrays.sort(sortedDerangment);
			assertArrayEquals(sortedOriginal1, sortedDerangment, "p1: Derangement enthält nicht dieselben Elemente wie original");
		}

		for (int[] derangement : p2.allDerangements) {
			assertEquals(p2.original.length, derangement.length, "p2: Falsche Länge im Derangement");
			int[] sortedDerangment = Arrays.copyOf(derangement, derangement.length);
			Arrays.sort(sortedDerangment);
			assertArrayEquals(sortedOriginal2, sortedDerangment, "p2: Derangement enthält nicht dieselben Elemente wie original");
		}
	}


	void setCases(int c) {
		this.cases=c;
	}

	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;

		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
}


