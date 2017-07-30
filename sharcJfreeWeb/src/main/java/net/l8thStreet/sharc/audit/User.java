package net.l8thStreet.sharc.audit;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 3, 2006
 * Time: 10:10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class User {
  private static Logger LOGGER = Logger.getLogger(User.class);
  private String firstSessionId;
  private int bytesSpent;
  private List<String> allSessions = new ArrayList<String>();

  public User(String sessionId) {
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
