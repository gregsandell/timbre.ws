package net.l8thStreet.sharc.service;

import org.apache.log4j.Logger;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jdom.Element;

import java.util.List;
import java.util.ArrayList;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.CentroidPlotManager;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.waveform.SynthFuncs;
import net.l8thStreet.sharc.exceptions.SharcException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WavePlot extends ServiceBaseAction implements ServiceInterface  {
  private static Logger LOGGER = Logger.getLogger(WavePlot.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
  static {
    requiredParamList.add(SharcConstants.PARAM_INSTRUMENT);
		requiredParamList.add(SharcConstants.PARAM_PITCH);
  }
  public String getAction() {
    return(SharcConstants.ACTION_WAVEPLOT);
  }
  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    String inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
		String note = request.getParameter(SharcConstants.PARAM_PITCH);
    InstrumentInf instrument = new Instrument(inst);
    String fullInstName = instrument.getFullName();
		String title = fullInstName + " " + note + " (four periods)";
		JFreeChart chart = SynthFuncs.makeWavePlot(inst, note, title, 22050);
		// String title = fullInstName + " all (1000 samples per note)";
		// JFreeChart chart = SynthFuncs.makeAllNoteWavePlot(inst, title, 100, 22050);
    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		ServiceFunctions.plotToBrowser(request, response, chart, info);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_WAVEPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_WAVEPLOT + "' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT).append("</li>")
		.append("<li>").append(SharcConstants.PARAM_PITCH).append("</li>")
    .append("</ul>Sample URLs:<br/>");
    String url = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_WAVEPLOT + "&" +
      SharcConstants.PARAM_INSTRUMENT + "=violin_vibrato&" +
			SharcConstants.PARAM_PITCH + "=c4");
		sb.append(url)
		.append("<h2>Instrument Requirements</h2>").append(ValidationMessages.acceptableInstrumentsText());
    return(sb.toString());
  }
  public void validateService(HttpServletRequest request) throws SharcException {
  }
  public List<String> getRequiredParamList() {
    return requiredParamList;
  }
  public List<String> getAlternateParamList() {
    return alternateParamList;
  }
}
