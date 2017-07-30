package net.l8thStreet.sharc.service;

import org.apache.log4j.Logger;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.StandardEntityCollection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcChartUtils;

import java.util.ArrayList;
import java.util.List;

public class LongtimeTwoplot  extends ServiceBaseAction implements ServiceInterface {
  private static Logger LOGGER = Logger.getLogger(LongtimeTwoplot.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    String inst1 = request.getParameter(SharcConstants.PARAM_INSTRUMENT1);
    String inst2 = request.getParameter(SharcConstants.PARAM_INSTRUMENT2);
    LongtimePlotHelper helper = new LongtimePlotHelper(request);
    helper.setInst(inst1);
    XYSeries series1 = helper.makeLongtimeXYseries();
    helper = new LongtimePlotHelper(request);    // TODO find out why the 2nd 'new' is needed
    helper.setInst(inst2);
    XYSeries series2 = helper.makeLongtimeXYseries();
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    dataset.addSeries(series2);
    JFreeChart chart = SharcChartUtils.standardSharcChart("Longtime spectrum",
      "Center Frequency (Hz)", "amplitude (dB)",dataset);
    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
    ServiceFunctions.plotToBrowser(request, response, chart, info);
  }
  public String getAction() {
    return(SharcConstants.ACTION_LONGTIMETWOPLOT);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_LONGTIMETWOPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_LONGTIMETWOPLOT + "' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT1).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT2).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_PITCH).append("</li>")
		.append("<li>(optional) ").append(SharcConstants.PARAM_LONGTIME_LOWFREQ).append("</li>")
		.append("<li>(optional) ").append(SharcConstants.PARAM_LONGTIME_HIGHFREQ).append("</li>")
    .append("</ul>Sample URLs:<br/>");
    String url = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_LONGTIMETWOPLOT + "&" +
      SharcConstants.PARAM_INSTRUMENT1 + "=violin_vibrato&" +
      SharcConstants.PARAM_INSTRUMENT2 + "=flute_vibrato&" +
			SharcConstants.PARAM_LONGTIME_BINS + "=30");
		sb.append(url).append("<br/>");
		url = ServiceFunctions.makeUrlLink(request,
				SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_LONGTIMETWOPLOT + "&" +
						SharcConstants.PARAM_INSTRUMENT1 + "=violin_vibrato&" +
						SharcConstants.PARAM_INSTRUMENT2 + "=flute_vibrato&" +
						SharcConstants.PARAM_LONGTIME_BINS + "=30&" +
						SharcConstants.PARAM_LONGTIME_LOWFREQ + "=2000.46&" +
						SharcConstants.PARAM_LONGTIME_HIGHFREQ + "=4000.99"
		);
    sb.append(url).append("<h2>Instrument Requirements</h2>").append(ValidationMessages.acceptableInstrumentsText())
    .append("<h2>Pitch Requirements</h2>").append(ValidationMessages.acceptablePitchesText())
    .append("<h2>Available Pitches</h2>Each instrument has its own available range of pitches.")
		.append(ValidationMessages.availablePitchesText());
    return(sb.toString());


  }
  public void validateService(HttpServletRequest request) throws SharcException {
		ValidationMessages.longtimeValidation(request, SharcConstants.ACTION_LONGTIMETWOPLOT );
	}
    public List<String> getRequiredParamList() {
    return requiredParamList;
  }
  public List<String> getAlternateParamList() {
    return alternateParamList;
  }
}
