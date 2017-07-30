package net.street18.sofa;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import net.street18.sofa.singleton.PropertyConfigurator;
import net.street18.sofa.exception.SofaException;

/**
 * Class representing the configuration for a single database.  A List of
 * these objects is managed by class DbConfigManager.
 * @author Greg Sandell, Roundarch Inc.
 * 2007-01-02
*/
public class DbConfig {
	private static Logger logger = Logger.getLogger(DbConfig.class.getName());
  /** Class from the Apache commons dbcp library. */
	private BasicDataSource 	dataSource;
	private String 			databaseIP;
	private String 			databaseUsername;
	private String 			databasePassword;
	private String 			databaseDriverClassName;
	private String 			databaseSubprotocol;
	private int 				databasePort;
	private String 			databaseUrl;
	private String 			databaseName;
  /** Not currently used by SSDAR, but could be used to control whether a user
   * needs to log in or not (for development purposes).
   */
	private boolean 		authenticationRequired;
  /** Defines a precursor for variables in a properties file. */
  private String      precursor;
  /** Local instance of singleton for managing properties. */
  private PropertyConfigurator cfg;

  /**
   * Class constructor
   * @param precursor  The name of a database as configured in a properties file.  See /WEB-INF/classes.
   * @throws net.street18.sofa.exception.SofaException
   */
	public DbConfig(String precursor) throws SofaException {
    cfg = PropertyConfigurator.getInstance();
    if (cfg == null) {
      throw new SofaException(
        "No PropertyConfigurator instance available.  Should have been created with SSdarStartupListener.");
    }
    setPrecursor(precursor);
    //propCfg = retrievePropCfg();
		readProperties();
    try {
        Class.forName(this.getDatabaseDriverClassName());
    } catch (ClassNotFoundException e) {
        throw new SofaException("The class name '" + getDatabaseDriverClassName() +
          "' could not be loaded as a class.  Check to see that the database driver " +
          "jar file is in the class path (/WEB-INF/lib, typically), or check for " +
          "an error in the properties files");
      }
    setDatabaseUrl(buildDatabaseUrl(getDatabaseSubprotocol(), getDatabaseIP(),
			getDatabasePort(), getDatabaseName()));
		setDataSource(buildDataSource(getDatabaseUrl(),
			getDatabaseDriverClassName(), getDatabaseUsername(), getDatabasePassword()));
    logger.info("Testing database connection for url [" + getDatabaseUrl() + "]...");
    BasicDataSource ds = this.getDataSource();
    if (ds == null) {
      throw new SofaException("Cannot perform db test for url [" + getDatabaseUrl() + "] because DataSource object is null");
    }
    // Test the connection.
    Connection mysqlConn = null;
    try {
        mysqlConn = ds.getConnection();
        if (mysqlConn == null) {
          throw new SofaException("Cannot perform db test for url [" + getDatabaseUrl() + "] because Connection object is null");
        }
    }
      catch (SQLException e) {
        throw new SofaException("Could not create for url [" + getDatabaseUrl() + "] a connection object");
      }
      finally {
        mysqlConn = null;
    }
    logger.info("...succeeded.");
	}
  /**
   * A developer convenience for testing a database without having to define
   * a properties file.
   */
	private void fakeProperties() {
		setDatabaseIP("localhost");
		setDatabaseDriverClassName("org.gjt.mm.mysql.Driver");
    // Note, these values don't apply to SSDAR, you'll need to change them
		setDatabaseUsername("whyVIEW");
		setDatabasePassword("view5ql");
		setDatabaseName("whydah");
		setDatabaseSubprotocol("mysql");
		setDatabasePort(3306);
		setAuthenticationRequired(true);
	}
  /**
   * Read database-related properties from .properties files (see /WEB-INF/classes)
   * and set this object's class variables to their values.
   * @throws net.street18.sofa.exception.SofaException
   */
	private void readProperties() throws SofaException {
    setDatabaseIP(getProp(DbConstants.DATABASE_IP_PROP));
    setDatabaseSubprotocol(getProp(DbConstants.DATABASE_SUBPROTOCOL_PROP));
    setDatabaseDriverClassName(getProp(DbConstants.DATABASE_DRIVER_PROP));
    setDatabaseUsername(getProp(DbConstants.DATABASE_USERNAME_PROP));
    setDatabasePassword(getProp(DbConstants.DATABASE_PASSWORD_PROP));
    setDatabaseName(getProp(DbConstants.DATABASE_NAME_PROP));
    try {
        // Properties with constraints (i.e. must be an integer, float, etc.
        // should go here.
        setDatabasePort(Integer.parseInt(getProp(DbConstants.DATABASE_PORT_PROP)));
    }
      catch (NumberFormatException e) {
        throw new SofaException(
          "The value for key " + DbConstants.DATABASE_PORT_PROP + " in the configuration was not an integer as expected");
      }
	}
  /**
   * Retrieve a specific property from a .properties file by name.  See the
   * .properties files in /WEB-INF/classes.  This method retrieves only
   * properties that begin either with "GLOBAL." or "&lt;precursor&gt;." where &lt;precursor&gt;
   * is the class variable defined in the constructor.  Properties beginning with
   * "GLOBAL." have precedence over same-named properties beginning with
   * "&lt;precursor&gt;.".  For example, if a properties file contains:
   * <blockquote><code><pre>
      GLOBAL.databaseUsername=root
      GLOBAL.databasePassword=admin
      db1.databaseName=ssdarweb
      db2.databaseName=world
   * </pre></code></blockquote>
   * <br/>...then calls to <code>getProp("databaseUsername")</code> and <code>getProp("databasePassword")</code>
   * will return the same value no matter what the precursor is.  On the other hand,
   * <code>getProp("databaseName")</code> will return "ssdarweb" when the precursor
   * is set to "db1" and "world" when the precursor is set to "db2".
   * @param propName  Name of a property preceded either by "GLOBAL." or "&lt;precursor&gt;."
   * @return  Value of the property
   * @throws net.street18.sofa.exception.SofaException
   */
  private String getProp(String propName) throws SofaException {
    String result = "";
    String globalProp = "GLOBAL." + propName;
    String localProp = precursor + "." + propName;
    result = cfg.get(globalProp);
    if (result == null) {
      result = cfg.get(localProp);
    }
    if (result == null) {
      throw new SofaException("Property [" + propName + "] not configured in " + cfg.getPropFileName() +
            ". Tried both " + globalProp + " and " + localProp);
    }
    return(result);
  }
  /**
   * Builds a database URL string, e.g. <code>jdbc:oracle:thin:@daylabsun10:1521:FMS6</code>.
   * It will construct the string according either to mysql conventions (the default),
   * or, if the string "mysql" (case insensitive) is not contained in the subprotocol parameter,
   * oracle conventions.
   * @param subprotocol   e.g. "oracle", "mysql"
   * @param IP            e.g. "daylabsun" or "localhost" or "158.187.20.1"
   * @param port          e.g. 1521
   * @param name          The database name (for mysql) or database instance (oracle)
   * @return
   */
  public static String buildDatabaseUrl(String subprotocol, String IP, int port, String name) {
    // Assume mysql
    String url = "jdbc:" + subprotocol + "://" + IP + ":" + port + "/" + name + "?autoReconnect=true";
    // Otherwise oracle
    if (subprotocol.toLowerCase().indexOf("mysql") == -1) {
      url = "jdbc:" + subprotocol + ":@" + IP + ":" + port + ":" + name;
    }
    return(url);
  }
  /**
   * Constructs a BasicDataSource (class from the Apache commons dbcp library).
   * @param connectURI
   * @param driver
   * @param username
   * @param password
   * @return
   */
  public static BasicDataSource buildDataSource(String connectURI, String driver,
                                           String username, String password) {
    BasicDataSource ds = new BasicDataSource();
    ds.setUrl(connectURI);
    ds.setDriverClassName(driver);
    ds.setUsername(username);
    ds.setPassword(password);
    return ds;
  }
	public BasicDataSource getDataSource()  {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getDatabaseIP() {
		return databaseIP;
	}

	public void setDatabaseIP(String databaseIP) {
		this.databaseIP = databaseIP;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getDatabaseDriverClassName() {
		return databaseDriverClassName;
	}

	public void setDatabaseDriverClassName(String databaseDriverClassName) {
		this.databaseDriverClassName = databaseDriverClassName;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public void setAuthenticationRequired(boolean authenticationRequired) {
		this.authenticationRequired = authenticationRequired;
	}

	public String getDatabaseSubprotocol() {
		return databaseSubprotocol;
	}

	public void setDatabaseSubprotocol(String databaseSubprotocol) {
		this.databaseSubprotocol = databaseSubprotocol;
	}

	public int getDatabasePort() {
		return databasePort;
	}

	public void setDatabasePort(int databasePort) {
		this.databasePort = databasePort;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}


  public String getPrecursor() {
    return precursor;
  }

  public void setPrecursor(String precursor) {
    this.precursor = precursor;
  }
}

