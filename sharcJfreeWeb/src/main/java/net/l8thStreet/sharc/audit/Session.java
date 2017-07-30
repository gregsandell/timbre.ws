package net.l8thStreet.sharc.audit;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 2, 2006
 * Time: 8:47:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Session {
  private static Logger LOGGER = Logger.getLogger(Session.class);
  private String sessionID;
  private Date began;
  private Date ended;
  private String IPnumber;

  public Session(String sessionID) {
    this.sessionID = sessionID;
    began = null;
    ended = null;
    IPnumber = null;
  }
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
