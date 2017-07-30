package net.l8thStreet.sharc.service;

import org.junit.*;
import static org.junit.Assert.*;
import org.jfree.data.xy.XYSeries;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcChartUtils;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.Note;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.servlet.SharcService;
import static net.l8thStreet.sharc.servlet.SharcService.*;

import junit.framework.JUnit4TestAdapter;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 23, 2009 Time: 1:14:27 PM To change this
 * template use File | Settings | File Templates.
 */
public class ServiceFunctionsTest
{
  public static junit.framework.Test suite()
  {
      return new JUnit4TestAdapter(ServiceFunctionsTest.class);
  }

  @Before
  public void setUp()
  {
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testSomething()
  {
      assertTrue("Known action not recognized as an action", ServiceFunctions.isKnownAction(SharcConstants.ACTION_CENTROIDPLOT));
      assertFalse("Unknown action recognized as a known action", ServiceFunctions.isKnownAction("bogusAction"));
      assertTrue("Unexpected plot legend text", "Harmonics 1-10 (F0 = 440.0 Hz.)".equals(ServiceFunctions.notePlotLegend("10", "440.0")));
      try
      {
          assertEquals("Conversion of sring double failed", 123.4, ServiceFunctions.toDouble("123.4"), 0.00001);
      }
      catch (SharcException e)
      {
          fail("Unexpected exception thrown from toDouble");
      }
      try
      {
          ServiceFunctions.toDouble("12R3.4");
          fail("Non-double string conversion should have thrown exception");
      }
      catch (SharcException e)
      {
      }
      Instrument i;
      Note n;
      try
      {
        i = new Instrument("flute_vibrato");
        n = i.getNote("c4");
        XYSeries xySeries = ServiceFunctions.makeSharcNoteXYseries("My legend Text", n);
        assertNotNull("Failed to return non-null XYSeries object", xySeries);
      }
      catch (SharcException e)
      {
          fail("Could not create Instrument for 'flute_vibrato'");
      }


  }
}