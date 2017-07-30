package net.l8thStreet.sharc.dwr;

import net.l8thStreet.sharc.SharcUtils;

import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class AllPitches {
  private static Logger LOGGER = Logger.getLogger(AllPitches.class);
	public String[] getPitches() {
		return(SharcUtils.allPitches);
	}
}
