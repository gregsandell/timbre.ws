package net.l8thStreet.sharc;

import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.jfree.chart.plot.XYPlot;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.service.Noteplot;
import net.l8thStreet.sharc.service.NoteplotTest;
import net.l8thStreet.sharc.exceptions.SharcException;

import junit.framework.JUnit4TestAdapter;
import static junit.framework.Assert.assertEquals;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 26, 2009 Time: 4:46:17 PM To change this
 * template use File | Settings | File Templates.
 */
public class CentroidSeriesTest
{
  CentroidSeries centroidSeries;
  int fluteCentroids = 37; //Last I checked, this is the expected number
  public static junit.framework.Test suite()
  {
      return new JUnit4TestAdapter(CentroidSeriesTest.class);
  }

  @Before
  public void setUp()
  {
    centroidSeries = new CentroidSeries("flute_vibrato");
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testSomething() {

    XYPlot plot = DummySharcObjects.makeAPlotObject();
    try
    {
      centroidSeries = new CentroidSeries("flute_vibrato");
      centroidSeries.addToPlot(plot);
      fail("Should have thrown exception");
    }
    catch (SharcException e)
    {
    }
    try
    {
      centroidSeries = new CentroidSeries("flute_vibrato");
      centroidSeries.make();
    }
    catch (SharcException e)
    {
      fail("Failure to make centroid series");
    }
    try
    {
      assertNotNull("Failed to get a centroid point", centroidSeries.getCentroidPoint(0));
    }
    catch (SharcException e)
    {
      fail("Unexpected exception");
    }
    try
    {
      assertEquals("Unexpected number of centroids", fluteCentroids, centroidSeries.getNumCentroids());
    }
    catch (SharcException e)
    {
      fail("Unexpected exception");
    }
  }
}