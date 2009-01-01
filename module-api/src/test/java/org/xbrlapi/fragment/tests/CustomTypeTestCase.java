package org.xbrlapi.fragment.tests;

import org.xbrlapi.CustomType;
import org.xbrlapi.DOMLoadingTestCase;
import org.xbrlapi.FragmentList;

/**
 * Tests the implementation of the org.xbrlapi.CustomType interface.
 * Uses the DOM-based data store to ensure rapid testing.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

public class CustomTypeTestCase extends DOMLoadingTestCase {
	private final String STARTING_POINT = "test.data.custom.link.role";
	
	protected void setUp() throws Exception {
		super.setUp();
		loader.discover(this.getURI(STARTING_POINT));		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}	
	
	public CustomTypeTestCase(String arg0) {
		super(arg0);
	}

	/**
	 * Test getting the roleType definition.
	 */
	public void testGetCustomRoleTypeDefinition() {
        try {
            FragmentList<CustomType> fragments = store.<CustomType>getFragments("RoleType");
            assertTrue(fragments.getLength() > 0);
            for (CustomType fragment: fragments) {
                if (fragment.getCustomURI().equals("http://mycompany.com/xbrl/roleE/newExtendedRoleType")) {
                    assertEquals("Test variation for defining a new role on a presentationLink", fragment.getDefinition());
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}	

	

	/**
	 * Test getting the custom type ID.
	 */
	public void testGetCustomTypeId() {
        try {
            FragmentList<CustomType> fragments = store.<CustomType>getFragments("RoleType");
            assertTrue(fragments.getLength() > 0);
            for (CustomType fragment: fragments) {
                if (fragment.getCustomURI().equals("http://mycompany.com/xbrl/roleE/newExtendedRoleType")) {
                    assertEquals("newExtendedRoleType", fragment.getCustomTypeId());
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}		
	
	/**
	 * Test getting the list of used on fragments.
	 */
	public void testGetUsedOns() {

		try {
		    FragmentList<CustomType> types = store.<CustomType>getFragments("RoleType");
		    for (CustomType type: types) {
	            assertTrue(type.getUsedOns().getLength() > 0);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}	
	
	
}
