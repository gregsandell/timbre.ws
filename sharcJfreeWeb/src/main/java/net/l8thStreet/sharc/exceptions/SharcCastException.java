package net.l8thStreet.sharc.exceptions;

import net.l8thStreet.sharc.SharcConstants;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 22, 2006
 * Time: 1:14:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcCastException extends SharcException {
  public SharcCastException(String containerName , String containerClass, String expectedClass) {
    super("The class variable '" + containerName + "' (" +
      containerClass + ") should have contained a class of type " +
      expectedClass + " but unexpectedly contained an incompatible object",
      SharcConstants.USER_SYSTEMERR_MSG);
  }
}
