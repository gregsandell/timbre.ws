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
import java.util.ArrayList;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Note;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;

public class AjaxPitchListGet extends SharcBaseServlet {
  private static Logger LOGGER = Logger.getLogger(AjaxPitchListGet.class);
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
      String inst = (String)request.getParameter(SharcConstants.PARAM_INSTRUMENT);
      InstrumentInf instrument = new Instrument(inst);
      List<Note> notes = instrument.getSharcNotes();
      StringBuffer sb = new StringBuffer();
      sb.append("var foo = [");
      for (int i = 0; i < notes.size(); i++) {
        sb.append("'" + notes.get(i).getPitch() + "'");
        if (i < (notes.size() - 1)) {
          sb.append(",");
        }
      }
      sb.append("];");
      try {
        response.getWriter().println(sb.toString());
      } catch (IOException e) {
          throw new SharcException("IO error while writing pitches for instrument '" + instrument + "'",
            "IO error while writing pitches for instrument '" + instrument + "'");
      }
    }
}
