package net.l8thStreet.sharc.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 22, 2006
 * Time: 1:52:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcEmptyArgumentException extends SharcException {
  public SharcEmptyArgumentException(String codeDesc , String userDesc) {
    super(codeDesc, userDesc);
  }

}
