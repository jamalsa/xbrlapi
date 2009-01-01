package org.xbrlapi.data.bdbxml.tests;

import java.util.List;
/**
 * Tests of performance with larger data sets.
 * @author Geoffrey Shuetrim (geoff@galexy.net) 
 */
public class LoadPerformanceTestCase extends BaseTestCase {
	private final String STARTING_POINT = "real.data.large.schema";
	
	protected void setUp() throws Exception {
		super.setUp();
		loader.discover(getURI(STARTING_POINT));		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public LoadPerformanceTestCase(String arg0) {
		super(arg0);
	}

	/**
	 * Test creation of an reconnection to a large data store.
	 */
	public void testLargerStore() {
		try {
			
			List urls = store.getStoredURIs();
			assertEquals(22,urls.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
