package org.xbrlapi.aspects;

import org.xbrlapi.Fragment;
import org.xbrlapi.utilities.XBRLException;

/**
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class EntityIdentifierAspectValue extends BaseAspectValue {

    public EntityIdentifierAspectValue(Aspect aspect, Fragment fragment)
            throws XBRLException {
        super(aspect, fragment);
        if (! fragment.isa("org.xbrlapi.impl.EntityImpl")) {
            throw new XBRLException("Fragment does not match the type of the aspect value.");
        }
    }

}