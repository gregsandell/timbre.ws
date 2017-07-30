package net.l8thStreet.sharc.service;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.StandardEntityCollection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.NoteInf;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;

import java.util.List;
import java.util.ArrayList;

public class TwoNoteplot extends ServiceBaseAction implements ServiceInterface {
  private static Logger LOGGER = Logger.getLogger(TwoNoteplot.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
  static {
    requiredParamList.add(SharcConstants.PARAM_INSTRUMENT1);
    requiredParamList.add(SharcConstants.PARAM_INSTRUMENT2);
    requiredParamList.add(SharcConstants.PARAM_PITCH);
  }
  public String getAction() {
    return(SharcConstants.ACTION_TWONOTEPLOT);
  }
  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    String inst1 = request.getParameter(SharcConstants.PARAM_INSTRUMENT1);
    String inst2 = request.getParameter(SharcConstants.PARAM_INSTRUMENT2);
    String pitch = request.getParameter(SharcConstants.PARAM_PITCH);
    InstrumentInf instrument = new Instrument(inst1);
    String fullInstName1 = instrument.getFullName();
    NoteInf note = instrument.getNote(pitch);
    XYSeriesCollection dataset = new XYSeriesCollection();
    XYSeries series1 = ServiceFunctions.makeSharcNoteXYseries(inst1, note);
    dataset.addSeries(series1);
    instrument = new Instrument(inst2);
    String fullInstName2 = instrument.getFullName();
    note = instrument.getNote(pitch);
    XYSeries series2 = ServiceFunctions.makeSharcNoteXYseries(inst2, note);
    dataset.addSeries(series2);
    JFreeChart chart = SharcUtils.standardSharcChart(fullInstName1 + " and " + fullInstName2, pitch, dataset);

    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
    ServiceFunctions.plotToBrowser(request, response, chart, info);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_TWONOTEPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_TWONOTEPLOT + "' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT1).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT2).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_PITCH).append("</li>")
    .append("</ul>Sample URL:<br/>");
    String url = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_TWONOTEPLOT + "&" +
      SharcConstants.PARAM_INSTRUMENT1 + "=violin_vibrato&" +
      SharcConstants.PARAM_INSTRUMENT2 + "=flute_vibrato&" +
      SharcConstants.PARAM_PITCH + "=c4");
    sb.append(url).append("<h2>Instrument Requirements</h2>").append(ValidationMessages.acceptableInstrumentsText())
    .append("<h2>Pitch Requirements</h2>").append(ValidationMessages.acceptablePitchesText())
    .append("<h2>Available Pitches</h2>Each instrument has its own available range of pitches.")
		.append(ValidationMessages.availablePitchesText());
    return(sb.toString());


  }
  public void validateService(HttpServletRequest request) throws SharcException {
	  String inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT1);
	  String pitch = request.getParameter(SharcConstants.PARAM_PITCH);
	  /* INSTRUMENT MUST HAVE THAT PITCH IN RANGE   */
	  if (!SharcXmlSingleton.getInstance().instHasNote(inst, pitch)) {
			String err = "Error in Url for service '" + SharcConstants.ACTION_TWONOTEPLOT + "': " +
					"The instrument [" + inst + "] does not contain pitch [" + pitch + "]. " +
					"Requested URL: " + ServiceFunctions.getRequestedUrl(request) + ". Documentation on this service: " +
					ServiceFunctions.getDocUrl(request, SharcConstants.ACTION_TWONOTEPLOT);
	    throw new SharcHttpQueryException(err,err);
	  }
	  inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT2);
	  /* INSTRUMENT MUST HAVE THAT PITCH IN RANGE   */
	  if (!SharcXmlSingleton.getInstance().instHasNote(inst, pitch)) {
			String err = "Error in Url for service '" + SharcConstants.ACTION_TWONOTEPLOT + "': " +
					"The instrument [" + inst + "] does not contain pitch [" + pitch + "]. " +
					"Requested URL: " + ServiceFunctions.getRequestedUrl(request) + ". Documentation on this service: " +
					ServiceFunctions.getDocUrl(request, SharcConstants.ACTION_TWONOTEPLOT);
	    throw new SharcHttpQueryException(err, err);
	  }
	}
  public List<String> getRequiredParamList() {
    return requiredParamList;
  }
  public List<String> getAlternateParamList() {
    return alternateParamList;
  }
}
