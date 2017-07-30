package net.l8thStreet.sharc;

import net.l8thStreet.sharc.exceptions.SharcMathException;
import net.l8thStreet.sharc.exceptions.SharcException;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 18, 2006
 * Time: 9:04:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmplitudeBin {
	private static Logger LOGGER = Logger.getLogger(AmplitudeBin.class);
  private double lowFreq;
  private double highFreq;
  private double sum;
  public AmplitudeBin(double lowFreq, double highFreq) {
    this.lowFreq = lowFreq;
    this.highFreq = highFreq;
    sum = 0.0;
  }
  public boolean isFit(double freq)  {
		/* if (freq >= lowFreq && freq < highFreq)  {
				LOGGER.info("YES " + freq + " fit between " + lowFreq + " and " + highFreq);
		}
			else {
				LOGGER.info("NO  " + freq + " fit between " + lowFreq + " and " + highFreq);
		} */
		return(freq >= lowFreq && freq < highFreq);
  }
  public boolean addSafe(double freq, double amp) {
    boolean result = false;
    if (isFit(freq))  {
      add(amp);
      result = true;
    }                                          
    return(result);
  }
  public void add(double amp) {
    sum += amp;
  }
  public double getSum() {
    return(this.sum);
  }
  public double getDbSum(double maxAmp) throws SharcException
  {
    return(SharcUtils.linToDb(this.sum, maxAmp));
  }
  public double getLowFreq() {
    return lowFreq;
  }

  public double getHighFreq() {
    return highFreq;
  }
}
