package net.l8thStreet.sharc.servlet;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.SharcValidate;
import net.l8thStreet.sharc.exceptions.SharcException;


/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 19, 2006
 * Time: 1:32:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcBaseServlet extends HttpServlet {
  public boolean isEmptyParam(HttpServletRequest request, String param)  {
    return(isEmpty(request.getParameter(param)));
  }
  public boolean isEmpty(String s)  {
    return(StringUtils.isEmpty(s));
  }
  public double toDouble(String d) throws SharcException
  {
    SharcValidate.notNullArg(d, "d");
    return(SharcUtils.toDouble(d));
  }

}
