package net.l8thStreet.sharc.listener;

import org.apache.log4j.Logger;
/*
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConversionException;
import org.iesabroad.common.data.mysql.Mysql;
*/
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import net.l8thStreet.sharc.singleton.PropertyConfigurator;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.singleton.UserManager;
import net.l8thStreet.sharc.singleton.SessionManager;
public class SharcStartupListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(SharcStartupListener.class.getName());
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Context is initialized");
		PropertyConfigurator.getInstance();
    SharcXmlSingleton.getInstance().init();
    UserManager.getInstance();
    SessionManager.getInstance();
	}
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Context is destroyed");
	}
}
