package net.l8thStreet.sharc.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 8, 2006
 * Time: 10:29:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class SharcCodeErrorException extends SharcException {
  public SharcCodeErrorException(String codeDesc) {
    super(codeDesc, codeDesc);
  }
}
