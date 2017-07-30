package net.street18.sofa.singleton;

import java.net.URL;
import java.util.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;
import net.street18.sofa.exception.SofaException;
import net.street18.sofa.exception.SofaException;
import net.street18.sofa.SofaConstants;
/**
 * A singleton that manages the reading of properties from <code>.properties</code>
 * files in /WEB-INF/classes.  Uses conventions from the Apache commons
 * Configuration library.
 */
public class PropertyConfigurator {
	private static PropertyConfigurator ourInstance = null;
	private static Logger logger = Logger.getLogger(PropertyConfigurator.class.getName());
  /** A Map of properties representing the key-value pairs of the
   *  <code>.properties</code> files in /WEB-INF/classes
   */
  private static Map propertiesMap;
  private Configuration cfg;
  /** Filename of  the master file used by Commons Configruation to decide
   * which of multiple possible properties files will be used.  This file
   * is manipulated by the project's ANT script.  */
  private static final String propFileName = SofaConstants.SOFA_PROPERTIES_XML_FILE;
  /**
   * Get instance of the singleton.  Lazy loads.
   * @return
   */
	public synchronized static PropertyConfigurator getInstance() {
		if (ourInstance == null) {
			logger.info("Creating first instance of PropertyConfigurator...");
      ourInstance = new PropertyConfigurator();
      logger.info("succeeded");
     }
		return ourInstance;
	}
  /**
   * Get the value of a property from the propertiesMap instance variable.
   * @param key The key for the property
   * @return
   */
  public String get(String key) {
    return((String)propertiesMap.get(key));
  }
  /**
   * Construct the private instance of the singleton.  Fills the propertiesMap
   * instance variable with all the key values from the chosen
   * <code>.properties</code> file in /WEB-INF/classes.
   */
	private PropertyConfigurator() {
    propertiesMap = new HashMap();
    try {
      cfg = retrievePropCfg();
      Iterator it = cfg.getKeys();
      while (it.hasNext()) {
        String key = (String)it.next();
        String value = cfg.getString(key);
        //logger.info("Retrieved property [" + key + "], value [" + value + "]");
        propertiesMap.put(key, value);
      }
    }
       catch (SofaException e)   {
          // The first instance is created at system startup time by a
          // listener (see SSdarStartupListener).  The listener can't
          // catch exceptions, so dump the stacktrace to log rather
          // than letting an exception be thrown.
         logger.error(e.codeDesc);
         e.printStackTrace();
       }
	}
/**
 * Determines the correct properties file to read from and returns
 * a Apache commons Configuration class.
 * @return
 * @throws net.street18.sofa.exception.SofaException
 */
  private Configuration retrievePropCfg() throws SofaException {
    logger.info("Going to retrieve properties cfg file " + propFileName);
    Class clazz = getClass();
    ClassLoader classLoader = clazz.getClassLoader();
    URL propertiesUrl = classLoader.getResource(propFileName);
    if (propertiesUrl == null) {
      throw new SofaException("Could not find properties file '" + propFileName + "'.  It should be located in /WEB-INF/classes.");
    }
  ConfigurationFactory factory = null;
  ConfigurationFactory.class.getClass();
  try {
    factory = new ConfigurationFactory(propFileName);
  }
    catch (Exception e) {
      logger.error("Error creating ConfigurationFactory(); exception type = " + e.getClass().getName() +
        ", reason = " + e.getMessage());
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  if (factory == null) {
      throw new SofaException("Could not create a Configuration factory for properties cfg file '" + propFileName + "'.");
    }
    // factory.setConfigurationURL(propertiesUrl);
    Configuration prop = null;
    try {
        prop = factory.getConfiguration();
    }
      catch (ConfigurationException e) {
        e.printStackTrace();
        throw new SofaException("Could not load the properties configuration specified in '" + propFileName +
            "'.  Check to make sure that is well-formed xml, and the properties files it " +
            "references actually exist.");
      }
      catch (UnhandledException ee)  {
        logger.error("some other exception");
        ee.printStackTrace();
      }
    return(prop);

  }

  public String getPropFileName() {
    return propFileName;
  }
  public List getPropertyList(String key)  {
    return(cfg.getList(key));
  }

}

