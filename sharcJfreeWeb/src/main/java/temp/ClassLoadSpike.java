package temp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.l8thStreet.sharc.service.ServiceInterface;
import net.l8thStreet.sharc.exceptions.SharcException;


public class ClassLoadSpike
{
    public static Iterator list(ClassLoader CL)  throws NoSuchFieldException, IllegalAccessException   {
        Class CL_class = CL.getClass();
        while (CL_class != java.lang.ClassLoader.class)   {
            CL_class = CL_class.getSuperclass();
        }
        java.lang.reflect.Field ClassLoader_classes_field = CL_class.getDeclaredField("classes");
        ClassLoader_classes_field.setAccessible(true);
        Vector classes = (Vector )ClassLoader_classes_field.get(CL);
        return classes.iterator();
    }
    public static void main (String args[])  throws Exception  {
        ClassLoader myCL = ClassLoadSpike.class.getClassLoader ();
        while (myCL != null)  {
            System.out.println("ClassLoader: " + myCL);
            for (Iterator iter = list(myCL); iter.hasNext(); )  {
                 System.out.println("\t" + iter.next());
            }
            myCL = myCL.getParent();
        }
    }
}