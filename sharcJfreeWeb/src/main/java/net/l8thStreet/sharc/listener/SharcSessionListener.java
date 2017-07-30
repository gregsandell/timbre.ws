package net.l8thStreet.sharc.listener;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import net.l8thStreet.sharc.singleton.SessionManager;
import net.l8thStreet.sharc.singleton.UserManager;
import net.l8thStreet.sharc.audit.Session;

import java.util.Date;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 1, 2006
 * Time: 11:09:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcSessionListener implements HttpSessionListener {
  private static Logger LOGGER = Logger.getLogger(SharcSessionListener.class);

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
     SessionManager sMan = SessionManager.getInstance();
     Session sObj = sMan.get(session.getId());
     if (sObj == null) {
         LOGGER.error("Unexpected failed retrieval of an object of type " +
           Session.class.getName() + "; for some reason our SessionManager does not know about " +
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

