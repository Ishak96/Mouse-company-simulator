package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import engine.GridBuilder;
import engine.GridParameters;

/**
 * 
 * This is an automated test.
 *
 */
public class GridBuilderTestCase{
	
	GridBuilder buildGrid;
	
	@Before
	public void prepareGridBuilder() {
		GridParameters parameters = GridParameters.getInstance();
		
		buildGrid = new GridBuilder(parameters);
	}

	@Test
	public void testGridBuilder() {
		assertEquals(buildGrid.getGrid().dimension,21);
	}
	
	@Test
	public void testGridFood() {
		assertEquals(buildGrid.getGrid().getFood().size(),25);
	}
	
	@Test
	public void testGridObstacl() {
		assertEquals(buildGrid.getGrid().getObstacle().size(),25);
	}
	
	@Test
	public void testGridMouse() {
		assertEquals(buildGrid.getGrid().getMouses().size(),10);
	}
}
