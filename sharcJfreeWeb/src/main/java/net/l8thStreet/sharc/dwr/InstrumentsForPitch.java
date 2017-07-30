package net.l8thStreet.sharc.dwr;

import org.apache.log4j.Logger;

import java.util.List;

import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.exceptions.SharcException;

public class InstrumentsForPitch {
  private static Logger LOGGER = Logger.getLogger(InstrumentsForPitch.class);
  public static List<String> getInstruments(String pitch) {
    List<String> instruments = null;
    try {
      instruments = SharcUtils.getInstrumentsForPitch(pitch);
    } catch (SharcException e) {
      e.printStackTrace();
    }
    String[] result = instruments.toArray(new String[] {});
    return(instruments);
	}
}
