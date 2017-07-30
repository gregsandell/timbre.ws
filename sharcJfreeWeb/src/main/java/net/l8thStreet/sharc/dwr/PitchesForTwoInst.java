package net.l8thStreet.sharc.dwr;

import org.apache.log4j.Logger;

import java.util.List;

import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.exceptions.SharcException;

public class PitchesForTwoInst {
  private static Logger LOGGER = Logger.getLogger(PitchesForTwoInst.class);
	public static List<String> getPitches(String inst1, String inst2) {
    List<String> notes = null;
    try {
      notes = SharcUtils.getSharcNotes(inst1, inst2);
    } catch (SharcException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    String[] result = notes.toArray(new String[] {});
    return(notes);
	}
}
