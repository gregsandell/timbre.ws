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

import java.util.List;
import java.util.ArrayList;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.CentroidPlotManager;
import net.l8thStreet.sharc.exceptions.SharcException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CentroidTwoplot extends ServiceBaseAction implements ServiceInterface  {
  private static Logger LOGGER = Logger.getLogger(CentroidTwoplot.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
  static {
    requiredParamList.add(SharcConstants.PARAM_INSTRUMENT1);
    requiredParamList.add(SharcConstants.PARAM_INSTRUMENT2);
  }
  public String getAction() {
    return(SharcConstants.ACTION_CENTROIDTWOPLOT);
  }
  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    String inst1 = request.getParameter(SharcConstants.PARAM_INSTRUMENT1);
    String inst2 = request.getParameter(SharcConstants.PARAM_INSTRUMENT2);
    CentroidPlotManager cpm = new CentroidPlotManager();
    cpm.add(inst1);
    cpm.add(inst2);
    XYSeriesCollection dataset = new XYSeriesCollection();
    JFreeChart chart = ChartFactory.createScatterPlot("'Brightness' over chromatic scale",
            "Pitch number (c4 = 48)", "Spectral Centroid (Hz)", dataset, PlotOrientation.VERTICAL, true, true, false);
    XYSeries series = new XYSeries("stuff");
    series.add(cpm.getMinKeynum(), cpm.getMinCentroid());
    series.add(cpm.getMaxKeynum(), cpm.getMaxCentroid());
    dataset.addSeries(series);
    XYPlot plot = (XYPlot) chart.getPlot();
    cpm.plotAll(plot);
    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
    ServiceFunctions.plotToBrowser(request, response, chart, info);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_CENTROIDTWOPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_CENTROIDTWOPLOT + "' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT1).append("</li>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT2).append("</li>")
    .append("</ul>Sample URLs:<br/>");
    String url = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_CENTROIDTWOPLOT + "&" +
      SharcConstants.PARAM_INSTRUMENT1 + "=violin_vibrato&"  +
      SharcConstants.PARAM_INSTRUMENT2 + "=flute_vibrato");
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
