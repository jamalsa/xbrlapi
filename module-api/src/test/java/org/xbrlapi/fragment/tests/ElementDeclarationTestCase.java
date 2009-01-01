package org.xbrlapi.fragment.tests;

/**
 * Tests the implementation of the org.xbrlapi.ElementDeclaration interface.
 * Uses the DOM-based data store to ensure rapid testing.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

import java.net.URI;

import org.xbrlapi.Concept;
import org.xbrlapi.DOMLoadingTestCase;
import org.xbrlapi.ElementDeclaration;
import org.xbrlapi.FragmentList;
import org.xbrlapi.utilities.Constants;
import org.xbrlapi.utilities.XBRLException;

public class ElementDeclarationTestCase extends DOMLoadingTestCase {

	private final String STARTING_POINT = "test.data.multi.concept.schema";
	
	protected void setUp() throws Exception {
		super.setUp();
		loader.discover(this.getURI(STARTING_POINT));		
	}

	private String documentStart = 
		"<?xml version=\"1.0\"?>\n"
		+"<schema targetNamespace=\"http://www.xbrlapi.org/targetNamespace/\"\n"
		+  "xmlns:tns=\"http://www.xbrlapi.org/targetNamespace/\"\n"
		+  "xmlns=\"http://www.w3.org/2001/XMLSchema\"\n"
		+  "xmlns:xbrli=\"http://www.xbrl.org/2003/instance\"\n"
		+  "xmlns:link=\"http://www.xbrl.org/2003/linkbase\"\n"
		+  "xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" 
		+  "elementFormDefault=\"qualified\">\n"

		+	"<import namespace=\"http://www.xbrl.org/2003/instance\"\n" 
		+          "schemaLocation=\"http://www.xbrl.org/2003/xbrl-instance-2003-12-31.xsd\"/>\n"
		+	"<element  name=\"A\"\n" 
		+            "id=\"A\"\n"
		+            "type=\"xbrli:monetaryItemType\"\n"
		+            "substitutionGroup=\"xbrli:item\"\n";
		
	private String documentEnd = "xbrli:periodType=\"instant\"/>\n</schema>\n";

	protected void tearDown() throws Exception {
		super.tearDown();
	}	
	
	public ElementDeclarationTestCase(String arg0) {
		super(arg0);
	}

	/**
	 * Test retrieval of the value for the nillable attribute.
	 */
	public void testGetNillable() {		
		try {
			String xml = documentStart + documentEnd;
			URI uri = new URI("http://www.xbrlapi.org/default.xsd");
			
			loader.discover(uri,xml);
		
			FragmentList<ElementDeclaration> fragments = store.<ElementDeclaration>query("/" + Constants.XBRLAPIPrefix + ":" + "fragment[" + Constants.XBRLAPIPrefix + ":" + "data/xsd:element/@nillable='true']");
			for (int i=0; i< fragments.getLength(); i++) {
				ElementDeclaration fragment = fragments.getFragment(i);
				assertTrue(fragment.getFragmentIndex() + " is nillable", fragment.isNillable());				
			}
			fragments = store.<ElementDeclaration>query("/" + Constants.XBRLAPIPrefix + ":" + "fragment[" + Constants.XBRLAPIPrefix + ":" + "data/xsd:element/@nillable='false']");
			for (int i=0; i< fragments.getLength(); i++) {
				ElementDeclaration fragment = fragments.getFragment(i);
				assertFalse(fragment.getFragmentIndex() + " is not nillable", fragment.isNillable());				
			}

			fragments = store.<ElementDeclaration>query("/" + Constants.XBRLAPIPrefix + ":" + "fragment/" + Constants.XBRLAPIPrefix + ":" + "data/xsd:element[count(@nillable)=0]");
			for (int i=0; i< fragments.getLength(); i++) {
				ElementDeclaration fragment = fragments.getFragment(i);
				assertFalse(fragment.getFragmentIndex() + " is not nillable", fragment.isNillable());		
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Test retrieval of the value for the nillable attribute.
	 */
	public void testGetFormQualified() {		
		try {
			FragmentList<ElementDeclaration> fragments = store.<ElementDeclaration>getFragments("ElementDeclaration");
			for (int i=0; i< fragments.getLength(); i++) {
				ElementDeclaration fragment = fragments.getFragment(i);
				assertTrue(fragment.getFragmentIndex() + " is element form qualified", fragment.getSchema().isElementFormQualified());				
			}
		} catch (XBRLException e) {
			fail(e.getMessage());
		}
		
	}	
	
	
	/**
	 * Test retrieval of the value for the default attribute.
	 */
	public void testGetDefault() {		

		try {
			String xml = 
				documentStart +
				" default=\"12.4\" " + 
				documentEnd;
			URI uri = new URI("http://www.xbrlapi.org/default.xsd");
			loader.discover(uri,xml);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		
		// Missing default attribute
		try {
			FragmentList<ElementDeclaration> fragments = store.<ElementDeclaration>query("/" + Constants.XBRLAPIPrefix + ":" + "fragment/" + Constants.XBRLAPIPrefix + ":" + "data/xsd:element[count(@default)=0]");
			for (int i=0; i< fragments.getLength(); i++) {
				ElementDeclaration fragment = fragments.getFragment(i);
				assertNull(fragment.getFragmentIndex() + " has not default", fragment.getDefault());				
			}
		} catch (XBRLException e) {
			fail(e.getMessage());
		}

		// Available attribute
		try {
			FragmentList<ElementDeclaration> fragments = store.<ElementDeclaration>query("/" + Constants.XBRLAPIPrefix + ":" + "fragment/" + Constants.XBRLAPIPrefix + ":" + "data/xsd:element[count(@default)=1]");
			for (int i=0; i< fragments.getLength(); i++) {
				ElementDeclaration fragment = fragments.getFragment(i);
				assertNotNull(fragment.getFragmentIndex() + " has not default", fragment.getDefault());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Test retrieval of the value for the fixed attribute.
	 */
	public void testGetFixed() {		

		// Missing default attribute
		try {
			FragmentList<ElementDeclaration> fragments = store.<ElementDeclaration>getFragments("ElementDeclaration");
			ElementDeclaration fragment = fragments.getFragment(0);
			assertNull(fragment.getFixed());
		} catch (XBRLException e) {
			fail(e.getMessage());
		}

		// Available attribute
		try {
			String xml = 
				documentStart +
				" fixed=\"12.4\" " + 
				documentEnd;
			URI uri = new URI("http://www.xbrlapi.org/default.xsd");
			loader.discover(uri,xml);

			String query = "/*[" + Constants.XBRLAPIPrefix + ":" + "data/*/@fixed='12.4']";
			FragmentList<ElementDeclaration> fragments = store.<ElementDeclaration>query(query);
			ElementDeclaration fragment = fragments.getFragment(0);
			store.serialize(fragment.getMetadataRootElement());
			assertEquals("12.4", fragment.getFixed());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Test getting the information about the data type.
	 */
	public void testGetTypeInformation() {		
		try {
		    
            FragmentList<Concept> fragments = store.getFragments("Concept");
            assertTrue(fragments.getLength() > 0);
            for (Concept fragment: fragments) {

                String type = fragment.getDataRootElement().getAttribute("type");
                assertEquals(type, fragment.getTypeQName());

                String prefix = fragment.getPrefixFromQName(type);
                assertNotNull(prefix);
                assertEquals(Constants.XBRL21Prefix , fragment.getTypeNamespaceAlias());

                String namespace = fragment.getNamespaceFromQName(type,fragment.getDataRootElement());
                assertEquals(namespace, fragment.getTypeNamespace());
                
                String localname = fragment.getLocalnameFromQName(type);
                assertEquals(fragment.getTypeLocalname(), localname);
                
            }

			
		} catch (XBRLException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test getting the information about the substitution group.
	 */
	public void testGetSubstitutionGroupInformation() {		

	    
	    try {
	        
            FragmentList<Concept> fragments = store.getFragments("Concept");
            assertTrue(fragments.getLength() > 0);
            for (Concept fragment: fragments) {

                String sg = fragment.getDataRootElement().getAttribute("substitutionGroup");
                assertEquals(sg, fragment.getSubstitutionGroupQName());

                String prefix = fragment.getPrefixFromQName(sg);
                assertNotNull(prefix);
                assertEquals(Constants.XBRL21Prefix , fragment.getSubstitutionGroupNamespaceAlias());

                String namespace = fragment.getNamespaceFromQName(sg,fragment.getDataRootElement());
                assertEquals(namespace, fragment.getSubstitutionGroupNamespace());
                
                String localname = fragment.getLocalnameFromQName(sg);
                assertEquals(fragment.getSubstitutionGroupLocalname(), localname);
                
            }
			
		} catch (XBRLException e) {
			fail(e.getMessage());
		}
	}
		
    public void testDerterminationOfBeingATuple() {     

        try {
            FragmentList<Concept> fragments = store.getFragments("Concept");
            assertTrue(fragments.getLength() > 0);
            for (Concept fragment: fragments) {
                assertTrue(fragment.isTuple() || fragment.isItem());
            }
        } catch (XBRLException e) {
            fail(e.getMessage());
        }
		
		
	}	
	

}
