package net.l8thStreet.sharc.filter;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

import net.l8thStreet.sharc.audit.Session;
import net.l8thStreet.sharc.singleton.SessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 1, 2006
 * Time: 11:52:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionFilter  implements Filter{
  private static Logger LOGGER = Logger.getLogger(SessionFilter.class);
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain)  throws ServletException, IOException {
    LOGGER.info("Filter invoked");
    HttpSession session = ((HttpServletRequest)request).getSession(true);
    SessionManager sMan = SessionManager.getInstance();
    sMan.add(session.getId());
    Session sObj = sMan.get(session.getId());
    if (sObj == null) {
      throw new ServletException("Bizarre error, class of object type " + Session.class.getName() + " was null");
    }
    sObj.setBegan(new Date());
    LOGGER.info("Session id = " + session.getId());
    chain.doFilter(request,response);
    return;
  }
  /**Filter should be configured with an system error page.*/
  public void init (FilterConfig FilterConfig) throws ServletException {
    LOGGER.warn("The filter is being created");
  }
  public void destroy() {
    LOGGER.warn("The filter is being destroyed");
  }

}
