/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 6, 2006
 * Time: 10:43:54 AM
 * To change this template use File | Settings | File Templates.
 */
package net.l8thStreet.sharc.singleton;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcValidate;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcFileException;
import net.l8thStreet.sharc.exceptions.SharcXmlException;
import net.street18.exception.l8thStreetException;
import net.street18.util.JdomUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import java.util.*;
import java.io.File;


public class SharcXmlSingleton  {
  private static Logger LOGGER = Logger.getLogger(SharcXmlSingleton.class);
  private static SharcXmlSingleton ourInstance = null;
  /*  None of these are in use.
  private HashMap xmlStringMap;
  private HashMap inputSourceMap;
  private HashMap jdomDocumentMap;
  */
  private Map<String, String> noteCatalog;
  private List<String> instCatalog;
  private SharcXmlSingleton()  {
    /*  These are not in use.
    xmlStringMap = new HashMap();
    inputSourceMap = new HashMap();
    */
    noteCatalog = new HashMap<String,String>();
    instCatalog = new ArrayList<String>();
    init();
  }

    /**
     * Create the noteCatalog and instCatalog from the sharc.xml file
     */
  public void init() {
    try {
        File sharcFile = new File(getAllSharcFilePath());
        if (!sharcFile.exists()) {
            throw new SharcFileException("File [" + sharcFile.getAbsolutePath() +
                "] not found.  Check the path in the resource file 'sharc.development.properties'",
                "The main file containing SHARC data could not be found");
        }
        Document doc = JdomUtils.filePathToDoc(getAllSharcFilePath());
        Element tree = doc.getRootElement();
        List insts = tree.getChildren("instrument");
        for (int i = 0; i < insts.size(); i++){
          Element inst = (Element)insts.get(i);
          String instId = inst.getAttributeValue("id");
          //  LOGGER.info("instId = [" + instId + "]");
          String noteList = inst.getChild("ranges").getChild("pitches").getText();
          noteCatalog.put(instId, noteList);
        }
        String instList = tree.getChild("ranges").getChild("instruments").getText();
        CollectionUtils.addAll(instCatalog, instList.split(","));
        // Add "sharc" as a way to retrieve the full xml file (sharc.xml)
				// ugh, is this really useful??
				instCatalog.add("sharc");
        doc = null;
        tree = null;
        instList = null;
        insts = null;
    }
      catch (l8thStreetException e) {
        LOGGER.error("Problem parsing file " + getAllSharcFilePath());
        e.printStackTrace();
      }
    catch (SharcFileException e)
    {
        e.printStackTrace();  
    }
      LOGGER.info("Singleton instance created");
  }

    public Map<String, String> getNoteCatalog()
    {
        return noteCatalog;
    }

    public List<String> getInstCatalog()
    {
        return instCatalog;
    }

