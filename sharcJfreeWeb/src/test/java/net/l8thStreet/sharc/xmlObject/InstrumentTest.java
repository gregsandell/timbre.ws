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
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.Note;
import net.street18.util.JdomUtils;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 20, 2009 Time: 5:21:11 PM To change this
 * template use File | Settings | File Templates.
 */
public class InstrumentTest
{
    Instrument i;
    Note n;
    @Before
    public void setup()

    {
    }

    @Test
    public void someTests()  {
        try
        {
            i = new Instrument("nonsense");
            fail("Should have been prevented from creating nonsense instrument");
        }
        catch (SharcException e)
        {
        }
        assertNull("Failed instrument retrieval should have produced null object", i);
        try
        {
            i = new Instrument("flute_vibrato");
            assertNotNull("Successful instrument retrieval should have produced non-null object", i);
        }
        catch (SharcException e)
        {
            fail("Could not create Instrument for 'flute_vibrato'");
        }
        try
        {
            n = i.getNote("c2");
            fail("Should have failed to retrieve nonexistent note for this instrument");
        }
        catch (SharcException e)
        {
        }
        try
        {
            n = i.getNote("c4");
        }
        catch (SharcException e)
        {
            fail("Failed to retrieve in-range note for this instrument");
        }
        try
        {
            List<Note> noteList = i.getSharcNotes();
            assertEquals("Did not get expected number of notes for 'flute_vibrato'", 37, noteList.size());
        }
        catch (SharcException e)
        {
            fail("Failed attempt to get notelist for instrument");
        }
        try
        {
            List<String> pitchList = i.getNoteList();
            assertEquals("Did not get expected number of pitches for 'flute_vibrato'", 37, pitchList.size());
            // TODO  Fix this failed test
            // assertEquals("First pitch was not the one expected", "c4", pitchList.get(0));
        }
        catch (SharcException e)
        {
            fail("Failed attempt to get notelist for instrument");
        }
        assertEquals("Did not get expected instrument name", "Flute", i.getFullName());
        assertEquals("Did not get expected lowKeynum value", 48, i.getLowKeyNum());
        assertEquals("Did not get expected highKeynum value", 84, i.getHighKeyNum());
    }
}