package net.l8thStreet.sharc.xmlObject;

import java.util.List;
import java.util.Map;
import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.jdom.Document;
import org.jdom.Element;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.singleton.PropertyConfigurator;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.xmlObject.*;
import net.street18.util.JdomUtils;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 20, 2009 Time: 5:21:11 PM To change this
 * template use File | Settings | File Templates.
 */
public class HarmonicTest
{
    Instrument i;
    Note n;
    Harmonic h;
    @Before
    public void setup()

    {
        try
        {
            i = new Instrument("flute_vibrato");
            n = i.getNote("c4");
            h = n.getSharcHarmonics().get(0);
        }
        catch (SharcException e)
        {
            fail("Could not create Instrument for 'flute_vibrato'");
        }
    }

    @Test
    public void someTests()  {
        try
        {
            assertEquals("Unexpected fundamental frequency",261.626, h.getFrequency(), 0.00001);
            assertEquals("Unexpected amplitude", 983.6,h.getAmplitude(), 0.00001);
            assertEquals("Unxpected phase", 2.613, h.getPhase(), 0.00001);
            assertEquals("Unexpected db", -9.560656089188761, h.getDb(), 0.00001);
        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }
    }
}