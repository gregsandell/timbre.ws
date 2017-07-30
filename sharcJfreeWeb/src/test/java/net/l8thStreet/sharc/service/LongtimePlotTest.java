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
public class LongtimePlotTest
{
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter(LongtimePlotTest.class);
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
    public void testSomething()
    {
        int bins = 35;
        String inst = "flute_vibrato";
        request.addParameter(SharcConstants.PARAM_INSTRUMENT, inst);
        request.addParameter(SharcConstants.PARAM_ACTION, SharcConstants.ACTION_LONGTIMEPLOT);
        request.addParameter(SharcConstants.PARAM_LONGTIME_BINS, String.valueOf(bins));
        request.addParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ, "100");
        request.addParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ, "10000");
        try
        {
          LongtimePlotHelper helper = new LongtimePlotHelper(request);
          assertNotNull("Should have returned an XYSeriesCollection object", helper.makeLongtimeDataset());
          assertNotNull("Should have returned a JFreeChart object", helper.makeLongtimeChart());
          assertNotNull("Should have returned an XYSeries object", helper.makeLongtimeXYseries());
          helper.addHarmonicEnergyToBins();

          LongtimePlot longtime = new LongtimePlot();
          longtime.doPlot(request, response);
        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }

    }
}