package net.l8thStreet.sharc.service;

import org.apache.log4j.Logger;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.entity.StandardEntityCollection;

import java.util.List;
import java.util.ArrayList;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcChartUtils;
import net.l8thStreet.sharc.CentroidPlotManager;
import net.l8thStreet.sharc.exceptions.SharcException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CentroidPlot extends ServiceBaseAction implements ServiceInterface  {
  private static Logger LOGGER = Logger.getLogger(CentroidPlot.class);
  public static List<String> requiredParamList = new ArrayList<String>();
  public static List<String> alternateParamList = new ArrayList<String>();
  static {
    requiredParamList.add(SharcConstants.PARAM_INSTRUMENT);
  }
  public String getAction() {
    return(SharcConstants.ACTION_CENTROIDPLOT);
  }
  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    String inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
    CentroidPlotManager centroidPlotManager = new CentroidPlotManager();
    centroidPlotManager.add(inst);
    XYSeriesCollection dataset = new XYSeriesCollection();
    JFreeChart chart = ChartFactory.createScatterPlot("'Brightness' over chromatic scale",
            "Pitch number (c4 = 48)", "Spectral Centroid (Hz)", dataset, PlotOrientation.VERTICAL, true, true, false);
    XYSeries series = new XYSeries("stuff");
    series.add(centroidPlotManager.getMinKeynum(), centroidPlotManager.getMinCentroid());
    series.add(centroidPlotManager.getMaxKeynum(), centroidPlotManager.getMaxCentroid());
    dataset.addSeries(series);
    XYPlot plot = (XYPlot) chart.getPlot();
    centroidPlotManager.plotAll(plot);
    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
    ServiceFunctions.plotToBrowser(request, response, chart, info);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_CENTROIDPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_CENTROIDPLOT + "' on the querystring<br/>")
    .append("The required parameters are: <ul>")
    .append("<li>").append(SharcConstants.PARAM_INSTRUMENT).append("</li>")
    .append("</ul>Sample URLs:<br/>");
    String url = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_CENTROIDPLOT + "&" +
      SharcConstants.PARAM_INSTRUMENT + "=violin_vibrato" );
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
