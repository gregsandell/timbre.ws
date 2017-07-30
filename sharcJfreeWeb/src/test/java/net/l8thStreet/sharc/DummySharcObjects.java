package net.l8thStreet.sharc;

import org.jfree.data.xy.*;
import org.jfree.chart.*;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.*;
import static org.junit.Assert.assertNotNull;
import net.l8thStreet.sharc.exceptions.SharcException;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 30, 2009 Time: 2:07:51 PM To change this
 * template use File | Settings | File Templates.
 */
public class DummySharcObjects
{

  public static XYSeriesCollection makeDataset() {
    return(new XYSeriesCollection());
  }
  public static JFreeChart makeChart(XYSeriesCollection xySeriesCollection) {
    return(ChartFactory.createScatterPlot("My title",
          "My y axis", "My x axis", xySeriesCollection, PlotOrientation.VERTICAL, true, true, false));
  }
  public static XYSeries makeSeries() {
    return(new XYSeries("stuff"));
  }
  public static void putData(XYSeries xySeries) {
    xySeries.add(1.0, 100.0);
    xySeries.add(2.0, 99.0);
  }
  public static ChartRenderingInfo makeChartInfo() {
    return(new ChartRenderingInfo(new StandardEntityCollection()));
  }
  public static Plot getPlot(JFreeChart chart) {
    return(chart.getPlot());
  }
  public static XYPlot getXYPlot(JFreeChart chart) {
    return((XYPlot)chart.getPlot());
  }
  public static void addAnnotation(XYPlot plot) {
    XYTextAnnotation xyt = new XYTextAnnotation("My text", 1.5, 99.5);
    plot.addAnnotation(xyt);
  }
  public static XYPlot makeAPlotObject() {
    XYSeriesCollection dataset = DummySharcObjects.makeDataset();
    JFreeChart chart = DummySharcObjects.makeChart(dataset);
    XYSeries series = DummySharcObjects.makeSeries();
    DummySharcObjects.putData(series);
    return(DummySharcObjects.getXYPlot(chart));    
  }
}
