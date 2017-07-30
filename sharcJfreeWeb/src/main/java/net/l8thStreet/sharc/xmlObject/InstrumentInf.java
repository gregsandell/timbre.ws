package net.l8thStreet.sharc.xmlObject;

import net.l8thStreet.sharc.exceptions.SharcException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: greg
 * Date: Oct 13, 2007
 * Time: 1:17:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InstrumentInf {
  public Note getNote(String pitch) throws SharcException;
  public List<Note> getSharcNotes() throws SharcException;
  public List<String> getNoteList()  throws SharcException;
  public String getFullName();
  public int getLowKeyNum();
  public int getHighKeyNum();
}
