package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import _r6.CovidEvolutionDiff;

public class CovidEvolutionDiffTest {

	CovidEvolutionDiff p1,p2,p3,p4;
	
	@Before
	public void setUp() {
		p1=new CovidEvolutionDiff();
		p2=new CovidEvolutionDiff();
		p3=new CovidEvolutionDiff();
	
	}
	
	@Test
	public void testNullAndNotNull() {
		assertNotNull(p1);
		assertNotNull(p2);
		assertNotNull(p3);
		assertNull(p4);
	}
	
	@Test
	public void testEqualities() {
		assertNotEquals(p1,p3);
	}
	
	
	
	
	
}
