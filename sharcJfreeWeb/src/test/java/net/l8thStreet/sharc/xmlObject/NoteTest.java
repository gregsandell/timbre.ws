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
public class NoteTest
{
    Instrument i;
    Note n;
    @Before
    public void setup()

    {
        try
        {
            i = new Instrument("flute_vibrato");
            n = i.getNote("c4");
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
            assertTrue("Pitch name unexpected", "c4".equals(n.getPitch()));
            assertTrue("Unexpected keynum", "48".equals(n.getKeyNum()));
            assertEquals("Unexpected number of harmonics", 37, n.getNumHarms());
            assertEquals("Unexpected fundamental frequency for note", 261.626, n.getFundHz(), 0.00001);
            assertEquals("Unexpected minimum harmonic amplitude for note", 0.22, n.getMinAmp(), 0.0001);
            assertEquals("Unexpected maximum harmonic amplitude for note", 2957.0, n.getMaxAmp(), 0.00001);
            List<Harmonic> harmonics = n.getSharcHarmonics();
            assertEquals("Unexpected number of harmonics", 37, harmonics.size());

        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }
    }
}