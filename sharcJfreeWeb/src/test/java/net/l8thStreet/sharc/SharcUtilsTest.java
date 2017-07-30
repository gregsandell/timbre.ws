package net.l8thStreet.sharc;

import java.util.List;
import java.util.Map;
import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.jdom.Document;
import org.jdom.Element;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.StandardEntityCollection;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.exceptions.*;
import net.l8thStreet.sharc.xmlObject.*;
import net.l8thStreet.sharc.service.ServiceFunctions;
import net.street18.util.JdomUtils;
import net.street18.util.FileSystemUtils;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 20, 2009 Time: 5:21:11 PM To change this
 * template use File | Settings | File Templates.
 */
public class SharcUtilsTest
{

    @Before
    public void setup()

    {
    }

    @Test
    public void someTests()

    {
        assertEquals("Wrong result from log10()", 1.0, SharcUtils.log10(10.0), 0.00001);
        try
        {
            double d = SharcUtils.linToDb(0.0, 0.0);
            fail("Divide by zero should have thrown exception");
        }
        catch (SharcException e)
        {
        }
        try
        {
            File flute = new File(SharcUtils.getXmlFilePath("flute_vibrato"));
            assertTrue("Xml file for flute should have been found", flute.exists());
        }
        catch (SharcException e)
        {
            fail("'flute_vibrato' should have been recognized as an instrument");
        }
        try
        {
            Instrument i = new Instrument("flute_vibrato");
            Note n = i.getNote("c4");
            assertEquals("Unexpected fundamental frequency", 261.626, n.getFundHz(), 0.00001);
        }
        catch (SharcException e)
        {
            fail("Unexpected exception while testing SharcUtils.getFundHz");
        }
        try
        {
            String filepath = SharcUtils.getXmlFilePath("flute_vibrato");
            assertTrue("Did not create contents as expected", filepath.indexOf("flute_vibrato.xml") > -1);
        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }
        try
        {
            assertTrue("Unexpected full name", "Flute".equals(SharcUtils.getFullNameFromInstName("flute_vibrato")));
        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }
        try
        {
            List<String> insts = SharcUtils.getInstrumentsForPitch("c4");
            assertEquals("Wrong number of instruments found", 34, insts.size());
            assertTrue("Expected instrument not found", insts.contains("flute_vibrato"));
        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }
    }
    @Test
    public void sharcNotesTest() {
        try
        {
            List<String> sharcNotes = SharcUtils.getSharcNotes("flute_vibrato", "violin_vibrato");
            assertTrue("Did not find expected lowest note", "c4".equals(sharcNotes.get(0)));
        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }

    }
    @Test
    public void nonsenseInstrumentRetrieve() {
        try {
            SharcUtils.getXmlFilePath("nonsense");
        }
        catch (SharcException e) { return; }
        fail("Attempt to get file path for nonsense instrument name should have failed");
    }
    @Test
    public void jdomTest() {
        // TODO File path must be universal
        File file = new File("/Users/gsandell/dropbox/Dropbox/dropDrive/asandell/sharc/xml/flute_vibrato.xml");
        Document doc = JdomUtils.fileObjToDoc(file);
        Element root = doc.getRootElement();
        Element instrument = root.getChild("instrument");
        List<Element> notes = instrument.getChildren("note");
    }
    @Test
    public void standardSharcChartTest() {
        try
        {
            InstrumentInf instrument = new Instrument("flute_vibrato");
            NoteInf note = instrument.getNote("c4");
            XYSeriesCollection dataset = new XYSeriesCollection();
            XYSeries series1 = ServiceFunctions.makeSharcNoteXYseries("Flute", note);
            dataset.addSeries(series1);
            JFreeChart chart = SharcUtils.standardSharcChart("Flute", "c4", dataset);
            assertNotNull("JFreeChart should have been non-null", chart);
        }
        catch (SharcException e)
        {
            fail("Unexpected exception");
        }
    }

}