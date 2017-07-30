package net.l8thStreet.sharc.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 8, 2006
 * Time: 10:30:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class SharcHttpQueryException extends SharcException {
  public SharcHttpQueryException(String codeDesc, String userDesc) {
    super(codeDesc, userDesc);
  }
  public SharcHttpQueryException(String oneDesc) {
    super(oneDesc, oneDesc);
  }
}
