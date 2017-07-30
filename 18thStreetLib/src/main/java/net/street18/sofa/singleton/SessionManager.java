package net.street18.sofa.singleton;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import net.street18.sofa.audit.SofaSession;
import net.street18.sofa.SofaConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Singleton that manages logins by SSDAR owners.  There are two main aspects
 * of sessions that are managed here:
 * <ol>
 * <li>A single point of contact for getting/setting the session-scoped OwnerID</li>
 * <li>A record of sessions created and destroyed by the servlet container.
 *      Currently this functionality is used only by SSdarSessionListener and
 *      SSdarSessionFilter with no actual impact on the app.</li>
 * </ol>
 * @author Greg Sandell, Roundarch, Inc. 2007-01-02
 */
public class SessionManager {
  private static Logger LOGGER = Logger.getLogger(SessionManager.class);
  /** A collection of SSdarSession objects representing active sessions in the app */
  protected Map sessionMap;
  private static SessionManager ourInstance;
  /**
   * Gets an instance of the singleton.  Lazy loads.
   * @return
   */
  public static SessionManager getInstance() {
    if (ourInstance == null) {
      ourInstance = new SessionManager();
    }
    return(ourInstance);
  }
  /**
   * Initializes the sessionMap class variable.
   */
  private SessionManager() {
    sessionMap = new HashMap();
    // ourInstance = new SessionManager();
    LOGGER.info("First instance of singleton SessionManager created");
  }
  /**
   * Adds a session to the sessionMap.  Insures that the same session is not added
   * twice, so there should be no concern about multiple calls to this.
   * @param sessionId  A unique key for the session; presumably session.getId()
   * @return  True if this is a new addition; if false, then this session is
   * already part of the collection and was not re-added.
   */
  public boolean add(String sessionId) {
    boolean isOld = sessionMap.containsKey(sessionId);
    if (!isOld) {
      sessionMap.put(sessionId, new SofaSession(sessionId));
    }
    return(!isOld);    // Returns true if the session is a new addition
  }
  /**
   * Get a SSdarSession object from the sessionMap instance variable.
   * @param id The unique key for the session; presumably session.getId()
   * @return
   */
  public SofaSession get(String id) {
    return((SofaSession)sessionMap.get(id));
  }

  /**
   * A user is authenticated if they have an ownerID stored in session scope.
   * @param request  Typically the calling class is a servlet action class that
   * is either processing a form/link or rendering a page.  The request object
   * gives access to the session.
   * @return
   */
  public static boolean isAuthenticated(HttpServletRequest request) {
    boolean result = true;  // add necessary criteria here
    return(result);
  }

  /**
   * Most pages and actions on the site require that a user be authenticated, as determined
   * by the session variable USER_ID_SESSION_KEY (see above) being set to
   * an ownerID.  However, pages/action that do the work of authentication are called
   * before this variable is set are excused.
   * @param uri  The URI to be evaluated (e.g. <code>/SSdar/existing.jsf</code>)
   * @return  True if this URI does not require authentication
   */
  public static boolean skipAuthentication(String uri)  {
    boolean result = false;
    for (int i = 0; !result && i < SofaConstants.EXCLUDE_FROM_AUTHENTICATION_URIS.length; i++) {
      result = uri.indexOf(SofaConstants.EXCLUDE_FROM_AUTHENTICATION_URIS[i]) != -1;
    }
    return(result);
  }
}
