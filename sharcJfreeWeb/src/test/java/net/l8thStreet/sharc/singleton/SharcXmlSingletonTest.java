package net.l8thStreet.sharc.singleton;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.jdom.Document;
import org.jdom.Element;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcUtils;
import net.street18.util.JdomUtils;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 20, 2009 Time: 5:21:11 PM To change this
 * template use File | Settings | File Templates.
 */
public class SharcXmlSingletonTest
{
    private SharcXmlSingleton s;
    private static final String flutePitches = "c4 cs4 d4 ds4 e4 f4 fs4 g4 gs4 a4 as4 b4 c5 cs5 d5 ds5 e5 f5 fs5 g5 gs5 a5 as5 b5 c6 cs6 d6 ds6 e6 f6 fs6 g6 gs6 a6 as6 b6 c7";
    @Before
    public void setup()

    {
        s = SharcXmlSingleton.getInstance();
    }

    @Test
    public void initTest()

    {
        assertNotNull("Could not create the SharcXmlSingleton", s);
        Map<String,String> noteCatalogMap = s.getNoteCatalog();
        assertNotNull("Could not get the note catalog", noteCatalogMap);
        Assert.assertEquals("Note catalog size should have equalled the number of instruments", noteCatalogMap.size(), SharcConstants.NUM_INSTRUMENTS);
        String fluteNoteList = noteCatalogMap.get("flute_vibrato");
        assertNotNull("Expected instrument 'flute_vibrato' not found in xml", fluteNoteList);
        assertTrue("Instrument 'flute_vibrato' did not have expected pitches", flutePitches.equals(fluteNoteList));
    }
    @Test
    public void bunchaTests() {
        try
        {
            assertTrue("c4 should have been reported to be a note that flute can play",
                       s.instHasNote("flute_vibrato", "c4"));
            assertFalse("c3 should have been reported to be a note that flute cannot play",
                       s.instHasNote("flute_vibrato", "c3"));
            String fluteFilePath = SharcUtils.getXmlFilePath("flute_vibrato");
            assertNotNull("Should have been able to retrieve a jdom doc from flute xml",
                         s.getSharcJdomDocument(fluteFilePath));
            assertTrue("did not get expected pitch list from 'flute_vibrato'",
                       flutePitches.equals(s.instPitchList("flute_vibrato"))); 
        }
        catch (SharcException e)
        {
            fail("flute xml doc couldn't be retrieved");
        }
        assertTrue("cs4 should have been reported as a valid pitch string", s.isPitch("cs4"));
        assertFalse("c#4 should have been reported as an invalid pitch string", s.isPitch("c#4"));
        assertTrue("Conversion of cs4 to c#4 failed", "c#4".equals(s.hashmarkPitch("cs4")));
        assertTrue("'flute_vibrato' should be a valid instrument name", s.isInstrument("flute_vibrato"));
        assertFalse("'nonsense' should be a valid instrument name", s.isInstrument("nonsense"));
        List instrumentList = s.getInstrumentList();
        assertNotNull("Instrument list is empty", instrumentList);
        assertTrue("Instrument list should have contained 'flute_vibrato'", instrumentList.contains("flute_vibrato"));
     }

}
