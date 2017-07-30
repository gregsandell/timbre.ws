/**
 * Constants used throughout the application.  Apart from a few constants
 * defined in class DbContants, this is the only place in the app where
 * Java constants are defined.  Categories of constants that are defined here
 * include:
 * <ul>
 * <li>Names of properties files</li>
 * <li>Database names defined in properties files</li>
 * <li>CSS parameters</li>
 * <li>Names keys used to store session- and request-scoped variables</li>
 * <li>Error messages</li>
 * <li>IDs of &lt;div&gt; tags used by multiple JSPs, CSS and Javascript files</li>
 * </ul>
 */
package net.street18.sofa;
public class SofaConstants {
  /** File used by Apache Commons Configuration library to manage properties.
   * See class PropertyConfigurator.
   */
  public static final String SOFA_PROPERTIES_XML_FILE = "sofa.properties.xml";
  /** Refers to a database named in a properties file (see /WEB-INF/classes)
   * and which matches the name of a configured database.
   */
   public static final String DATABASE_LOAD_LIST_KEY = "loaddatabase";
  /** Key used to store a request-scoped variable that will hold an exception.
   * See class SSdarAction and JSP file SSdarException.jsp.
   */
  public static final String ERROR_GENERIC_KEY = "error.ssdar.generic";
  /**
   * The key to the session variable storing the OwnerID of the currently
   * logged in user.  Whether this variable is null or specifies an
   * owner plays an important role in determining authentication state.
   * See class SSdarSessionManager.
   */
  public static final String USER_ID_SESSION_KEY = "OWNER_ID";
  /**
   * Following a logoff, we save the name of the last OwnerID in a
   * session variable.  Currently this is only for the purpose of
   * displaying a message saying "Owner xxxxx has logged off".
   */

  /** Used by timeoutClock.jsp and timeout.jsp */
  public static final String TIMER_CLOCK_DIV_NAME = "clock";
  /** Used by timeoutClock.jsp and timeout.jsp */
  public static final String TIMER_CLOCK_CONTAINER_DIV_NAME = "clockContainer";
  /** Maximum number of characters for an abbreviated extract description appearing
   * on the Existing Extracts page (see URI /existing.jsf).
   */
  public static final String DB_ERROR_USER_FRIENDLY =
    "Sorry, a database error is preventing this page from loading.  Please contact the system administrator.";
  /**
   * The user-friendly message that an exception should use for a configuration error.
   */
  public static final String CFG_ERROR_USER_FRIENDLY =
    "Sorry, a application configuration error is preventing this page from loading.  Please contact the system administrator.";

  // The URIs (e.g. /tempLogin.jsf) that can be excused from authentication.
  // See SSdarSessionManager.skipAuthentication(uri)
  public static final String[] EXCLUDE_FROM_AUTHENTICATION_URIS =
      {"sofaException", "tempLogin", "tempLoginSubmit"};
}


