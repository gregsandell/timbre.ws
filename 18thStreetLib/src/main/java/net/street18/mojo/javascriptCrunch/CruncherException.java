package net.street18.mojo.javascriptCrunch;

/**
 * Created by IntelliJ IDEA.
 * User: sandelgb
 * Date: Aug 14, 2007
 * Time: 9:14:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class CruncherException extends Exception {
	public CruncherException(String msg) {
		super(msg);
	}
	public CruncherException(String msg, Exception e) {
		super(msg + " Root cause: " + e.getClass().getName() + ".  Message: " + e.getMessage());
	}
}
