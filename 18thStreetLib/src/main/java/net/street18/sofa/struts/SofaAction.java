package net.street18.sofa.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import net.street18.sofa.exception.SofaException;
import net.street18.sofa.singleton.SessionManager;
import net.street18.sofa.SofaConstants;

/**
 * A wrapper around the Struts Action class which makes it possible to intercept
 * every action call in the web app and do any desired preprocessing.  All Action classes in SSDAR should use
 * this class rather than the parent Struts Action class.  Currently SSDAR uses the
 * following opportunities in this wrapper:
 * <ol>
 * <li>Preventing an unauthenticated user from reaching any page that requires
 * authentication</li>
 * <li>Allowing a an action class to throw an custom SSDAR exception and have
 * it route appropriately to an exception-handling page</li>
 * </ol>
 */
public abstract class SofaAction extends Action
{
  private static Logger logger = Logger.getLogger(SofaAction.class.getName());
  /**
   * Delegate for the Struts Action.execute() method.  An abstract class that
   * must be defined.  Everything that would usually be done by Action.execute()
   * should be done in this method.  The signature is identical to Action.execute()
   * with the addition of a throwable SSdarException.
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
  * @throws java.io.IOException
   * @throws javax.servlet.ServletException
   */
  public abstract ActionForward performTask(
    ActionMapping mapping, ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws SofaException, IOException, ServletException;
  /**
   * Execute the action, intercepting any defined conditions and re-routing to
   * the appropriate page.
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws java.io.IOException
   * @throws javax.servlet.ServletException
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response
                               )
        throws IOException, ServletException {
    ActionForward af = null;
    // Check for required authentication and re-route if necessary
    String uri = request.getRequestURI();
    boolean isAuthenticated = SessionManager.isAuthenticated(request);
    // logger.info("Current session is " + (!isAuthenticated ? "not " : "") + "authenticated");
    // logger.info("URI [ " + uri + " can " + (!SSdarSessionManager.skipAuthentication(uri) ? "not " : "") + "skip authentication");
    if (!SessionManager.skipAuthentication(uri) && !isAuthenticated)  {
      af = mapping.findForward("notAuthenticated");
    }
    if (af == null) {
      // Authentication passed or is not necessary.  Process the request.
      try {
          af = performTask(mapping, form, request, response);
      }
        catch(SofaException e) {
          // Route to custom error-handling process
          request.setAttribute(SofaConstants.ERROR_GENERIC_KEY, e);
          af = mapping.findForward("ssdarException");
        }
    }
    return(af);
  }
}
