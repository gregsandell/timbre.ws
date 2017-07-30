package net.street18.sofa;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import net.street18.sofa.singleton.UserManager;
import net.street18.sofa.singleton.SessionManager;
import net.street18.sofa.audit.SofaSession;

/**
 * A listener that fires actions when sessions are created or destroyed.  Currently
 * SSDar is keeping track of session lifecycles with the singletons
 * SSdarSessionManager and SSdarUserManager but nothing is being done with this data yet.
 * @author Greg Sandell, Roundarch Inc, 2007-01-02

 * @see net.street18.sofa.singleton.UserManager
 */
public class SessionListener implements HttpSessionListener {
  private static Logger LOGGER = Logger.getLogger(SessionListener.class);

   private static int activeSessions = 0;

   /* Session Creation Event */
   public void sessionCreated(HttpSessionEvent se)  {
     HttpSession session = se.getSession();
     LOGGER.info("New session! " + session.getId() + "; total sessions = " + activeSessions);
     activeSessions++;
   }

   /* Session Invalidation Event */
   public void sessionDestroyed(HttpSessionEvent se) {
     HttpSession session = se.getSession();
     SessionManager sMan = net.street18.sofa.singleton.SessionManager.getInstance();
     SofaSession sObj = sMan.get(session.getId());
     if (sObj == null) {
         LOGGER.error("Unexpected failed retrieval of an object of type " +
           SofaSession.class.getName() + "; for some reason our SSdarSessionManager does not know about " +
           " the Session that has just been destroyed");
     }
      else {
        sObj.setEnded(new Date());
     }
     UserManager.removeSession(session.getId());
     if(activeSessions > 0)
         activeSessions--;
     LOGGER.info("Session dying! " + session.getId() + "; total sessions = " + activeSessions);
   }

   public static int getActiveSessions() {
       return activeSessions;
   }
 }

