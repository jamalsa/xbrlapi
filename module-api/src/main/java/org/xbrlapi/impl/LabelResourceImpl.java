package org.xbrlapi.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xbrlapi.Concept;
import org.xbrlapi.Fragment;
import org.xbrlapi.LabelResource;
import org.xbrlapi.utilities.Constants;
import org.xbrlapi.utilities.XBRLException;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 * TODO Find out why XPointer metadata does not include the id for label resources.
 */

public class LabelResourceImpl extends MixedContentResourceImpl implements LabelResource {

	/**
	 * @see org.xbrlapi.LabelResource#getStringValue()
	 */
	public String getStringValue() throws XBRLException {
		String value = "";
		NodeList nodes = getContent();
		for (int i=0; i<nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.TEXT_NODE) value = value + " " + node.getTextContent().trim() + " ";
			if (node.getNodeType() == Node.ELEMENT_NODE) value = value + getStringValue((Element) node);			
		}
		
        Pattern pattern = Pattern.compile("\\s+");
        Matcher matcher = pattern.matcher(value);
        return matcher.replaceAll(" ").trim();
	}
	
	/**
	 * @param element The element at the top of the markup to be converted to a string.
	 * @return the string value from an element contained in the XHTML markup content,
	 * eliminating the markup itself.
	 * @throws XBRLException
	 */
	private String getStringValue(Element element) throws XBRLException {
		String value = "";
		NodeList nodes = element.getChildNodes();
		for (int i=0; i<nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.TEXT_NODE) value = value + node.getTextContent();
			else if (node.getNodeType() == Node.ELEMENT_NODE) value = value + getStringValue((Element) node);
		}
		return " " + value.trim() + " ";
	}	
	
    /**
     * @return the list of Concepts in the data store that have this label.
     * @throws XBRLException
     */
    public List<Concept> getConcepts() throws XBRLException	{

        List<Fragment> fragments;
        if (this.getNamespace().equals(Constants.GenericLabelNamespace()))
            fragments = getStore().<Fragment>getSources(getIndex(),null,Constants.GenericLabelArcrole());
        else 
            fragments = getStore().<Fragment>getSources(getIndex(),null,Constants.LabelArcrole());
        
        Set<Concept> concepts = new HashSet<Concept>();
        for (Fragment fragment: fragments) {
            if (fragment.isa("org.xbrlapi.impl.ConceptImpl")) concepts.add((Concept) fragment);
        }
        
        return new Vector<Concept>(concepts);

    }
	
}
