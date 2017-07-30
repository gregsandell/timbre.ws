package net.l8thStreet.sharc;
import net.l8thStreet.sharc.servlet.EchoList;

public class SharcConstants {
  public static final int NUM_INSTRUMENTS = 39;
  public static final String[] prodDomains = {
    "timbre.ws","18thstreet.net","gregsandell.com","carolannsandell.com"
  };
  //public static final List knownInstruments = new ArrayList();

  // These are used to validate query strings (tell user when unknown parameters
  // or values are being used.
  public static final EchoList knownActions = new EchoList();
  public static final EchoList knownParams = new EchoList();

  //   Actions
  public static final String ACTION_XPATHXML = knownActions.addIt("XpathXml");
  public static final String ACTION_NOTEPLOT = knownActions.addIt("Noteplot");
  public static final String ACTION_TWONOTEPLOT = knownActions.addIt("TwoNoteplot");
  public static final String ACTION_LONGTIMEPLOT = knownActions.addIt("LongtimePlot");
  public static final String ACTION_LONGTIMETWOPLOT = knownActions.addIt("LongtimeTwoplot");
  public static final String ACTION_CENTROIDPLOT = knownActions.addIt("CentroidPlot");
  public static final String ACTION_CENTROIDTWOPLOT = knownActions.addIt("CentroidTwoplot");
  public static final String ACTION_CENTROIDALLINSTRUMENTS = knownActions.addIt("centroidallinstruments");
	public static final String ACTION_WAVEPLOT = knownActions.addIt("WavePlot");
	public static final String ACTION_PITCHRANGES = knownActions.addIt("PitchRanges");

  public static final String COOKIE_FIRSTSESSION_PARAM = "firstSessionId";
  // Params
  public static final String PARAM_ACTION = knownParams.addIt("action");
  public static final String PARAM_DOCUMENTATION = knownParams.addIt("doc");
  public static final String PARAM_INSTRUMENT = knownParams.addIt("inst");
  public static final String PARAM_INSTRUMENT1 = knownParams.addIt("inst1");
  public static final String PARAM_INSTRUMENT2 = knownParams.addIt("inst2");
  public static final String PARAM_PITCH = knownParams.addIt("pitch");
  public static final String PARAM_XPATH = knownParams.addIt("xpath");
  public static final String PARAM_LONGTIME_BINS = knownParams.addIt("bins");
  public static final String PARAM_LONGTIME_LOWFREQ = knownParams.addIt("lowFreq");
  public static final String PARAM_LONGTIME_HIGHFREQ = knownParams.addIt("highFreq");
  public static final String ALLSHARC_FILENAME = "sharc.xml";
  //public static final String ALLXML_FILEPATH = SHARCXML_ROOT_PATH + "sharc.xml";

  public static final String SHARC_PROPERTIES_XML_FILE = "sharc.properties.xml";
  public static final String SHARC_ROOTPATH_PROP = "sharcRootpath";

  public static final String USER_SYSTEMERR_MSG = "System error requiring a code fix.  Sorry...try again later.";
  
  // Why 11221.0?  Because this is just above the highest frequency found in the entire sharc collection.
  // The distinction belonds to CB_martele, note g1, harmonic number 229, and the exact value is 11220.77.
  public static final double ceilingFreq = 11221.0;
  public static final String KEY_DOC_STRING = "_docstring";
	public static final String KEY_SHARC_EXCEPTION = "_sharcException";
  public static final String  SERVICE_PACKAGE_PATH = "net.l8thStreet.sharc.service."; 
}

