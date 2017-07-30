package net.l8thStreet.sharc;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 19, 2006
 * Time: 12:30:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcChartUtils {
	public static JFreeChart sharcImpulseChart(String desc, String xDesc, String yDesc, XYSeriesCollection dataset) {
	  JFreeChart chart = ChartFactory.createXYBarChart(
       desc, xDesc, false, yDesc,
       dataset,
       PlotOrientation.VERTICAL,
       true,
       true,
       false
    );
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
/*
		XYLineAndShapeRenderer renderer
				= (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setShapesFilled(true);
*/
		// change the auto tick unit selection to integer units only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 400));
		return chart;
	}
	public static JFreeChart standardSharcChart(String desc, String xDesc, String yDesc, XYSeriesCollection dataset) {
    JFreeChart chart = ChartFactory.createXYLineChart(
        desc,      // chart title
        xDesc,                      // keynum axis label
     yDesc,                      // centroid axis label
        dataset,                  // data
        PlotOrientation.VERTICAL,
        true,                     // include legend
        true,                     // tooltips
        false                     // urls
    );

    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
    chart.setBackgroundPaint(Color.white);

    // get a reference to the plot for further customisation...
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.lightGray);
    plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);

    XYLineAndShapeRenderer renderer
        = (XYLineAndShapeRenderer) plot.getRenderer();
    renderer.setShapesVisible(true);
    renderer.setShapesFilled(true);

    // change the auto tick unit selection to integer units only...
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(600, 400));
    return chart;
  }
}
