/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Oct 23, 2003
 * Time: 8:31:31 PM
 * To change this template use Options | File Templates.
 */
package net.street18.sofa.singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import net.street18.sofa.DbConfig;
import net.street18.sofa.exception.SofaException;
/**
 * A singleton that configures multiple databases for use and makes them available
 * as datasources.
 * @author Greg Sandell, Roundarch, Inc. 2007-01-02
 */
public class DbConfigManager {
	private static DbConfigManager ourInstance = null;
	private static Logger logger = Logger.getLogger(DbConfigManager.class.getName());
  /** Collection of DbConfig classes, keyed by the database names given in
   * a properties file (see /WEB-INF/classes).
   */
  private Map sqlCfgs;
  /**
   * Gets an instance of the singleton.  Instance is lazy-loaded.
   * @return
   */
	public synchronized static DbConfigManager getInstance() {
		if (ourInstance == null) {
			logger.info("Creating first instance of DbConfigManager...");
			ourInstance = new DbConfigManager();
      logger.info("...succeeded");
		}
		return ourInstance;
	}
  /** Constructor that initializes the sqlCfgs class variable. */
	private DbConfigManager() {
    sqlCfgs = new HashMap();
	}
  /**
   * Add a DbConfig class according to the supplied precursor.
   * @param precursor  Defines a precursor for variables in a properties file.
   * @throws net.street18.sofa.exception.SofaException
   */
  public void add(String precursor)  throws SofaException {
    sqlCfgs.put(precursor, new DbConfig(precursor));
  }
  /**
   * Retrieves a DbConfig from the sqlCfgs class variable with key precursor.
   * @param precursor  Key that retrieve this precursor from the Map.
   * @return
   */
  public DbConfig get(String precursor) {
    return((DbConfig)sqlCfgs.get(precursor));
  }
  /**
   *
   * @return  A set of all keys in the sqlCfgs Map class variable.
   */
  public Set getDbKeys() {
    return(sqlCfgs.keySet());
  }
}

