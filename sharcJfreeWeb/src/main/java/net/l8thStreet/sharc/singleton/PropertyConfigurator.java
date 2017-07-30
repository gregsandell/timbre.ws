/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Oct 23, 2003
 * Time: 8:31:31 PM
 * To change this template use Options | File Templates.
 */
package net.l8thStreet.sharc.singleton;

import org.apache.log4j.Logger;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Iterator;

import net.l8thStreet.sharc.SharcConstants;

public class PropertyConfigurator {
	private static PropertyConfigurator ourInstance = null;
	private static Logger LOGGER = Logger.getLogger(PropertyConfigurator.class.getName());
  private String sharcRootPath;
  /*
	private DataSource 	dataSource;
	private boolean 		cachingPreferred;
	private String 			databaseIP;
	private String 			databaseUsername;
	private String 			databasePassword;
	private String 			databaseDriverClassName;
	private String 			databaseSubprotocol;
	private int 				databasePort;
	private String 			databaseUrl;
	private String 			databaseName;
	private boolean 		authenticationRequired;
  */
	public synchronized static PropertyConfigurator getInstance() {
		if (ourInstance == null) {
			LOGGER.info("First instance of PropertyConfigurator created");
			ourInstance = new PropertyConfigurator();
		}
		return ourInstance;
	}
	private PropertyConfigurator() {
		readProperties();
    /*
		setDatabaseUrl(Database.buildDatabaseUrl(getDatabaseSubprotocol(), getDatabaseIP(),
			getDatabasePort(), getDatabaseName()));
		setDataSource(Database.buildDataSource(getDatabaseUrl(),
			getDatabaseDriverClassName(), getDatabaseUsername(), getDatabasePassword()));
      */
	}
	private void fakeProperties() {
    /*
		setCachingPreferred(false);
		setDatabaseIP("localhost");
		setDatabaseDriverClassName("org.gjt.mm.mysql.Driver");
		setDatabaseUsername("whyVIEW");
		setDatabasePassword("view5ql");
		setDatabaseName("whydah");
		setDatabaseSubprotocol("mysql");
		setDatabasePort(3306);
		setAuthenticationRequired(true);
    */
	}

	private void readProperties() {
		String xml = SharcConstants.SHARC_PROPERTIES_XML_FILE;
		ConfigurationFactory factory = new ConfigurationFactory();
		URL propertiesUrl = getClass().getClassLoader().getResource(xml);
		if (propertiesUrl == null) {
			LOGGER.error("Could not find properties file '" + xml + "'.  It should be located in /WEB-INF/classes.");
		}
		factory.setConfigurationURL(propertiesUrl);
		Configuration prop = null;
		try {
				prop = factory.getConfiguration();
		}
			catch (ConfigurationException e) {
				LOGGER.error("Could not the load properties file specified in " + xml);
				e.printStackTrace();
			}
    if (prop == null) {
      LOGGER.error("Configuration is null");
    }
        setSharcRootPath(prop.getString(SharcConstants.SHARC_ROOTPATH_PROP));
		/*try {
				try {

						setCachingPreferred(prop.getBoolean(Globals.CACHING_PREFERRED_PROP));
						setAuthenticationRequired(prop.getBoolean(Globals.REQUIRE_AUTHENTICATION_PROP));
						setDatabasePort(prop.getInt(Globals.DATABASE_PORT_PROP));

				}
					catch (ConversionException e) {
						LOGGER.error("A key in sharc configuration which requires either true or false had an invalid value");
						e.printStackTrace();
					}

				setDatabaseIP(prop.getString(Globals.DATABASE_IP_PROP));
				setDatabaseSubprotocol(prop.getString(Globals.DATABASE_SUBPROTOCOL_PROP));
				setDatabaseDriverClassName(prop.getString(Globals.DATABASE_DRIVER_PROP));
				setDatabaseUsername(prop.getString(Globals.DATABASE_USERNAME_PROP));
				setDatabasePassword(prop.getString(Globals.DATABASE_PASSWORD_PROP));
				setDatabaseName(prop.getString(Globals.DATABASE_NAME_PROP));

		}
			catch (NoSuchElementException e) {
				LOGGER.error("The sharc configuration file was missing one or more expected keys");
				e.printStackTrace();
			}        */
	}

  public String getSharcRootPath() {
    return sharcRootPath;
  }

  public void setSharcRootPath(String sharcRootPath) {
    this.sharcRootPath = sharcRootPath;
  }
  /*
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean isCachingPreferred() {
		return cachingPreferred;
	}

	public void setCachingPreferred(boolean cachingPreferred) {
		this.cachingPreferred = cachingPreferred;
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
  */
}

