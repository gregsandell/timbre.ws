package net.l8thStreet.sharc.service;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.SharcChartUtils;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.NoteInf;
import net.l8thStreet.sharc.exceptions.*;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.street18.util.FileSystemUtils;
import net.street18.util.XpathPruner;
import net.street18.exception.l8thStreetException;
import net.street18.sofa.singleton.UserManager;
import net.street18.sofa.audit.SofaUser;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class XpathXml
    extends ServiceBaseAction implements ServiceInterface {
  private static Logger LOGGER = Logger.getLogger(XpathXml.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
	static {
		requiredParamList.add(SharcConstants.PARAM_INSTRUMENT);
		requiredParamList.add(SharcConstants.PARAM_XPATH);
	}
	public String getAction() {
		return(SharcConstants.ACTION_XPATHXML);
	}
	public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
        xpathWorker(request, response);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_XPATHXML).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_XPATHXML).append("' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_XPATH).append("</li>")
    .append("</ul>Sample URL:<br/>");
    String link = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_XPATHXML + "&" +
      SharcConstants.PARAM_INSTRUMENT + "=violin_vibrato&" +
      SharcConstants.PARAM_XPATH + "=/tree/instrument[@id='violin_vibrato']/note[@pitch='c4']");
    sb.append(link)
    .append("<h2>Instrument Requirements</h2>").append(ValidationMessages.acceptableInstrumentsText());
    return(sb.toString());
  }
  public void validateService(HttpServletRequest request) throws SharcException {
  /* INSTRUMENT AND PITCH REQUIRED */
    String inst, pitch, xpath;
    /* inst and pitch will already have been validated by SharcService.scrubParameters() */
    inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
    xpath =  request.getParameter(SharcConstants.PARAM_XPATH);
  if (!SharcXmlSingleton.getInstance().isInstrument(inst)) {
    throw new SharcHttpQueryException(
      "The instrument [" + inst + "] does not exist.",
      "The instrument [" + inst + "] does notexist. " +
        "The available instruments are " + SharcXmlSingleton.getInstance().getInstrumentList().toString()
    );
  }
  }
  private void xpathWorker(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    String inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
      String xpath = request.getParameter(SharcConstants.PARAM_XPATH);
    String genericError = "An error occurred processing your request for instrument [" + inst +
          "], xpath query [" + xpath + "].  Cause unknown at this time.  Please " +
          "try again later.";
    File myFile = null;
    try {
        String filePath = SharcUtils.getXmlFilePath(inst);
        myFile = new File(filePath);
        if (!FileSystemUtils.isReadable(myFile)) {
          throw new SharcFileException("File [" + myFile.getAbsolutePath() + "] could not be found", "Application failure due to missing data.");
        }

        String xmlString = FileSystemUtils.getStringFromFileObj(myFile);
        XpathPruner xPruner = new XpathPruner(xpath, xmlString);
        if (xPruner.isEmptyResult())   {
          throw new SharcXmlException("Call to XpathPruner with xpath = [" + xpath +
            "], xml file = [" + filePath + " yielded an empty string",genericError);
        }
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        xPruner.addDeclaration();
        xPruner.enclosingElement("root");
        // Lines below broken...something about Sofa...use the non-Sofa version of user?  TODO
        // SofaUser user = UserManager.getInstance().get(request);
        // user.addByteCost(xPruner.getXmlString().getBytes().length);
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
          "], xml file = [" + myFile.getAbsolutePath() + " produced this error: [" + e.getMessage() + "]" +
          ", cause message = [" + e.getCause().getMessage() + "]",
          genericError);
      }
  }

  public List<String> getRequiredParamList() {
    return requiredParamList;
  }
  public List<String> getAlternateParamList() {
    return alternateParamList;
  }
}