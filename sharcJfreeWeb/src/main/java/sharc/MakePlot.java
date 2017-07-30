package sharc;

import org.jdom.Document;
import org.jdom.Element;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.*;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.awt.*;

import net.street18.util.JdomUtils;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.exceptions.SharcException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 22, 2006
 * Time: 9:55:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class MakePlot extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
          SharcPlot(response);
      }
        catch (SharcException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    private void SharcPlot(HttpServletResponse response) throws SharcException {
        final String filePath = "g:\\sharc\\xml\\bassflute_vibrato.xml";
        Document sharcDoc = SharcXmlSingleton.getInstance().getSharcJdomDocument(filePath);
        Element tree = sharcDoc.getRootElement();
        XYSeries series1 = new XYSeries("Bass Flute c3");
        List notes = tree.getChild("instrument").getChildren("note");
        Element note = null;
        for (int i = 0; i < notes.size(); i++) {
            if (((String)((Element)notes.get(i)).getAttributeValue("pitch")).equals("c3")) {
                note = (Element)notes.get(i);
                break;
            }
        }
        double fund = Double.valueOf(note.getAttributeValue("fundHz")).doubleValue();
        double maxAmp = Double.valueOf(note.getChild("ranges").getChild("highest").getChild("amplitude").getText()).doubleValue();
        List harmonics = note.getChildren("a");
        for (int i = 0; i < harmonics.size(); i++) {
            Element a = (Element)harmonics.get(i);
            double freq = Double.valueOf(a.getAttributeValue("n")).doubleValue() * fund;
            double amp = 20.0 * log10(Double.valueOf(a.getText()).doubleValue()/maxAmp);
            series1.add(freq, amp);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 2",      // chart title
            "X",                      // keynum axis label
            "Y",                      // centroid axis label
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
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        ChartRenderingInfo info = new ChartRenderingInfo(
            new StandardEntityCollection()
        );
        File file1 = new File("sharcPlot.png");
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            //ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info);
            ChartUtilities.writeChartAsPNG(os, chart, 600, 400, info);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    private double log10(double d) {
        return Math.log(d)/Math.log(10.0);
    }

}
