package net.l8thStreet.sharc.servlet;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 18, 2006
 * Time: 4:10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class EchoList extends ArrayList<String> {
  /**
   * Adds an item to the List and returns the value that was added (assumed to be a String).
   * @param s
   * @return
   */
  public String addIt(String s) {
    this.add(s);
    return(s);
  }

}
