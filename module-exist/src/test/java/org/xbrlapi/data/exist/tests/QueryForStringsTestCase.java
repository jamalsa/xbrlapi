package org.xbrlapi.data.exist.tests;

import java.util.Set;

/**
 * Tests the query for strings method.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

public class QueryForStringsTestCase extends BaseTestCase {
	private final String START = "test.data.small.schema";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}	
	
	public QueryForStringsTestCase(String arg0) {
		super(arg0);
	}
	
	public void testQueryForFragmentIndices() {
		try {
	        loader.discover(this.getURI(START));
		    
	        String query = "#roots#";
	        Set<String> results = store.queryForIndices(query);
	        assertTrue(results.size() > 1);
/*	        for (String result: results) {
	            logger.info(result);
	            if (result.length() > 10) {
	                store.serialize(store.getXMLResource(result));
	            }
	        }*/
		} catch (Exception e) {
		    e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
