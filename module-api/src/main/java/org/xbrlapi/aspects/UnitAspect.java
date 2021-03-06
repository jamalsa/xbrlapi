package org.xbrlapi.aspects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.xbrlapi.Fact;
import org.xbrlapi.Measure;
import org.xbrlapi.NumericItem;
import org.xbrlapi.Unit;
import org.xbrlapi.utilities.Constants;
import org.xbrlapi.utilities.XBRLException;

/**
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class UnitAspect extends BaseAspect implements Aspect {

    /**
     * 
     */
    private static final long serialVersionUID = 7580982188301896251L;
    public final static String TYPE = "unit";
    
    /**
     * @see Aspect#getType()
     */
    public String getType() {
        return TYPE;
    }

    private static final Logger logger = Logger.getLogger(UnitAspect.class);    

    /**
     * @param aspectModel The aspect model with this aspect.
     * @throws XBRLException.
     */
    public UnitAspect(AspectModel aspectModel) throws XBRLException {
        super(aspectModel);
        initialize();
    }
    
    protected void initialize() {
        this.setTransformer(new Transformer(this));
    }



    public class Transformer extends BaseAspectValueTransformer implements AspectValueTransformer {
        
        public Transformer(Aspect aspect) {
            super(aspect);
        }
        
        /**
         * @see AspectValueTransformer#getIdentifier(AspectValue)
         */
        public String getIdentifier(AspectValue value) throws XBRLException {

            if (hasMapId(value)) {
                return getMapId(value);
            }

            String id = "";

            Unit unit = value.<Unit>getFragment();
            if (unit != null) {
                List<Measure> numerators = unit.getResolvedNumeratorMeasures();
                if (numerators.size() == 1 && ! unit.hasDenominator() && numerators.get(0).getNamespace().equals(Constants.ISO4217))
                       id = "Currency: " + numerators.get(0).getLocalname();
                else if (numerators.size() == 1 && ! unit.hasDenominator() && numerators.get(0).getNamespace().equals(Constants.XBRL21Namespace) && numerators.get(0).getLocalname().equals("pure") )
                    id = "Pure number";
                else {
                    for (int i=0; i<numerators.size(); i++) {
                        Measure measure = numerators.get(i);
                        if (i == 0) id += measure.getNamespace() + "#" + measure.getLocalname();
                        else id += " x " + measure.getNamespace() + "#" + measure.getLocalname();
                    }
                    if (unit.hasDenominator()) {
                        id += " / (";
                        List<Measure> denominators = unit.getResolvedDenominatorMeasures();
                        for (int i=0; i<denominators.size(); i++) {
                            Measure measure = denominators.get(i);
                            if (i == 0) id += measure.getNamespace() + "#" + measure.getLocalname();
                            else id += " x " + measure.getNamespace() + "#" + measure.getLocalname();
                        }
                        id += ")";
                    }
                }
            }

            setMapId(value,id);
            return id;
        }
        
        /**
         * @see AspectValueTransformer#getLabel(AspectValue)
         */
        public String getLabel(AspectValue value) throws XBRLException {
            if (value.getFragment() == null) return null;
            return getIdentifier(value);
        }
        
    }    
    
    /**
     * @see org.xbrlapi.aspects.Aspect#getValue(org.xbrlapi.Fact)
     */
    @SuppressWarnings("unchecked")
    public UnitAspectValue getValue(Fact fact) throws XBRLException {
            Unit unit = this.<Unit>getFragment(fact);
            return new UnitAspectValue(this,unit);
    }
    
    /**
     * @see Aspect#getFragmentFromStore(Fact)
     */
    @SuppressWarnings("unchecked")
    public Unit getFragmentFromStore(Fact fact) throws XBRLException {
        if (! fact.isNumeric()) return null;
        NumericItem item = (NumericItem) fact;
        return item.getUnit();
    }    
    
    /**
     * @see Aspect#getKey(Fact)
     */
    public String getKey(Fact fact) throws XBRLException {
        Unit unit = getFragmentFromStore(fact);
        if (unit == null) return "";
        return unit.getURI().toString() + "#" + unit.getId();
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
