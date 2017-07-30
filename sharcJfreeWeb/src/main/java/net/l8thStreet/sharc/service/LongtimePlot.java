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

import java.util.List;
import java.util.ArrayList;

public class LongtimePlot extends ServiceBaseAction implements ServiceInterface  {
  private static Logger LOGGER = Logger.getLogger(LongtimePlot.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
  static {
    requiredParamList.add(SharcConstants.PARAM_INSTRUMENT);
    requiredParamList.add(SharcConstants.PARAM_LONGTIME_BINS);
    alternateParamList.add(SharcConstants.PARAM_LONGTIME_LOWFREQ);
    alternateParamList.add(SharcConstants.PARAM_LONGTIME_HIGHFREQ);
  }
  public String getAction() {
    return(SharcConstants.ACTION_LONGTIMEPLOT);
  }

  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
    LongtimePlotHelper helper = new LongtimePlotHelper(request);
    JFreeChart chart = helper.makeLongtimeChart();
    ServiceFunctions.plotToBrowser(request, response, chart, info);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_LONGTIMEPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_LONGTIMEPLOT + "' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_LONGTIME_BINS).append("</li>")
    .append("<li>(optional) ").append(SharcConstants.PARAM_LONGTIME_LOWFREQ).append("</li>")
    .append("<li>(optional) ").append(SharcConstants.PARAM_LONGTIME_HIGHFREQ).append("</li>")
    .append("</ul>Sample URLs:<br/>");
    String url = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_LONGTIMEPLOT + "&" +
      SharcConstants.PARAM_INSTRUMENT + "=violin_vibrato&" +
      SharcConstants.PARAM_LONGTIME_BINS + "=30");
		sb.append(url).append("<br/>");
		url = ServiceFunctions.makeUrlLink(request,
				SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_LONGTIMEPLOT + "&" +
						SharcConstants.PARAM_INSTRUMENT + "=violin_vibrato&" +
						SharcConstants.PARAM_LONGTIME_BINS + "=30&" +
						SharcConstants.PARAM_LONGTIME_LOWFREQ + "=2000.46&" +
						SharcConstants.PARAM_LONGTIME_HIGHFREQ + "=4000.99"
				);
		sb.append(url)
		.append("<h2>Instrument Requirements</h2>").append(ValidationMessages.acceptableInstrumentsText())
    .append("<h2>Bins Requirements</h2>Specifies the number of frequency bins that energy is")
    .append("summed over.  Must be an integer.");
    return(sb.toString());
  }
  public void validateService(HttpServletRequest request) throws SharcException {
    ValidationMessages.longtimeValidation(request, SharcConstants.ACTION_LONGTIMEPLOT );
  }
  public List<String> getRequiredParamList() {
    return requiredParamList;
  }
  public List<String> getAlternateParamList() {
    return alternateParamList;
  }
}
