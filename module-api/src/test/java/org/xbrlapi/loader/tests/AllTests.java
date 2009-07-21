package org.xbrlapi.loader.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.xbrlapi.loader.tests");
		//$JUnit-BEGIN$
        suite.addTestSuite(AsyncLoaderImplTestCase.class);
        suite.addTestSuite(LoaderImplTestCase.class);
		suite.addTestSuite(LoaderSerializationTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
