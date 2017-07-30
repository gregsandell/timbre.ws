package net.l8thStreet.sharc.xmlObject;

import org.jdom.Element;
import org.jdom.Document;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcValidate;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: greg
 * Date: Oct 13, 2007
 * Time: 1:17:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Instrument extends Element implements InstrumentInf {
  private Element instrument;
  public Instrument(String inst) throws SharcException {
    // This should be using the caching in SharcXmlSingleton!
    SharcValidate.notNullArg(inst, "inst");
    String filePath = SharcUtils.getXmlFilePath(inst);
    Document sharcDoc = SharcXmlSingleton.getInstance().getSharcJdomDocument(filePath);
    Element tree = sharcDoc.getRootElement();
    List<Element> insts = tree.getChildren("instrument");
    // LOGGER.info(insts.size() + " instruments");
    instrument = null;
    for (int i = 0; i < insts.size() && instrument == null; i++) {
      Element e = insts.get(i);
      if (e.getAttributeValue("id").equals(inst)) {
          instrument = e;
      }
    }
    SharcValidate.notNullLocalVar(instrument, "instrument", "The <instrument> node for '" + inst + "' may not be in the expected xml file.");
  }
  public Instrument(Element inst) {
    this.instrument = inst;
  }
  public Note getNote(String pitch) throws SharcException {
    SharcValidate.notNullArg(pitch, "pitch");
    List<Note> notes = getSharcNotes();
    SharcValidate.notNullLocalVar(notes, "notes");
    Note note = null;
    for (int i = 0; i < notes.size()  && note == null; i++) {
      Note e = notes.get(i);
      if (e.getPitch().equals(pitch)) {
        note = e;
      }
    }
    SharcValidate.notNullLocalVar(note, "note", "The <note> node for '" + pitch + "' may not be in the expected xml file");
    return(note);
  }
  public List<Note> getSharcNotes() throws SharcException {
    List<Note> result = new ArrayList<Note>();
/*    for (Object e:instrument.getChildren("note"))  {
      result.add(new Note((Element)e));
    }     */
    List<Element> notes = instrument.getChildren("note");
      for (Element note:notes)  {
        result.add(new Note(note));
      }
    return(result);
  }
  public List<String> getNoteList()  throws SharcException{
    List<String> result = new ArrayList<String>();
    List<Note> notes = getSharcNotes();
    for (Note note:notes) {
      result.add(note.getPitch());
    }
    return(result);
  }
  public String getFullName() {
    return(instrument.getAttributeValue("name"));
  }
  public int getLowKeyNum() {
    return((new Integer(instrument.getChild("ranges").getChild("lowest").getChild("pitch").getAttributeValue("keyNum"))).intValue());
  }
  public int getHighKeyNum() {
    return((new Integer(instrument.getChild("ranges").getChild("highest").getChild("pitch").getAttributeValue("keyNum"))).intValue());
  }
}
