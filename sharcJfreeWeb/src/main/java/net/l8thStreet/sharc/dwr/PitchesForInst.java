package net.l8thStreet.sharc.dwr;

import org.apache.log4j.Logger;
import org.jdom.Element;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Array;

import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;

public class PitchesForInst {
  private static Logger LOGGER = Logger.getLogger(PitchesForInst.class);
	public static List<String> getPitches(String inst) {
    List<String> notes = null;
    try {
      // notes = SharcUtils.getNoteList(SharcUtils.getSharcNotes(SharcUtils.getSharcInstrument(inst)));
      notes = (new Instrument(inst)).getNoteList();
    } catch (SharcException e) {
      e.printStackTrace();  
    }
    String[] result = notes.toArray(new String[] {});
    return(notes);
	}
}
