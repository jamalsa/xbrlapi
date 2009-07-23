package org.xbrlapi.aspects;

import java.io.IOException;

import org.xbrlapi.Fragment;
import org.xbrlapi.utilities.XBRLException;

/**
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public abstract class BaseAspectValue implements AspectValue {

    private Aspect aspect;
    
    private Fragment fragment;

    /**
     * @param aspect The aspect with this value
     * @param fragment The fragment expressing this value
     * @throws XBRLException if either parameter is null.
     */
    public BaseAspectValue(Aspect aspect, Fragment fragment) throws XBRLException {
        super();
        setAspect(aspect);
        setFragment(fragment);
    }

    private void setAspect(Aspect aspect) throws XBRLException {
       if (aspect == null) throw new XBRLException("The aspect must not be null."); 
       this.aspect = aspect;
    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
     }

    /**
     * @see AspectValue#getAspect()
     */
    public Aspect getAspect() {
        return aspect;
    }

    /**
     * @see AspectValue#getFragment()
     */
    public Fragment getFragment() {
        return fragment;
    }

    /**
     * @see AspectValue#getId()
     */
    public String getId() throws XBRLException {
        return getAspect().getTransformer().getIdentifier(this);
    }
    
    /**
     * @see AspectValue#getLabel()
     */
    public String getLabel() throws XBRLException {
        return getAspect().getTransformer().getLabel(this);
    }

    /**
     * @see AspectValue#getParent()
     */
    public AspectValue getParent() throws XBRLException {
        return null;
    }    
 

    /**
     * Handles object serialization
     * @param out The input object stream used to store the serialization of the object.
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(aspect);
        out.writeObject(fragment);
   }
    
    /**
     * Handles object inflation.
     * @param in The input object stream used to access the object's serialization.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject( );
        aspect = (Aspect) in.readObject();
        fragment = (Fragment) in.readObject();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fragment == null) ? 0 : fragment.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseAspectValue other = (BaseAspectValue) obj;
        if (fragment == null) {
            if (other.fragment != null)
                return false;
        } else if (!fragment.equals(other.fragment))
            return false;
        return true;
    }
    
    
}
