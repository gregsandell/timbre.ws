package net.l8thStreet.sharc;

import org.jdom.Element;

import java.util.List;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.xmlObject.Harmonic;
import net.l8thStreet.sharc.xmlObject.HarmonicInf;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 22, 2006
 * Time: 2:59:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CentroidAccumulator {
  private double sumAmp;
  private double sumAmpFreq;
  public CentroidAccumulator() {
    sumAmp = 0.0;
    sumAmpFreq = 0.0;
  }
  public void add(List<Harmonic> harmonics, double fundHz) throws SharcException  {
    for (int j = 0; j < harmonics.size(); j++) {
      HarmonicInf harmonic = harmonics.get(j);
      double amp = harmonic.getAmplitude();
      sumAmp += amp;
      sumAmpFreq += amp * harmonic.getFrequency();
    }
  }
  public double getCentroid() {
    return(sumAmpFreq/sumAmp);
  }

}
