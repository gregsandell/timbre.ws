package net.l8thStreet.sharc.xmlObject;

import net.l8thStreet.sharc.exceptions.SharcException;

/**
 * Created by IntelliJ IDEA.
 * User: greg
 * Date: Oct 13, 2007
 * Time: 8:21:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HarmonicInf {
  public double getFrequency() throws SharcException;
  public double getAmplitude()  throws SharcException;
  public double getPhase()  throws SharcException;
  public double getDb() throws SharcException;
}
