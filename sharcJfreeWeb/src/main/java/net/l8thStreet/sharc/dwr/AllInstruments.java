package net.l8thStreet.sharc.dwr;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;

public class AllInstruments {
  private static Logger LOGGER = Logger.getLogger(AllInstruments.class);
	public List<String> getInstruments() {
		List<String> insts = SharcXmlSingleton.getInstance().getInstrumentList();
		//return insts.toArray(new String[]{});
    return insts;
	}
}
