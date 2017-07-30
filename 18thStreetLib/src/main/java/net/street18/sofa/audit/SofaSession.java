package net.street18.sofa.audit;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
  * Class representing a webapp user session.  Currently these objects are created or
  * managed by classes SSdarSessionFilter, SSdarSessionListener and
  * SSdarSessionManager, but the SSdar app does not yet do anything with this data.
  * @author Greg Sandell, Roundarch Inc, 2007-01-02
 * @see net.street18.sofa.singleton.UserManager
  * @see net.street18.sofa.SessionFilter
  */
public class SofaSession {
  private static Logger LOGGER = Logger.getLogger(SofaSession.class);
  private String sessionID;
  private Date began;
  private Date ended;
  private String IPnumber;

  public SofaSession(String sessionID) {
    this.sessionID = sessionID;
    began = null;
    ended = null;
    IPnumber = null;
  }
   /**
    * Records the IP number of the originating browser requiest.
    * @param request
    */
  public void setIP(HttpServletRequest request) {
    setIPnumber(request.getRemoteAddr());  
  }

  public String getSessionID() {
    return sessionID;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  public Date getBegan() {
    return began;
  }

  public void setBegan(Date began) {
    this.began = began;
  }

  public Date getEnded() {
    return ended;
  }

  public void setEnded(Date ended) {
    this.ended = ended;
  }

  public String getIPnumber() {
    return IPnumber;
  }

  public void setIPnumber(String IPnumber) {
    this.IPnumber = IPnumber;
  }
}
