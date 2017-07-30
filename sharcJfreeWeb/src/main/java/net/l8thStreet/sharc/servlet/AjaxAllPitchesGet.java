package net.l8thStreet.sharc.servlet;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcUtils;

public class AjaxAllPitchesGet extends SharcBaseServlet {
  private static Logger LOGGER = Logger.getLogger(AjaxAllPitchesGet.class);
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
      StringBuffer sb = new StringBuffer();
      sb.append("var foo = [");
      for (int i = 0; i < SharcUtils.allPitches.length; i++) {
        sb.append("'" + SharcUtils.allPitches[i] + "'");
        if (i < (SharcUtils.allPitches.length - 1)) {
          sb.append(",");
        }
      }
      sb.append("];");
      try {
        response.getWriter().println(sb.toString());
      } catch (IOException e) {
          throw new SharcException("IO error while writing all pitches",
            "IO error while writing  all pitches");
      }
    }
}
