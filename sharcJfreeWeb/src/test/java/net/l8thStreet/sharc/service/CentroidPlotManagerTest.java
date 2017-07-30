package net.l8thStreet.sharc.service;

import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import org.jfree.chart.plot.XYPlot;

import net.l8thStreet.sharc.CentroidPlotManager;
import net.l8thStreet.sharc.DummySharcObjects;
import net.l8thStreet.sharc.exceptions.SharcException;

import junit.framework.JUnit4TestAdapter;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 26, 2009 Time: 4:46:17 PM To change this
 * template use File | Settings | File Templates.
 */
public class CentroidPlotManagerTest
{
  CentroidPlotManager manager;
  String inst1, inst2;

  public static junit.framework.Test suite()
  {
      return new JUnit4TestAdapter(CentroidPlotManagerTest.class);
  }

  @Before
  public void setUp()
  {
     manager = new CentroidPlotManager();
    inst1 = "bassoon";
    inst2 = "flute_vibrato";
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testSomething()
  {
    try
    {
      manager.add(inst1);
      manager.add(inst2);
    }
    catch (SharcException e)
    {
      fail("Failure adding instruments");
    }
    try
    {
      manager.getCentroidRanges();
    }
    catch (SharcException e)
    {
      fail("Failure getting centroid ranges");
    }
    try
    {
      assertEquals(2508.8330831479984, manager.getMaxCentroid(), .0001);
      assertEquals(478.3726708759824, manager.getMinCentroid(), .0001);
      assertEquals(22.0, manager.getMinKeynum(), .0001);
      assertEquals(84.0, manager.getMaxKeynum(), .0001);
    }
    catch (SharcException e)
    {
      fail("Failure to get a centroid or keynum ranges value");
    }
    XYPlot plot = DummySharcObjects.makeAPlotObject();
    try
    {
      manager.plotAll(plot);
    }
    catch (SharcException e)
    {
      fail("Plotting failure");
    }
    assertNotNull("Retrieval of plotting object failed", manager.get(0));
  }
}