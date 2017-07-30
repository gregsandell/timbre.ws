package net.l8thStreet.sharc.singleton;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import net.l8thStreet.sharc.audit.User;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.SharcConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 2, 2006
 * Time: 8:38:31 PM
 * To change this template use File | Settings | File Templates.
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

  public User add(String sessionId) {
    User user = new User(sessionId);
    userMap.put(sessionId, user);
    //addSession()
    return user;  //To change body of created methods use File | Settings | File Templates.
  }

  public User get(String firstSessionId) {
    return((User)userMap.get(firstSessionId));
  }
  public static User get(HttpServletRequest request) {
    String sessionId = (String)sessionMap.get(request.getSession().getId());
    return((User)userMap.get(sessionId));
  }
  public static void addSession(String thisSessionId, String cookiedSessionId) {
    sessionMap.put(thisSessionId, cookiedSessionId);
  }
  public static void removeSession(String thisSessionId) {
    sessionMap.remove(thisSessionId);
  }
}
