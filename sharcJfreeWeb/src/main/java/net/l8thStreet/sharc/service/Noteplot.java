package net.l8thStreet.sharc.service;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.SharcChartUtils;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.NoteInf;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
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

public class Noteplot extends ServiceBaseAction implements ServiceInterface {
  private static Logger LOGGER = Logger.getLogger(Noteplot.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
	static {
		requiredParamList.add(SharcConstants.PARAM_INSTRUMENT);
		requiredParamList.add(SharcConstants.PARAM_PITCH);
	}
	public String getAction() {
		return(SharcConstants.ACTION_NOTEPLOT);
	}
	public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
		String inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
    String pitch = request.getParameter(SharcConstants.PARAM_PITCH);
    InstrumentInf instrument = new Instrument(inst);
    String fullInstName = instrument.getFullName();
    LOGGER.info("fullname is " + fullInstName);
    NoteInf note = instrument.getNote(pitch);
    XYSeries series1 = ServiceFunctions.makeSharcNoteXYseries(
      ServiceFunctions.notePlotLegend(String.valueOf(note.getNumHarms()), String.valueOf(note.getFundHz())),
      note);
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    // JFreeChart chart = SharcUtils.standardSharcChart(fullInstName, pitch, dataset);
		JFreeChart chart = SharcChartUtils.sharcImpulseChart(fullInstName + " " +  pitch, "Harmonic", "Amplitude (db)", dataset);
    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
    ServiceFunctions.plotToBrowser(request, response, chart, info);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_NOTEPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_NOTEPLOT).append("' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_PITCH).append("</li>")
    .append("</ul>Sample URL:<br/>");
    String link = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_NOTEPLOT + "&" +
      SharcConstants.PARAM_INSTRUMENT + "=violin_vibrato&" +
      SharcConstants.PARAM_PITCH + "=c4");
    sb.append(link)
    .append("<h2>Instrument Requirements</h2>").append(ValidationMessages.acceptableInstrumentsText())
    .append("<h2>Pitch Requirements</h2>").append(ValidationMessages.acceptablePitchesText())
    .append("<h2>Available Pitches</h2>Each instrument has its own available range of pitches.")
    .append(ValidationMessages.availablePitchesText());
    return(sb.toString());
  }
  public void validateService(HttpServletRequest request) throws SharcException {
  /* INSTRUMENT AND PITCH REQUIRED */
    String inst, pitch;
    /* inst and pitch will already have been validated by SharcService.scrubParameters() */
    inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
	  pitch = request.getParameter(SharcConstants.PARAM_PITCH);
	  /* INSTRUMENT MUST HAVE THAT PITCH IN RANGE   */
	  if (!SharcXmlSingleton.getInstance().instHasNote(inst, pitch)) {
	    throw new SharcHttpQueryException(
	      "The instrument [" + inst + "] does not contain pitch [" + pitch + "].",
	      "The instrument [" + inst + "] does not contain pitch [" + pitch + "]. " +
	        "The available pitches are " + SharcXmlSingleton.getInstance().instPitchList(inst)
      );
	  }
	}

  public List<String> getRequiredParamList() {
    return requiredParamList;
  }
  public List<String> getAlternateParamList() {
    return alternateParamList;
  }
}
