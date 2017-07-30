package net.street18.sofa.audit;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

/**
 * Class representing a webapp user.  Currently these objects are created or
 * managed by class SSdarUserManager, but the SSdar app does not yet do anything with this data.
 * @author Greg Sandell, Roundarch Inc, 2007-01-02
 * @see net.street18.sofa.singleton.UserManager
 */
public class SofaUser {
  private static Logger LOGGER = Logger.getLogger(SofaUser.class);
  private String firstSessionId;
  private int bytesSpent;
  private List allSessions = new ArrayList();

  public SofaUser(String sessionId) {
    this.firstSessionId = sessionId;
    allSessions.add(sessionId);
    this.bytesSpent = 0;
  }

  public String getFirstSessionId() {
    return firstSessionId;
  }
  public void setFirstSessionId(String firstSessionId) {
    this.firstSessionId = firstSessionId;
  }
  public void addByteCost(int length) {
    bytesSpent += length;
    LOGGER.info("This user has just spent " + length + " bytes, for a total of " + bytesSpent);
  }
}
