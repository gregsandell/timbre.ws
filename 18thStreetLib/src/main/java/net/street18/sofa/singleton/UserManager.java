package net.street18.sofa.singleton;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import net.street18.sofa.audit.SofaUser;
import net.street18.sofa.audit.SofaUser;


/**
 * A record of users creating session objects in the app.
 *      Currently this functionality is used only by SSdarSessionListener and
 *      SSdarSessionFilter with no actual impact on the app.
 * @author Greg Sandell, Roundarch Inc., 2007-01-02
 */
public class UserManager {
  private static Logger LOGGER = Logger.getLogger(UserManager.class);
  private static Map userMap;
  private static Map sessionMap;
  private static UserManager ourInstance = new UserManager();

  private UserManager() {
    userMap = new HashMap();
    sessionMap = new HashMap();
    LOGGER.info("First instance of singleton UserManager created");
  }
  public static UserManager getInstance() {
    return(ourInstance);
  }
  public SofaUser add(String sessionId) {
    SofaUser user = new SofaUser(sessionId);
    userMap.put(sessionId, user);
    //addSession()
    return user;
  }
  public SofaUser get(String firstSessionId) {
    return((SofaUser)userMap.get(firstSessionId));
  }
  public static SofaUser get(HttpServletRequest request) {
    String sessionId = (String)sessionMap.get(request.getSession().getId());
    return((SofaUser)userMap.get(sessionId));
  }
  public static void addSession(String thisSessionId, String storedSessionId) {
    sessionMap.put(thisSessionId, storedSessionId);
  }
  public static void removeSession(String thisSessionId) {
    sessionMap.remove(thisSessionId);
  }
}
