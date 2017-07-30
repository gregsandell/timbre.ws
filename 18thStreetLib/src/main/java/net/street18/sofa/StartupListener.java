package net.street18.sofa;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import net.street18.sofa.singleton.DbConfigManager;
import net.street18.sofa.singleton.PropertyConfigurator;
import net.street18.sofa.exception.SofaException;
/**
 * A class that will be called when SSDar first starts, prior to any page requests.
 * This makes it possible to load various helpers and singleton classes instead of
 * lazy-loading them the first time a page request requires them.  The same listener
 * offers the opportunity to clean up when the application is shutdown (see
 * closeDataSources()).
 * @author Greg Sandell, Roundarch Inc, 2007-01-02
 * @see net.street18.sofa.singleton.DbConfigManager
 * @see net.street18.sofa.singleton.SessionManager
 * @see net.street18.sofa.singleton.PropertyConfigurator
 */
public class StartupListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(StartupListener.class.getName());
  private DbConfigManager sqlMan;
  private PropertyConfigurator propMan;
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Context is initialized");
    propMan = PropertyConfigurator.getInstance();
    sqlMan = DbConfigManager.getInstance();
    List databases = propMan.getPropertyList(SofaConstants.DATABASE_LOAD_LIST_KEY);
    for (int i = 0; i < databases.size(); i++) {
      try {
          sqlMan.add((String)databases.get(i));    // add a database
      }
        catch (SofaException e) {
          logger.info("Failure adding database [" + (String)databases.get(i) + "]");
          e.printStackTrace();
        }
    }
    logger.info("End of contextInitialized");
	}
	public void contextDestroyed(ServletContextEvent sce) {
    logger.info("contextDestroyed() called");
    if (propMan.get("GLOBAL.useDatabase").equals("true"))   {
      closeDataSources();
    }
    logger.info("Context is destroyed");
	}
  /**
   * Close the datasources that were opened at startup.
   */
  private void closeDataSources() {
    Set dbKeys = sqlMan.getDbKeys();
    Iterator it = dbKeys.iterator();
    BasicDataSource ds;
    while (it.hasNext()) {
      ds = sqlMan.get((String)it.next()).getDataSource();
      try {
          ds.close();
          logger.info("Closed datasource for " + ds.getUrl());
      }
        catch (SQLException e) {
          // Listener cannot catch exceptions, have to deal with it now.
          logger.error("Could not close data source for " + ds.getUrl());
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
  }
}
