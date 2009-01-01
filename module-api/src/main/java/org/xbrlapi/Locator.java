package org.xbrlapi;

import java.net.URI;

import org.xbrlapi.utilities.XBRLException;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public interface Locator extends ArcEnd {

	/**
	 * Set metadata about the target of the locator.
	 * This includes, the absolute URI, the target document URI and the XPointer value
	 * used to identify part of the target document.
	 * @throws XBRLException
	 */
	public void setTarget(URI uri) throws XBRLException;	
	
    /**
     * Get the raw xlink:href attribute value (before any resolution).
     * @return the value of the xlink:href attribute on the locator.
     * @throws XBRLException if the attribute is missing.
     */
    public String getHref() throws XBRLException;
    

    
    /**
     * Get the single fragment that this locator references.
     * @return the single fragment referenced by the locator or none if none
     * is found.
     * @throws XBRLException
     */
    public Fragment getTargetFragment() throws XBRLException;
    
    /**
     * Get the absolute value of the HREF to the metadata.
     * @return The absolute URI specified by the locator HREF attribute.
     * @throws XBRLException.
     */
    public URI getAbsoluteHref() throws XBRLException;   
    
    /**
     * @return the document URI for the target fragment.
     * @throws XBRLException.
     */
    public URI getTargetDocumentURI() throws XBRLException;    
    
    /**
     * @return the string value of the XPointer element scheme expression from the xlink:href attribute.
     * @throws XBRLException.
     */
    public String getTargetPointerValue() throws XBRLException;    
}