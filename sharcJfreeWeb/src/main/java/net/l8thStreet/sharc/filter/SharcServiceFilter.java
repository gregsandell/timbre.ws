package net.l8thStreet.sharc.filter;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Sep 1, 2006
 * Time: 11:52:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcServiceFilter  implements Filter{
  private static Logger LOGGER = Logger.getLogger(SharcServiceFilter.class);
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain)  throws ServletException, IOException {
    HttpSession session = ((HttpServletRequest)request).getSession(true);
    LOGGER.info("Filter invoked");
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
