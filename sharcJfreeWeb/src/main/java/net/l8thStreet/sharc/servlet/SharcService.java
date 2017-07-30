package net.l8thStreet.sharc.servlet;

import net.l8thStreet.sharc.*;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.Note;
import net.l8thStreet.sharc.xmlObject.NoteInf;
import net.l8thStreet.sharc.service.*;
import net.l8thStreet.sharc.audit.Session;
import net.l8thStreet.sharc.exceptions.*;
import net.l8thStreet.sharc.singleton.SessionManager;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;


public class SharcService extends SharcBaseServlet {
  private static Logger LOGGER = Logger.getLogger(SharcService.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher dispatcher = null;
      try {
          doService(request, response);
      }
        catch (SharcDocRequestException e) {
          dispatcher = request.getRequestDispatcher("/jsp/sharcDoc.jsp");
          dispatcher.forward(request, response);
        }
        catch (SharcException e) {
          LOGGER.error("SHARC EXCEPTION; see stack trace");
          e.printStackTrace();
          request.setAttribute(SharcConstants.KEY_SHARC_EXCEPTION, e);
          dispatcher = request.getRequestDispatcher("/jsp/sharcException.jsp");
          if (e.getClass().getName().equals("net.l8thStreet.sharc.exceptions.SharcImageException")) {
              /* this.ServiceFunctions.plotToBrowser() is the only process that will throw  SharcImageException.
               * It already begins writing the response with mime-type "img/png", so it's not possible
               * to forward to a jsp at this point.  The appropriate thing will be to return a standard
               * image that will explain the exception.  (That's a to-do)
              */
              LOGGER.error("Non-recoverable error in image processing.  Reason: " + e.getMessage());
          }
            else {
              if (dispatcher != null ) {
               dispatcher.forward(request, response);
             }
           }
        }
    }
    private void doService(HttpServletRequest request, HttpServletResponse response) throws SharcException {
      Session sObj = SessionManager.getInstance().get(request.getSession().getId());
      sObj.setIP((HttpServletRequest)request);

      this.scrubActionParameter(request);
	String action = StringUtils.defaultString(request.getParameter(SharcConstants.PARAM_ACTION), "");
      SharcServiceFactory serviceFactory = new SharcServiceFactory();
      ServiceInterface service = serviceFactory.getService(action);
      service.plot(request, response);

/*      
      if (action.equals(SharcConstants.ACTION_CENTROIDALLINSTRUMENTS))  {
        allInstrumentsCentroid();
      }
      if (action.equals("allXml")) {
        response.setContentType("text/xml");
        try {
          PrintWriter out = response.getWriter();
          out.print(sngltn.getXmlString(sngltn.getAllSharcFilePath()));
        } catch (IOException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
      }
      if (action.equals(SharcConstants.ACTION_XPATHXML)) {
        String inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
        String filePath = SharcUtils.getXmlFilePath(inst);
        String xmlString = sngltn.getXmlString(filePath);
        String xpath = request.getParameter(SharcConstants.PARAM_XPATH);
        String genericError = "An error occurred processing your request for instrument [" + inst +
                "], xpath query [" + xpath + "].  Cause unknown at this time.  Please " +
                "try again later.";
        try {
            XpathPruner xPruner = new XpathPruner(xpath, xmlString);
            if (xPruner.isEmptyResult())   {
              throw new SharcXmlException("Call to XpathPruner with xpath = [" + xpath +
                "], xml file = [" + filePath + " yielded an empty string",genericError);
            }
            PrintWriter out = response.getWriter();
            response.setContentType("text/xml");
            xPruner.addDeclaration();
            xPruner.enclosingElement("root");
            User user = UserManager.getInstance().get(request);
            user.addByteCost(xPruner.getXmlString().getBytes().length);
            out.print(xPruner.getXmlString());
        }
          catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new SharcException("An IOException occurred: " + e.getMessage(),
              genericError);
            }
          catch (l8thStreetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new SharcException("Call to XpathPruner with xpath = [" + xpath +
              "], xml file = [" + filePath + " produced this error: [" + e.getMessage() + "]" +
              ", cause message = [" + e.getCause().getMessage() + "]",
              genericError);
          }
      } */
    }

  /**
   *
   * @param inst
   * @return
   * @throws SharcException
   * @deprecated
   */
  private double allInstrumentCentroid(String inst) throws SharcException {
    SharcValidate.notNullArg(inst, "inst");
    InstrumentInf instrument = new Instrument(inst);
    List<Note> notes = instrument.getSharcNotes();
    CentroidAccumulator centroidAccumulator = new CentroidAccumulator();
    for (int i = 0; i < notes.size(); i++) {
      NoteInf note = notes.get(i);
      centroidAccumulator.add(note.getSharcHarmonics(), note.getFundHz());
    }
    return(centroidAccumulator.getCentroid());
  }

  /**
   *
   * @return
   * @throws SharcException
   * @deprecated
   */
  private Map allInstrumentsCentroidMap() throws SharcException {
    Map<String,Double> result = new HashMap<String,Double>();
    List instnames = SharcXmlSingleton.getInstance().getInstrumentList();
    for (int i = 0; i < instnames.size(); i++) {
      String inst = (String)instnames.get(i);
      if (inst.equals("sharc"))  {
        continue;
      }
      double d = allInstrumentCentroid(inst);
      LOGGER.info(inst + " = " + d);
      result.put(inst, new Double(d));
    }
    return(result);
  }

  /**
   *
   * @throws SharcException
   * @deprecated
   */
  private void allInstrumentsCentroid() throws SharcException {
    Map<String,Double> allCentroid = allInstrumentsCentroidMap();
  }

  public void scrubActionParameter(HttpServletRequest request) throws SharcException {
    SharcValidate.notNullArg(request, "request");
    SharcXmlSingleton s = SharcXmlSingleton.getInstance();
    /*  ACTION PARAM ALWAYS REQUIRED */
    if (isEmptyParam(request, SharcConstants.PARAM_ACTION)) {
      throw new SharcHttpQueryException(
        "Missing HTTP parameter '" + SharcConstants.PARAM_ACTION + "'",
        "The querystring must contain the parameter '" + SharcConstants.PARAM_ACTION +
          "'.  For example, " + request.getRequestURL() + "?" +  SharcConstants.PARAM_ACTION +
          "=" + SharcConstants.ACTION_XPATHXML);
    }
    String action = request.getParameter(SharcConstants.PARAM_ACTION);
    /* MUST BE A KNOWN ACTION */
    if (!ServiceFunctions.isKnownAction(action)) {
      throw new SharcHttpQueryException(
        "Unknown value '" + action + "' supplied for HTTP parameter '" + SharcConstants.PARAM_ACTION + "'",
        "The value '" + action + "' appeared for the Http Querystring " +
          "parameter '" + SharcConstants.PARAM_ACTION + "'  This is not a known action.  The allowed actions are " +
          SharcConstants.knownActions.toString());
    }
  }
}

