package org.xbrlapi.data;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.xbrlapi.ArcroleType;
import org.xbrlapi.Concept;
import org.xbrlapi.Fact;
import org.xbrlapi.Fragment;
import org.xbrlapi.FragmentList;
import org.xbrlapi.Item;
import org.xbrlapi.RoleType;
import org.xbrlapi.Tuple;
import org.xbrlapi.utilities.XBRLException;

/**
 * The XBRL store interface extends the base data store to define 
 * a range of utility methods that provide XBRL related functionality 
 * to the data store.
 * 
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

public interface XBRLStore extends Store {

    /**
     * @return a list of all of the root-level facts in the data store (those facts
     * that are children of the root element of an XBRL instance).  Returns an empty list 
     * if no facts are found.
     * @throws XBRLException
     */
    public FragmentList<Fact> getFacts() throws XBRLException;
    
    /**
     * @return a list of all of the items in the data store.
     * @throws XBRLException
     */
    public FragmentList<Item> getItems() throws XBRLException;
    
    /**
     * @return a list of all of the tuples in the data store.
     * @throws XBRLException
     */
    public FragmentList<Tuple> getTuples() throws XBRLException;

    /**
     * @param url The URL of the document to get the facts from.
     * @return a list of all of the root-level facts in the specified document.
     * @throws XBRLException
     */
    public FragmentList<Fact> getFacts(URL url) throws XBRLException;
    
    /**
     * @param url The URL of the document to get the items from.
     * @return a list of all of the root-level items in the data store.
     * @throws XBRLException
     */
    public FragmentList<Item> getItems(URL url) throws XBRLException;
    
    /**
     * @param url The URL of the document to get the facts from.
     * @return a list of all of the root-level tuples in the specified document.
     * @throws XBRLException
     */
    public FragmentList<Tuple> getTuples(URL url) throws XBRLException;
    
    /**
     * @param linkNamespace The namespace of the link element.
     * @param linkName The name of the link element.
     * @param linkRole the role on the extended links that contain the network arcs.
     * @param arcNamespace The namespace of the arc element.
     * @param arcName The name of the arc element.
     * @param arcRole the arcrole on the arcs describing the network.
     * @return The list of fragments for each of the resources that is identified as a root
     * of the specified network (noting that a root resource is defined as a resource that is
     * at the source of one or more relationships in the network and that is not at the target 
     * of any relationships in the network).
     * @throws XBRLException
     */
    public FragmentList<Fragment> getNetworkRoots(String linkNamespace, String linkName, String linkRole, String arcNamespace, String arcName, String arcRole) throws XBRLException;
   
    /**
     * @param namespace The namespace for the concept.
     * @param name The local name for the concept.
     * @return the concept fragment for the specified namespace and name.
     * @throws XBRLException if more than one matching concept is found in the data store
     * or if no matching concepts are found in the data store.
     */
    public Concept getConcept(String namespace, String name) throws XBRLException;

    /**
     * @return a hash map indexed by link roles that are used in extended links in the data store.
     * @throws XBRLException
     */
    public HashMap<String,String> getLinkRoles() throws XBRLException;
    
    /**
     * @param arcrole The arcrole determining the extended links that are to be examined for
     * linkroles that are used on links containing arcs with the required arcrole.
     * @return a hashmap of link roles, with one entry for each link role that is used on an
     * extended link that contains an arc with the required arcrole.
     * @throws XBRLException
     */
    public HashMap<String,String> getLinkRoles(String arcrole) throws XBRLException;
    
    /**
     * @return a hash map indexed by arc roles that are used in extended links in the data store.
     * @throws XBRLException
     */
    public HashMap<String,String> getArcRoles() throws XBRLException;
    
    /**
     * @return a list of arcroleType fragments
     * @throws XBRLException
     */
    public FragmentList<ArcroleType> getArcroleTypes() throws XBRLException;
    
    /**
     * @return a list of arcroleType fragments with a given arcrole
     * @throws XBRLException
     */
    public FragmentList<ArcroleType> getArcroleTypes(String uri) throws XBRLException;
    
    /**
     * @return a list of roleType fragments
     * @throws XBRLException
     */
    public FragmentList<RoleType> getRoleTypes() throws XBRLException;
    

    
    /**
     * @return a list of RoleType fragments with a given role
     * @throws XBRLException
     */
    public FragmentList<RoleType> getRoleTypes(String uri) throws XBRLException;    
    
    /**
     * @return a hash map indexed by resource roles that are used in extended links in the data store.
     * @throws XBRLException
     */
    public HashMap<String,String> getResourceRoles() throws XBRLException;    
    
    /**
     * @param starters The list of URLs of the documents to use as 
     * starting points for analysis.
     * @return list of URLs for the documents in the data store
     * that are referenced, directly or indirectly, by any of the documents
     * identified by the supplied list of document URLs.  Each entry in the list is a String.
     * @throws XBRLException if some of the referenced documents are not in
     * the data store.
     */
    public List<String> getMinimumDocumentSet(List<String> starters) throws XBRLException;
    
    
    /**
     * This is just a convenience method.
     * @param url The single document URL to use as 
     * starting points for analysis.
     * @return list of URLs for the documents in the data store
     * that are referenced, directly or indirectly, by the document
     * identified by the supplied URL.  Each entry in the list is a String.
     * @throws XBRLException if some of the referenced documents are not in
     * the data store.
     */
    public List<String> getMinimumDocumentSet(String url) throws XBRLException;
    
}
