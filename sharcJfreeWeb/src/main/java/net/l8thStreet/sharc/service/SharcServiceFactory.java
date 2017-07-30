package net.l8thStreet.sharc.service;

import net.l8thStreet.sharc.exceptions.SharcCodeErrorException;
import net.l8thStreet.sharc.SharcConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2007
 * Time: 1:10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcServiceFactory {
  private static Logger LOGGER = Logger.getLogger(SharcServiceFactory.class);
  public SharcServiceFactory() { }
  public ServiceInterface getService(String serviceName) throws SharcCodeErrorException {
    ServiceInterface service = null;
    if (!ServiceFunctions.isKnownAction(serviceName)) {
      throw new SharcCodeErrorException("Action '" + serviceName + "' is unknown");
    }
    try {
        String fullPath = SharcConstants.SERVICE_PACKAGE_PATH + serviceName;
        LOGGER.info("Going to instantiate " + fullPath);
        Class clazz = Class.forName(fullPath);
        Constructor ctor = clazz.getConstructor();
        service = (ServiceInterface)ctor.newInstance();
    }
      catch (ClassNotFoundException e) {
        throw new SharcCodeErrorException("Class '" + serviceName + "' could not be instantiated.  Root cause = " + e.getClass().getName() + ", message = " + e.getMessage());
      }
      catch (IllegalAccessException e) {
        throw new SharcCodeErrorException("Class '" + serviceName + "' could not be instantiated.  Root cause = " + e.getClass().getName() + ", message = " + e.getMessage());
      }
      catch (NoSuchMethodException e) {
        throw new SharcCodeErrorException("Class '" + serviceName + "' could not be instantiated.  Root cause = " + e.getClass().getName() + ", message = " + e.getMessage());
      }
      catch (InvocationTargetException e) {
        throw new SharcCodeErrorException("Class '" + serviceName + "' could not be instantiated.  Root cause = " + e.getClass().getName() + ", message = " + e.getMessage());
      }
      catch (InstantiationException e) {
        throw new SharcCodeErrorException("Class '" + serviceName + "' could not be instantiated.  Root cause = " + e.getClass().getName() + ", message = " + e.getMessage());
      }
    return(service);
  }
}
