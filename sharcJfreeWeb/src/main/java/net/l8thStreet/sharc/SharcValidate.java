package net.l8thStreet.sharc;

import org.apache.commons.lang.Validate;
import net.l8thStreet.sharc.exceptions.SharcEmptyArgumentException;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 22, 2006
 * Time: 1:47:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcValidate  {
  public static void notEmptyString(String s, String varName) throws SharcEmptyArgumentException {
    if (varName == null) varName = "noname";
    try {
        Validate.notEmpty(s, "");
    }
      catch (IllegalArgumentException e) {
        String ss = "Method argument '" + varName + "' (" + String.class.getName() + ") was unexpectedly null or empty";
        throw new SharcEmptyArgumentException(ss, SharcConstants.USER_SYSTEMERR_MSG);
      }
  }
  public static void notNullArg(Object o, String varName) throws SharcEmptyArgumentException {
      if (varName == null) varName = "noname";
    try {
        Validate.notNull(o, "");
    }
      catch (IllegalArgumentException e) {
        String s = "Method argument '" + varName + " was unexpectedly null";
        throw new SharcEmptyArgumentException(s, SharcConstants.USER_SYSTEMERR_MSG);
      }
  }
  public static void notNullClassVar(Object o, String varName) throws SharcEmptyArgumentException {
      if (varName == null) varName = "noname";
    try {
        Validate.notNull(o, varName);
    }
      catch (IllegalArgumentException e) {
        String s = "The class variable " + varName + " was unexpectedly null.";
        throw new SharcEmptyArgumentException(s, SharcConstants.USER_SYSTEMERR_MSG);
      }
  }
  public static void notNullLocalVar(Object o, String varName) throws SharcEmptyArgumentException {
    try {
        if (varName == null) varName = "noname";
        Validate.notNull(o, varName);
    }
      catch (IllegalArgumentException e) {
        String s = "The local variable '" + varName + " was unexpectedly null.";
        throw new SharcEmptyArgumentException(s, SharcConstants.USER_SYSTEMERR_MSG);
      }
  }
  public static void notNullArg(Object o, String varName, String extra) throws SharcEmptyArgumentException {
    if (varName == null) varName = "noname";
    if (extra == null) extra = "";
    try {
        Validate.notNull(o, "");
    }
      catch (IllegalArgumentException e) {
        String s = "Method argument '" + varName + "' was unexpectedly null. " + extra;
        throw new SharcEmptyArgumentException(s, SharcConstants.USER_SYSTEMERR_MSG);
      }
  }
  public static void notNullClassVar(Object o, String varName, String extra) throws SharcEmptyArgumentException {
      if (varName == null) varName = "noname";
      if (extra == null) extra = "";
    try {
        Validate.notNull(o, varName);
    }
      catch (IllegalArgumentException e) {
        String s = "The class variable '" + varName + "' was unexpectedly null. " + extra;
        throw new SharcEmptyArgumentException(s, SharcConstants.USER_SYSTEMERR_MSG);
      }
  }
  public static void notNullLocalVar(Object o, String varName, String extra) throws SharcEmptyArgumentException {
      if (varName == null) varName = "noname";
      if (extra == null) extra = "";
    try {
        Validate.notNull(o, "");
    }
      catch (IllegalArgumentException e) {
        String s = "The local variable '" + varName + "' was unexpectedly null. " + extra;
        throw new SharcEmptyArgumentException(s, SharcConstants.USER_SYSTEMERR_MSG);
      }
  }

}
