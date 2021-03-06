package org.xbrlapi.impl;

import org.xbrlapi.NonNumericItem;
import org.xbrlapi.utilities.XBRLException;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

public class NonNumericItemImpl extends ItemImpl implements NonNumericItem {

	/**
     * 
     */
    private static final long serialVersionUID = -679088686747723914L;

    /** 
	 * Get the value of the fact.
	 * @return the value of fact with leading and trailing spaces deleted or null 
	 * if the fact is nill.
	 * @throws XBRLException
	 * @see org.xbrlapi.NonNumericItem#getValue()
	 */
	public String getValue() throws XBRLException {
		if (this.isNil()) return null;
		return getDataRootElement().getTextContent().trim();
	}
	

	
}
