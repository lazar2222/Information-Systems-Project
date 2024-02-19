package InteropClasses;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Objects;

public abstract class InteropClass implements Serializable{
 
    public void toInteropClass(Object o)
    {
        Class dst = this.getClass();
        Class src = o.getClass();
        Field[] dstFields=dst.getDeclaredFields();
        Field[] srcFields=src.getDeclaredFields();
        
        for (Field f : dstFields) {
            Field eq = findEqField(f,srcFields);
            if(eq!=null)
            {
                f.setAccessible(true);
                eq.setAccessible(true);
                if(equateTypes(f,this,eq,o))
                {    
                    try {       
                        f.set(this, eq.get(o));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        Object srcObj = eq.get(o);
                        if(srcObj!=null){
                            Field[] srcObjF=srcObj.getClass().getDeclaredFields();
                            for (Field srcObjf : srcObjF) {
                                Annotation[] annotations= srcObjf.getAnnotations();
                                for (Annotation annotation : annotations) {
                                    if(annotation.toString().startsWith("@javax.persistence.Id"))
                                    {
                                        srcObjf.setAccessible(true);
                                        f.set(this, srcObjf.get(srcObj));
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else
            {
                System.out.println("No Field "+f.getName()+" in source.");
            }
        }
        
    }
    
    public void fromInteropClass(Object o)
    {
        Class dst = this.getClass();
        Class src = o.getClass();
        Field[] dstFields=dst.getDeclaredFields();
        Field[] srcFields=src.getDeclaredFields();
        
        for (Field f : dstFields) {
            Field eq = findEqField(f,srcFields);
            if(eq!=null)
            {
                f.setAccessible(true);
                eq.setAccessible(true);
                if(equateTypes(f,this,eq,o))
                {    
                    try {       
                        eq.set(o, f.get(this));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        if(f.get(this)!=null){
                            Class eqType=eq.getType();
                            Constructor def = eqType.getConstructor();
                            Object srcObj=def.newInstance();

                            Field[] srcObjF=srcObj.getClass().getDeclaredFields();
                            for (Field srcObjf : srcObjF) {
                                Annotation[] annotations= srcObjf.getAnnotations();
                                for (Annotation annotation : annotations) {
                                    if(annotation.toString().startsWith("@javax.persistence.Id"))
                                    {
                                        srcObjf.setAccessible(true);
                                        srcObjf.set(srcObj, f.get(this));
                                        eq.set(o,srcObj);
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else
            {
                System.out.println("No Field "+f.getName()+" in source.");
            }
        }
        
    }
    
    public boolean memberWiseCompare(Object o)
    {
        Class c = this.getClass();
        Field[] dstFields=c.getDeclaredFields();
        for (Field f : dstFields) {
            f.setAccessible(true);
            try {
                if(!Objects.equals(f.get(this),f.get(o)))
                {
                    return false;
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
        return true;
    }
    
    private Field findEqField(Field f,Field[] srcFields)
    {
        for (Field field : srcFields) {
            if(field.getName().equals(f.getName()))
            {
                return field;
            }
        }
        return null;
    }
    
    private boolean equateTypes(Field a,Object oa,Field b,Object ob)
    {
        Class ta=a.getType();
        Class tb=b.getType();
        if(ta.isPrimitive())
        {
            try {
                a.setAccessible(true);
                ta=a.get(oa).getClass();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(tb.isPrimitive())
        {
            try {
                b.setAccessible(true);
                tb=b.get(ob).getClass();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ta.equals(tb);
    }
    
    public abstract int numcol();
    public abstract int[] colsize();
    public abstract String[] colnames();
    public abstract String[] colvals();
    
}
