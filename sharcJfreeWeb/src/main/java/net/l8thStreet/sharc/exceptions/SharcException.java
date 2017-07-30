package net.l8thStreet.sharc.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 8, 2006
 * Time: 10:27:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SharcException extends Exception {
  public String userDesc;
  public String codeDesc;
  public SharcException(String codeDesc, String userDesc) {
    super(codeDesc);
    this.userDesc = userDesc;
    this.codeDesc = codeDesc;
  }
}
