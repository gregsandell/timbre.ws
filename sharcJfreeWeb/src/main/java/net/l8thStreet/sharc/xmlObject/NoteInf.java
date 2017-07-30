package net.l8thStreet.sharc.xmlObject;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: greg
 * Date: Oct 13, 2007
 * Time: 1:20:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NoteInf {
  public String getPitch()  throws SharcException;
  public double getFundHz() throws SharcException;
  public double getMaxAmp() throws SharcException;
  public double getMinAmp() throws SharcException;
  public String getKeyNum()  throws SharcException;
  public int getNumHarms() throws SharcException;
  public List<Harmonic> getSharcHarmonics() throws SharcException;
}
