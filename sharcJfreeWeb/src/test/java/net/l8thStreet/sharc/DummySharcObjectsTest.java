package net.l8thStreet.sharc;

import java.io.OutputStream;
import java.io.IOException;

import org.junit.*;
import static org.junit.Assert.assertNotNull;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.*;
import org.jfree.chart.plot.XYPlot;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import junit.framework.JUnit4TestAdapter;
import static junit.framework.Assert.fail;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 30, 2009 Time: 2:26:47 PM To change this
 * template use File | Settings | File Templates.
 */
public class DummySharcObjectsTest
{
  MockHttpServletRequest request;
  MockHttpServletResponse response;
  public static junit.framework.Test suite()
  {
    return new JUnit4TestAdapter(DummySharcObjectsTest.class);
  }

  @Before
  public void setUp()
  {
    request = new MockHttpServletRequest("GET", "/main.app");
    response = new MockHttpServletResponse();
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testPlottingLifecycle()
  {
    XYSeriesCollection dataset = DummySharcObjects.makeDataset();
    assertNotNull(dataset);
    JFreeChart chart = DummySharcObjects.makeChart(dataset);
    assertNotNull(chart);
    XYSeries series = DummySharcObjects.makeSeries();
    assertNotNull(series);
    DummySharcObjects.putData(series);
    ChartRenderingInfo info = DummySharcObjects.makeChartInfo();
    assertNotNull(info);
    XYPlot plot = DummySharcObjects.getXYPlot(chart);
    assertNotNull(plot);
    DummySharcObjects.addAnnotation(plot);
    OutputStream os = response.getOutputStream();
    assertNotNull(os);
    try
    {
      ChartUtilities.writeChartAsPNG(os, chart, 100, 200, info);
    }
    catch (IOException e)
    {
      fail("Failure creating a plot");
    }
  }
  @Test
  public void testStuff() {
    XYPlot plot = DummySharcObjects.makeAPlotObject();
    assertNotNull(plot);
  }
}