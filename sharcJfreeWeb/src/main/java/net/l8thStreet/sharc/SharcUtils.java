package net.l8thStreet.sharc;

import net.l8thStreet.sharc.exceptions.SharcMathException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.exceptions.SharcXmlException;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.singleton.PropertyConfigurator;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.street18.util.JdomUtils;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.DataConversionException;
import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Enumeration;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 18, 2006
 * Time: 9:10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharcUtils {
  private static Logger LOGGER = Logger.getLogger(SharcUtils.class);
  public static final String allPitches[] = {"as0","b0","c1","cs1","d1","ds1","e1","f1","fs1","g1","gs1","a1","as1","b1","c2","cs2","d2","ds2","e2","f2","fs2","g2","gs2","a2","as2","b2","c3","cs3","d3","ds3","e3","f3","fs3",
"g3","gs3","a3","as3","b3","c4","cs4","d4","ds4","e4","f4","fs4","g4","gs4","a4","as4","b4","c5","cs5","d5","ds5","e5","f5","fs5","g5","gs5","a5","as5","b5","c6","cs6","d6","ds6","e6","f6","fs6","g6","gs6","a6","as6","b6","c7","cs7","d7","ds7","e7","f7","fs7","g7"};
  public static double log10(double d) {
      return Math.log(d)/Math.log(10.0);
  }
  public static double linToDb(double amp, double maxAmp) throws SharcException {
    if (maxAmp == 0.0)  {
      throw new SharcMathException(
        "Conversion to decibels was passed 0.0 for the maximum amplitude; would have caused division by zero",
        "A math error occurred while processing amplitude data.");
    }
    return(20.0 * log10(amp/maxAmp));
  }

    /**
     *
     * @param note
     * @return
     * @throws SharcException
     * @deprecated
     */
  public static double getFundHz(Element note) throws SharcException {
    SharcValidate.notNullArg(note, "note");
      String fundHzString = note.getAttributeValue("fundHz");
    return(toDouble(fundHzString));
  }
  public static JFreeChart standardSharcChart(String fullInstName, String pitch, XYSeriesCollection dataset) throws SharcException {
      SharcValidate.notNullArg(fullInstName, "fullInstName");
      SharcValidate.notNullArg(pitch, "pitch");
      SharcValidate.notNullArg(dataset, "dataset");
    return(SharcChartUtils.standardSharcChart("Harmonic spectrum of " + fullInstName + " (pitch " +
        SharcXmlSingleton.getInstance().hashmarkPitch(pitch) + ")","frequency (Hz)","amplitude (dB)",
      dataset));
  }
  public static List<String> getSharcNotes(String inst1, String inst2) throws SharcException {
    SharcValidate.notNullArg(inst1, "instrument1");
    SharcValidate.notNullArg(inst2, "instrument2");
    List<String> notes1 = (new Instrument(inst1)).getNoteList();
    List<String> notes2 = (new Instrument(inst2)).getNoteList();
    List<String> result = new ArrayList<String>();
    for (int i = 0; i < SharcUtils.allPitches.length; i++) {
      if (notes1.contains(allPitches[i]) && notes2.contains(allPitches[i])) {
        result.add(allPitches[i]);
      }
    }
    return(result);
  }
  public static double toDouble(String d) throws SharcException {
    SharcValidate.notNullArg(d, "d");
    double f = 0.0;
      try
      {
          f = Double.valueOf(d).doubleValue();
      }
      catch (NumberFormatException e)
      {
          throw new SharcMathException("The String '" + d + "' could not be translated to a double primitive", "A numeric conversion could not be performed");
      }
      return(f);
  }
  public static String getXmlFilePath(String instId) throws SharcException {
    String result = "";
    if (SharcXmlSingleton.getInstance().isInstrument(instId)) {
        result = PropertyConfigurator.getInstance().getSharcRootPath() + instId + ".xml";
    }
      else {
      throw new SharcHttpQueryException(
        "Attempt to retrieve non-existent Sharc instrument [" + instId + "]",
        "Attempt to retrieve non-existent Sharc instrument [" + instId + "]");
      }
    return(result);
  }
  public static String getFullNameFromInstName(String instname) throws SharcException {
    InstrumentInf instrument = new Instrument(instname);
    return(instrument.getFullName());
  }

  public static String getCookie(HttpServletRequest request, String name) {
    String result = null;
    Cookie[] cookies =  request.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length && result == null; i++) {
        if (cookies[i].getName().equals(name)) {
          result = cookies[i].getValue();
        }
      }
    }
    return(result);
  }
	public static List<String> getInstrumentsForPitch(String pitch) throws SharcException {
		List<String> result = new ArrayList<String>();
		List<String> instList = SharcXmlSingleton.getInstance().getInstrumentList();
		for (int i = 0; i < instList.size(); i++) {
      if (instList.get(i).equals("sharc")) continue;
      if (SharcXmlSingleton.getInstance().instHasNote(instList.get(i), pitch)) {
				result.add(instList.get(i));
			}
		}
		return(result);
	}

  /**
   *
   * @param inst
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static Element getSharcInstrument(String inst) throws SharcException {
    // TODO Redundant? This should be using the caching in SharcXmlSingleton!
    SharcValidate.notNullArg(inst, "inst");
    String filePath = getXmlFilePath(inst);
    Document sharcDoc = SharcXmlSingleton.getInstance().getSharcJdomDocument(filePath);
    Element tree = sharcDoc.getRootElement();
    List<Element> insts = tree.getChildren("instrument");
    // LOGGER.info(insts.size() + " instruments");
    Element instrument = null;
    for (int i = 0; i < insts.size() && instrument == null; i++) {
      Element e = insts.get(i);
      if ((e.getAttributeValue("id")).equals(inst)) {
          instrument = e;
      }
    }
    SharcValidate.notNullLocalVar(instrument, "instrument", "The <instrument> node for '" + inst + "' may not be in the expected xml file.");
    return(instrument);
  }

  /**
   *
   * @param instrument
   * @param pitch
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static Element getSharcNote(Element instrument, String pitch) throws SharcException {
    SharcValidate.notNullArg(instrument, "instrument");
    SharcValidate.notNullArg(pitch, "pitch");
    List<Element> notes = getSharcNotes(instrument);
    SharcValidate.notNullLocalVar(notes, "notes");
    Element note = null;
    for (int i = 0; i < notes.size()  && note == null; i++) {
      Element e = notes.get(i);
      if (getPitch(e).equals(pitch)) {
        note = e;
      }
    }
    SharcValidate.notNullLocalVar(note, "note", "The <note> node for '" + pitch + "' may not be in the expected xml file");
    return(note);
  }

  /**
   *
   * @param instrument
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static int getInstrumentLowKeynum(Element instrument) throws SharcException {
		List<Element> notes = getSharcNotes(instrument);
		return(Integer.valueOf(notes.get(0).getAttributeValue("keyNum")));
	}

  /**
   *
   * @param instrument
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static int getInstrumentHighKeynum(Element instrument) throws SharcException {
		List<Element> notes = getSharcNotes(instrument);
		return(Integer.valueOf(notes.get(notes.size() - 1).getAttributeValue("keyNum")));
	}

  /**
   *
   * @param instrument
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static List<Element> getSharcNotes(Element instrument) throws SharcException {
    SharcValidate.notNullArg(instrument, "instrument");
    return(instrument.getChildren("note"));
  }

  /**
   *
   * @param note
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static List<Element> getSharcHarmonics(Element note) throws SharcException {
    SharcValidate.notNullArg(note, "note");
    return(note.getChildren("a"));
  }

  /**
   *
   * @param harmonic
   * @param fundHz
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static double getFrequency(Element harmonic, double fundHz) throws SharcException {
    SharcValidate.notNullArg(harmonic, "harmonic");
    return(fundHz * toDouble(harmonic.getAttributeValue("n")));
  }

  /**
   *
   * @param harmonic
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static double getAmplitude(Element harmonic)  throws SharcException {
    SharcValidate.notNullArg(harmonic, "harmonic");
    return(toDouble(harmonic.getText()));
  }

  /**
   *
   * @param harmonic
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static double getPhase(Element harmonic)  throws SharcException {
    SharcValidate.notNullArg(harmonic, "harmonic");
    return(toDouble(harmonic.getAttributeValue("p")));
  }

  /**
   *
   * @param harmonic
   * @param maxAmp
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static double getDb(Element harmonic, double maxAmp) throws SharcException {
    SharcValidate.notNullArg(harmonic, "harmonic");
    return(linToDb(toDouble(harmonic.getText()), maxAmp));
  }


  /**
   *
   * @param note
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static String getKeyNum(Element note)  throws SharcException {
    SharcValidate.notNullArg(note, "note");
    return(note.getAttributeValue("keyNum"));
  }

  /**
   *
   * @param note
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static String getPitch(Element note)  throws SharcException{
    SharcValidate.notNullArg(note, "note");
    return(note.getAttributeValue("pitch"));
  }

  /**
   *
   * @param notes
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static List<String> getNoteList(List<Element> notes)  throws SharcException{
    SharcValidate.notNullArg(notes, "notes");
    List<String> result = new ArrayList<String>();
    for (Element e:notes) {
      result.add(SharcUtils.getPitch(e));
    }
    return(result);
  }

  /**
   *
   * @param instrument
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static String getFullName(Element instrument)  throws SharcException{
    SharcValidate.notNullArg(instrument, "instrument");
    return(instrument.getAttributeValue("name"));
  }
	/*
  public static String getCookie(HttpServletRequest request, String name) {
    String result = null;
    Cookie[] cookies =  request.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length && result == null; i++) {
        if (cookies[i].getName().equals(name)) {
          result = cookies[i].getValue();
        }
      }
    }
    return(result);
  }
  */
  /**
   *
   * @param note
   * @return
   * @throws SharcException
   * @deprecated
   */
  public static int getNumHarms(Element note) throws SharcException {
    SharcValidate.notNullArg(note, "note");
    int numHarms = 0;
    try {
      numHarms = note.getAttribute("numHarms").getIntValue();
    } catch (DataConversionException e) {
        String debug = "Could not convert numHarms value of [" + note.getAttribute("numHarms").getValue() + "] to an integer";
        throw new SharcException(debug, debug);
    }
    return(numHarms);
  }
}
