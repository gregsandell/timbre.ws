package net.l8thStreet.sharc;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import net.l8thStreet.sharc.exceptions.SharcMathException;

import junit.framework.JUnit4TestAdapter;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 26, 2009 Time: 10:19:22 PM To change this
 * template use File | Settings | File Templates.
 */
public class BinManagerTest
{
    BinManager bMan;
    private int nBins;
    private double lowFreq;
    private double highFreq;
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter(BinManagerTest.class);
    }

    @Before
    public void setUp()
    {
        nBins = 100;
        lowFreq = 100.0;
        highFreq = 10000.0;
        bMan = new BinManager(nBins, lowFreq, highFreq);
        bMan.makeBins();
        assertEquals("Unexpected number of bins", (highFreq-lowFreq)/(double)nBins, (double)bMan.getNumBins(), 1.0);
        try
        {
            bMan.add(1000.0, 1000.0);
        }
        catch (SharcMathException e)
        {
            fail("Add of freq/amp should have succeeded");
        }
        assertEquals("Addition of one frequency should have yielded max amp of that frequency's amplitude",
                     bMan.findMaxSumAmp(), 1000.0, 0.00001);
                try
        {
            bMan.add(lowFreq - 1.0, 1000.0);
            fail("Adding too-low frequency should have thrown exception");
        }
        catch (SharcMathException e)
        {
        }
        try
        {
            bMan.add(lowFreq, 1000.0);
        }
        catch (SharcMathException e)
        {
            fail("Adding frequency at low boundary should not have thrown exception");
        }
        try
        {
            bMan.add(highFreq - 1.0, 1000.0);
        }
        catch (SharcMathException e)
        {
            fail("Adding frequency near high boundary should not have thrown exception");
        }
        try
        {
            bMan.add(highFreq, 1000.0);
            fail("Adding frequency at high boundary should have thrown exception");
        }
        catch (SharcMathException e)
        {
        }
        try
        {
            bMan.add(highFreq + 1.0, 1000.0);
            fail("Adding too-high frequency should have thrown exception");
        }
        catch (SharcMathException e)
        {
        }
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testSomething()
    {
    }
}