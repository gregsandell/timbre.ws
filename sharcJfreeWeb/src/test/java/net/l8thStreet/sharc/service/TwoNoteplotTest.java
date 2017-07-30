package net.l8thStreet.sharc.service;

import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.exceptions.SharcException;

import junit.framework.JUnit4TestAdapter;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 26, 2009 Time: 4:46:17 PM To change this
 * template use File | Settings | File Templates.
 */
public class TwoNoteplotTest
{
  MockHttpServletRequest request;
  MockHttpServletResponse response;
  TwoNoteplot twoNoteplot;
  public static junit.framework.Test suite()
  {
      return new JUnit4TestAdapter(TwoNoteplotTest.class);
  }

  @Before
  public void setUp()
  {
    request = new MockHttpServletRequest("GET", "/main.app");
    response = new MockHttpServletResponse();
    twoNoteplot = new TwoNoteplot();
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testSomething()
  {
    int bins = 35;
    String inst1 = "flute_vibrato";
    String inst2 = "violin_vibrato";
    String pitch = "c4";
    request.addParameter(SharcConstants.PARAM_INSTRUMENT1, inst1);
    request.addParameter(SharcConstants.PARAM_INSTRUMENT2, inst2);
    request.addParameter(SharcConstants.PARAM_ACTION, SharcConstants.ACTION_TWONOTEPLOT);
    request.addParameter(SharcConstants.PARAM_PITCH, pitch);
    try  {
      twoNoteplot.doPlot(request, response);
    }
    catch (SharcException e)
    {
        fail("Failed waveplot");
    }
    try  {
      request.addParameter(SharcConstants.PARAM_INSTRUMENT1, "bogus");
      twoNoteplot.doPlot(request, response);
      fail("Should have failed for non-existent instrument");
    }
    catch (SharcException e)
    {
    }
    request.addParameter(SharcConstants.PARAM_INSTRUMENT1, inst1);
    try  {
      request.addParameter(SharcConstants.PARAM_PITCH, "bogus");
      twoNoteplot.doPlot(request, response);
      fail("Should have failed for invalid pitch");
    }
    catch (SharcException e)
    {
    }
    try  {
      request.addParameter(SharcConstants.PARAM_PITCH, "c2");
      twoNoteplot.doPlot(request, response);
      fail("Should have failed for out of range pitch");
    }
    catch (SharcException e)
    {
    }

  }
}