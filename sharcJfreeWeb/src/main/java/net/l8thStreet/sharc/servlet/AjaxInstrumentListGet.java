package net.l8thStreet.sharc.servlet;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.l8thStreet.sharc.exceptions.SharcDocRequestException;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcValidate;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.CentroidAccumulator;
import net.l8thStreet.sharc.service.SharcServiceFactory;
import net.l8thStreet.sharc.service.ServiceInterface;
import net.l8thStreet.sharc.service.ServiceFunctions;
import net.l8thStreet.sharc.singleton.SessionManager;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.audit.Session;

public class AjaxInstrumentListGet extends SharcBaseServlet {
  private static Logger LOGGER = Logger.getLogger(AjaxInstrumentListGet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher dispatcher = null;
      try {
          doService(request, response);
      }
        catch (SharcException e) {
          LOGGER.error("SHARC EXCEPTION; see stack trace");
          e.printStackTrace();
        }
    }
    private void doService(HttpServletRequest request, HttpServletResponse response) throws SharcException {
      // Session sObj = SessionManager.getInstance().get(request.getSession().getId());
      // sObj.setIP((HttpServletRequest)request);
      String pitch = (String)request.getParameter(SharcConstants.PARAM_PITCH);
      List<String> instnames = SharcXmlSingleton.getInstance().getInstrumentList(false);
      List<String> result = instnames;
      if (StringUtils.isNotEmpty(pitch)) {
        result = new ArrayList<String>();
        for (String inst:instnames) {
          if (SharcXmlSingleton.getInstance().instHasNote(inst, pitch))  {
            result.add(inst);
          }
        }
      }
      try {
        response.getWriter().println("var foo = {'instlist': [");
        for (int i = 0; i < result.size(); i++) {
          String inst = result.get(i);
          if (inst.equals("sharc")) {
            continue;
          }
          response.getWriter().print("{'short': '" + inst + "', 'long': '" + SharcUtils.getFullNameFromInstName(inst) + "'}");
          if (i < (result.size() - 1)) {
            response.getWriter().println(", ");
          }
        }
        response.getWriter().println("]};");
      } catch (IOException e) {
          throw new SharcException("IO error while writing instrument names","IO error while writing instrument names");
      }
  }
}
