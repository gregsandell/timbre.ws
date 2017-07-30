package net.l8thStreet.sharc.xmlObject;

import org.jdom.Element;
import org.jdom.DataConversionException;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcValidate;
import net.l8thStreet.sharc.SharcUtils;

import java.util.List;
import java.util.ArrayList;


public class Note extends Element implements NoteInf {
  private Element note;
  private double fundHz;
  public Note(Element n) {
    note = n;
  }                                                                                                
  public String getPitch()  throws SharcException {
    return(note.getAttributeValue("pitch"));
  }
  public String toString() {
      try
      {
          String p = getPitch();
          int n = getNumHarms();
          String k = getKeyNum();
          return("pitch " + p + ", fundHz = " + getFundHz() + ", numHarms = " + n + ", keynum = " + k);

      }
      catch (SharcException e)
      {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      return("");
  }
  public List<Harmonic> getSharcHarmonics() throws SharcException {
    List<Harmonic> result = new ArrayList<Harmonic>();
    for (Object o:note.getChildren("a")) {
      result.add(new Harmonic((Element)o, getFundHz(), getMaxAmp()));
    }
    return(result);
  }
  public double getFundHz() throws SharcException {
    return(SharcUtils.toDouble(note.getAttributeValue("fundHz")));
  }
  public double getMaxAmp()  throws SharcException{
    return(SharcUtils.toDouble(note.getChild("ranges").getChild("highest").getChild("amplitude").getText()));
  }

  public double getMinAmp()  throws SharcException{
    return(SharcUtils.toDouble(note.getChild("ranges").getChild("lowest").getChild("amplitude").getText()));
  }
  public String getKeyNum()  throws SharcException {
    return(note.getAttributeValue("keyNum"));
  }
  public int getNumHarms() throws SharcException {
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
