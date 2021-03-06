package org.xbrlapi.xdt.aspects;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xbrlapi.Fact;
import org.xbrlapi.Item;
import org.xbrlapi.OpenContextComponent;
import org.xbrlapi.aspects.Aspect;
import org.xbrlapi.aspects.AspectModel;
import org.xbrlapi.aspects.AspectValue;
import org.xbrlapi.aspects.AspectValueTransformer;
import org.xbrlapi.aspects.BaseAspectValueTransformer;
import org.xbrlapi.utilities.XBRLException;
import org.xbrlapi.xdt.TypedDimension;
import org.xbrlapi.xdt.values.DimensionValue;

/**
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class TypedDimensionAspect extends DimensionAspect implements Aspect {

    /**
     * 
     */
    private static final long serialVersionUID = 279544715598540722L;
    private static final Logger logger = Logger.getLogger(TypedDimensionAspect.class);
    
    /**
     * @param aspectModel The aspect model with this aspect.
     * @param dimension The dimension defining this aspect.
     * @throws XBRLException.
     */
    public TypedDimensionAspect(AspectModel aspectModel, TypedDimension dimension) throws XBRLException {
        super(aspectModel, dimension);
        initialize();
    }
    
    protected void initialize() {
        setTransformer(new Transformer(this));        
    }

    public class Transformer extends BaseAspectValueTransformer implements AspectValueTransformer {

        public Transformer(Aspect aspect) {
            super(aspect);
        }
        
        /**
         * @see AspectValueTransformer#getIdentifier(AspectValue)
         */
        public String getIdentifier(AspectValue value) throws XBRLException {
            
            if (value.isMissing()) {
                setMapId(value,"");
                return "";
            }
            
            if (hasMapId(value)) {
                return getMapId(value);
            }

            TypedDimensionAspect aspect = (TypedDimensionAspect) value.getAspect();
            Element content = aspect.getAccessor().getTypedDimensionContentFromOpenContextComponent((OpenContextComponent) value.getFragment(),aspect.getDimension());
            NodeList nodes = content.getChildNodes();
            Element child = content;
            GETELEMENT: for (int i=0; i<nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    child = (Element) nodes.item(i);
                    break GETELEMENT;
                }
            }
            
            String id = getLabelFromElement(child);
            
            setMapId(value,id);
            return id;
        }
        
        String dimensionLabel = null;
        
        /**
         * @see AspectValueTransformer#getLabel(AspectValue)
         */
        public String getLabel(AspectValue value) throws XBRLException {

            return this.getIdentifier(value);

        }

    }    
    
    /**
     * @see org.xbrlapi.aspects.Aspect#getValue(org.xbrlapi.Fact)
     */
    @SuppressWarnings("unchecked")
    public TypedDimensionAspectValue getValue(Fact fact) throws XBRLException {
        OpenContextComponent occ = this.<OpenContextComponent>getFragment(fact);
        return new TypedDimensionAspectValue(this,occ);
    }

    /**
     * @see Aspect#getFragmentFromStore(Fact)
     */
    @SuppressWarnings("unchecked")
    public OpenContextComponent getFragmentFromStore(Fact fact) throws XBRLException {
        if (fact.isTuple()) return null;
        TypedDimension dimension = this.<TypedDimension>getDimension();
        DimensionValue value = getAccessor().getValue((Item) fact, dimension);
        if (value == null) return null; 
        return value.getOpenContextComponent();
    }
    
    /**
     * @see Aspect#getKey(Fact)
     */
    public String getKey(Fact fact) throws XBRLException {
        if (fact.isTuple()) return "";
        DimensionValue dimensionValue = getAccessor().getValue((Item) fact, getDimension());
        if (dimensionValue == null) return "";
        Element element = (Element) dimensionValue.getValue();
        return this.getLabelFromElement(element);
    }

    /**
     * Handles object inflation.
     * @param in The input object stream used to access the object's serialization.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject( );
        initialize();
    }
    
    /**
     * Handles object serialization
     * @param out The input object stream used to store the serialization of the object.
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }    
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
       return true;
    }
    
}


