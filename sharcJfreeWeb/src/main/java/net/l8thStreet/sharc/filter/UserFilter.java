package net.l8thStreet.sharc.filter;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

import net.l8thStreet.sharc.audit.Session;
import net.l8thStreet.sharc.audit.User;
import net.l8thStreet.sharc.singleton.SessionManager;
import net.l8thStreet.sharc.singleton.UserManager;
import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcUtils;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 1, 2006
 * Time: 11:52:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserFilter  implements Filter{
  private static Logger LOGGER = Logger.getLogger(UserFilter.class);
  public void doFilter(ServletRequest req,
                       ServletResponse resp,
                       FilterChain chain)  throws ServletException, IOException {
    LOGGER.info("Filter invoked");
    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)resp;
    String firstSessionId = SharcUtils.getCookie(request, SharcConstants.COOKIE_FIRSTSESSION_PARAM);
    String sessionId = request.getSession().getId();
    UserManager uMan = UserManager.getInstance();
    User user = null;
    if (firstSessionId != null)   {
      LOGGER.info("Repeat visitor; firstSessionId found in cookie");
      user = uMan.get(firstSessionId);
      if (user == null) {
        LOGGER.error("Expected to find a user and didn't; probably an out of sync database");
      }
    }
    if (user == null) {
      LOGGER.info("Adding a new user with sessionId " + sessionId);
      user = uMan.add(sessionId);
      firstSessionId = sessionId;
      Cookie cookie = new Cookie(SharcConstants.COOKIE_FIRSTSESSION_PARAM, sessionId);
      cookie.setPath("/");
      cookie.setMaxAge(60 * 60 * 24 * 365);
      response.addCookie(cookie);
      LOGGER.info("Setting response to write a cookie named " + SharcConstants.COOKIE_FIRSTSESSION_PARAM +
        " with value " + sessionId);
    }
    UserManager.addSession(sessionId, firstSessionId);
    chain.doFilter(req,resp);
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
