package org.xbrlapi.data.bdbxml.examples.utilities;

import java.net.URI;
import java.util.List;

import org.xbrlapi.Fragment;

/**
 * Lists all documents that enable XBRL discovery of the document
 * with the specified URI.
 * Additional commandline arguments (optional ones marked with an *)
 * <ul>
 *  <li>-document [The URI of the document]</li>
 * </ul> 
 * These are in addition to those commandline arguments documented at
 * @link BaseUtilityExample
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class FindLinksToGivenDocument extends BaseUtilityExample {
    
    public FindLinksToGivenDocument(String[] args) {
        argumentDocumentation = addArgumentDocumentation();
        parseArguments(args);
        String message = setUp();
        if (message.equals("")) {
            try {
                URI uri = new URI(arguments.get("document"));
                                
                List<URI> referencingDocuments = store.getReferencingDocuments(uri);
                for (URI ref: referencingDocuments) {
                    System.out.println(ref + " references " + uri);
                }    
                
                List<Fragment> fragments = store.getReferencingFragments(uri);
                for (Fragment fragment: fragments) {
                    fragment.serialize();
                }
                
/*                List<Stub> stubs = store.getStubs();
                for (Stub stub: stubs) {
                    uri = stub.getResourceURI();
                    System.out.println("-------------------------");
                    System.out.println("Missing resource: " + uri);
                    fragments = store.getReferencingFragments(uri);
                    System.out.println("Fragments linking to the missing resource.");
                    for (Fragment fragment: fragments) {
                        fragment.serialize();
                    }
                    System.out.println("");
                }*/
                
                
            } catch (Exception e) {
                badUsage(e.getMessage());
            }
        } else {
            badUsage(message);
        }
        tearDown();
    }
    
    protected String addArgumentDocumentation() {
        String explanation = super.addArgumentDocumentation();
        explanation += "-document\t\tURI of the document to find links to.\n";
        return explanation;
    }    

    /**
     * @param args The array of commandline arguments.
     */
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        FindLinksToGivenDocument utility = new FindLinksToGivenDocument(args);
    }

    protected String setUp() {
        String message = super.setUp();
        if (!arguments.containsKey("document")) 
            message += "The document to be loaded is not specified.\n";
        return message;
    }

   
    

    
}
