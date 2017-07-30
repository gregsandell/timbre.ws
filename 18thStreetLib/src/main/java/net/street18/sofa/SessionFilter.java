package net.street18.sofa;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

import net.street18.sofa.singleton.SessionManager;
import net.street18.sofa.audit.SofaSession;

/**
 * Created by IntelliJ IDEA.
 * SSdarUser: gsandell
 * Date: Sep 1, 2006
 * Time: 11:52:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionFilter  implements Filter{
  private static Logger LOGGER = Logger.getLogger(SessionFilter.class);
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain)  throws ServletException, IOException {
    HttpSession session;
    HttpServletRequest hReq = (HttpServletRequest)request;
    //LOGGER.info("Filter invoked, page = [" + hReq.getRequestURI() + "]");
    session = hReq.getSession(false);
    if (session == null)  {
          session = ((HttpServletRequest)request).getSession(true);
          LOGGER.info("Creating new session [" + session.getId() + "]");
    }
      else {
        //LOGGER.info("Session already exists [" + session.getId() + "]");
      }
    SessionManager sMan = SessionManager.getInstance();
    sMan.add(session.getId());
   SofaSession sObj = sMan.get(session.getId());
    if (sObj == null) {
      throw new ServletException("Bizarre error, class of object type " + SofaSession.class.getName() + " was null");
    }
    sObj.setBegan(new Date());
    chain.doFilter(request,response);
    return;
  }
  /**Filter should be configured with an system error page.*/
  public void init (FilterConfig FilterConfig) throws ServletException {
    LOGGER.info("The filter is being created");
  }
  public void destroy() {
    LOGGER.info("The filter is being destroyed");
  }

}
