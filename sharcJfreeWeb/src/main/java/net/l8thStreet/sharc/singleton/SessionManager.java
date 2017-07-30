package net.l8thStreet.sharc.singleton;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;

import net.l8thStreet.sharc.audit.Session;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 2, 2006
 * Time: 8:38:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionManager {
  private static Logger LOGGER = Logger.getLogger(SessionManager.class);
  private Map sessionMap;
  private static SessionManager ourInstance = new SessionManager();
  
  private SessionManager() {
    sessionMap = new HashMap();
    // ourInstance = new SessionManager();
    LOGGER.info("First instance of singleton UserManager created");
  }
  public boolean add(String sessionId) {
    boolean isOld = sessionMap.containsKey(sessionId);
    if (!isOld) {
      sessionMap.put(sessionId, new Session(sessionId));
    }
    return(!isOld);    // Returns true if the session is a new addition
  }
  public static SessionManager getInstance() {
    return(ourInstance);    
  }

  public Session get(String id) {
    return((Session)sessionMap.get(id));
  }
}