    public synchronized static SharcXmlSingleton getInstance()  {
    if (ourInstance == null)   {
      ourInstance = new SharcXmlSingleton();
    }
    return(ourInstance);
  }
  /*
     This is not in use and shouldn't be....xmlStringMap does not
     contain InputSource's!
  public InputSource getXmlInputSource(String filePath) throws SharcException {
    InputSource result = null;
    String kkey = filePath.equals("sharc") ? getAllSharcFilePath() : filePath;
    if (!xmlStringMap.containsKey(kkey)) {
      xmlStringMap.put(filePath, getSharcInputSource(kkey));
      LOGGER.info("First retrieval of file [" + filePath + "] has been cached");
    }
    result = (InputSource)inputSourceMap.get(kkey);
    LOGGER.info("A cached instance of file [" + kkey + "] has been retrieved");
    return(result);
  }
   */
  /*
      This is not in use and shouldn't be
  public Document getJdomDocument(String filePath) throws SharcException {
    Document result = null;
    String kkey = filePath.equals("sharc") ? getAllSharcFilePath() : filePath;
    if (!jdomDocumentMap.containsKey(kkey)) {
      jdomDocumentMap.put(filePath, getSharcInputSource(kkey));
      LOGGER.info("First retrieval of file [" + filePath + "] has been cached");
    }
    result = (Document)jdomDocumentMap.get(kkey);
    LOGGER.info("A cached instance of file [" + kkey + "] has been retrieved");
    return(result);
  }
   */
  /*
      This is not in use
  public String getXmlString(String filePath) throws SharcException {
    String result = "";
    String kkey = filePath.equals("sharc") ? getAllSharcFilePath() : filePath;
    if (!xmlStringMap.containsKey(kkey)) {
      xmlStringMap.put(filePath, getSharcXmlString(kkey));
      LOGGER.info("First retrieval of file [" + filePath + "] has been cached");
    }
    result = (String)xmlStringMap.get(kkey);
    LOGGER.info("A cached instance of file [" + kkey + "] has been retrieved");
    return(result);
  }
   */
  /*
    This is not in use.
  private String getSharcXmlString(String path) throws SharcException {
    String s = "";
    try {
      s = FileSystemUtils.getStringFromFile(path);
    } catch (l8thStreetException e) {
        e.printStackTrace();
      throw new SharcFileException("Error reading file [" + path + "];" +
        " description = " + e.getMessage(),
        "There was an error caused when the system attempted to read a file from the " +
        "webserver.  The error is a flaw in the SHARC query system.");
    }
    return(s);
  }
   */
  public Document getSharcJdomDocument(String path) throws SharcException {
    Document result = null;
    try {
        result = JdomUtils.filePathToDoc(path);
    }
      catch (l8thStreetException e) {
        e.printStackTrace();
        throw new SharcFileException("Error reading file [" + path + "];" +
          " description = " + e.getMessage(),
          "There was an error caused when the system attempted to read a file from the " +
          "webserver.  The error is a flaw in the SHARC query system.");
      }
      finally {
        return(result);
    }
  }
  /*
  Not in use
  public InputSource getSharcInputSource(String path) throws SharcException {
    InputSource in = null;
    try {
        in = SAXutils.filePathToInputSource(path);
    }
      catch (l8thStreetException e) {
        e.printStackTrace();
        throw new SharcFileException("Error reading file [" + path + "];" +
          " description = " + e.getMessage(),
          "There was an error caused when the system attempted to read a file from the " +
          "webserver.  The error is a flaw in the SHARC query system.");
      }
      finally {
        return(in);
    }
  }
   */

	/**
	 * Returns true if the note is available for the instrument.
	 * @param inst
	 * @param pitch
	 * @return
	 * @throws SharcXmlException
	 */
	public boolean instHasNote(String inst, String pitch) throws SharcException {
    SharcValidate.notNullArg(inst, "instrument");
    SharcValidate.notNullArg(pitch, "pitch");
    if(!isInstrument(inst)) {
      throw new SharcXmlException("There is no instrument called [" + inst +
        "] in SHARC", "There is no instrument called [" + inst +
        "] in SHARC");
    }
    return(instPitchList(inst).indexOf(pitch) != -1);
  }

	/**
	 * Returns true if the String is an Xml-friendly pitch.
	 * @param pitch
	 * @return
	 */
	public boolean isPitch(String pitch) {
    return(pitch.matches("^\\s*[abcdefg]s?[0123456789]\\s*$") &&
      !pitch.matches("^\\s*bs?[0123456789]\\s*$") &&
      !pitch.matches("^\\s*es?[0123456789]\\s*$"));
  }

	/**
	 * Converts a pitch from the xml-friendly "cs" format to
	 * music-friendly "c#" (for example).
	 * @param pitch
	 * @return
	 */
	public String hashmarkPitch(String pitch) {
    return(pitch.replace('s','#'));
  }

	/**
	 * Returns true if the instId passed (e.g. "violin_vibrato",
	 * "_c_trumpet_muted", etc.) is an instrument in the collection.
	 * @param instId
	 * @return
	 */
	public boolean isInstrument(String instId) {
    return(instCatalog.contains(instId));
  }

	/**
	 * Returns a String of all the pitches available for this
	 * instrument, each pitch separated by a space.
	 * @param inst
	 * @return
	 * @throws SharcXmlException
	 */
	public String instPitchList(String inst) throws SharcException {
    SharcValidate.notNullArg(inst, "instrument");
    if(!instCatalog.contains(inst)) {
      throw new SharcXmlException("There is no instrument called [" + inst +
        "] in SHARC", "There is no instrument called [" + inst +
        "] in SHARC");
    }
    return((String)noteCatalog.get(inst));
  }

  public List getInstrumentList() {
    return(instCatalog);
  }
	public List<String> getInstrumentList(boolean includeSharc) {
		if (includeSharc) {
			return(getInstrumentList());
		}
		List<String> copy = new ArrayList<String>(instCatalog);
		copy.remove("sharc");
		return(copy);
  }
	public String getAllSharcFilePath() {
    return(PropertyConfigurator.getInstance().getSharcRootPath() + SharcConstants.ALLSHARC_FILENAME);
  }
}
