package net.l8thStreet.sharc.xmlObject;

import org.jdom.Element;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.SharcValidate;
import net.l8thStreet.sharc.SharcUtils;

/**
 * Created by IntelliJ IDEA.
 * User: greg
 * Date: Oct 13, 2007
 * Time: 8:21:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Harmonic extends Element implements HarmonicInf {
  private Element harmonic;
  private double fundHz;
  private double maxAmp;
  public Harmonic(Element e, double f, double m) {
    harmonic = e;
    fundHz = f;
    maxAmp = m;
  }
  public double getFrequency() throws SharcException {
    return(fundHz * SharcUtils.toDouble(harmonic.getAttributeValue("n")));
  }
  public double getAmplitude()  throws SharcException {
    return(SharcUtils.toDouble(harmonic.getText()));
  }
  public double getPhase()  throws SharcException {
    return(SharcUtils.toDouble(harmonic.getAttributeValue("p")));
  }
  public double getDb() throws SharcException {
    return(SharcUtils.linToDb(SharcUtils.toDouble(harmonic.getText()), maxAmp));
  }

}
