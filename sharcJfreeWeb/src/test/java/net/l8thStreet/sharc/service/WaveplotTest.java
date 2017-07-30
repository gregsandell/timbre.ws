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
public class WaveplotTest
{
  MockHttpServletRequest request;
  MockHttpServletResponse response;
  WavePlot waveplot;

  public static junit.framework.Test suite()
  {
      return new JUnit4TestAdapter(WaveplotTest.class);
  }

  @Before
  public void setUp()
  {
       request = new MockHttpServletRequest("GET", "/main.app");
       response = new MockHttpServletResponse();
    waveplot = new WavePlot();
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testSomething()
  {
    int bins = 35;
    String inst = "flute_vibrato";
    String pitch = "c4";
    request.addParameter(SharcConstants.PARAM_INSTRUMENT, inst);
    request.addParameter(SharcConstants.PARAM_ACTION, SharcConstants.ACTION_WAVEPLOT);
    request.addParameter(SharcConstants.PARAM_PITCH, pitch);
    try  {
      waveplot.doPlot(request, response);
    }
    catch (SharcException e)
    {
        fail("Failed waveplot");
    }
    try  {
      request.addParameter(SharcConstants.PARAM_INSTRUMENT, "bogus");
      waveplot.doPlot(request, response);
      fail("Should have failed for non-existent instrument");
    }
    catch (SharcException e)
    {
    }
    try  {
      request.addParameter(SharcConstants.PARAM_PITCH, "bogus");
      waveplot.doPlot(request, response);
      fail("Should have failed for invalid pitch");
    }
    catch (SharcException e)
    {
    }
    try  {
      request.addParameter(SharcConstants.PARAM_PITCH, "c2");
      waveplot.doPlot(request, response);
      fail("Should have failed for out of range pitch");
    }
    catch (SharcException e)
    {
    }

  }
}