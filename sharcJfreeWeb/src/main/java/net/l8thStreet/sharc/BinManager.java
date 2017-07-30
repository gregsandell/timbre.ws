package net.l8thStreet.sharc;

import net.l8thStreet.sharc.exceptions.SharcMathException;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 18, 2006
 * Time: 9:20:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinManager {
  private static Logger LOGGER = Logger.getLogger(BinManager.class);
  private int numBins;

  public  final double whisker = 0.000001;
  private static final double NO_VALUE = -1.0;
  private double maxSumAmp;
  private double minFreq;
  private double maxFreq;
	private double[] criticalBandUpperEnds =
			{50, 95, 140, 235, 330, 420, 560, 660, 800, 940, 1125, 1265, 1500, 1735,
			  1970, 2340, 2720, 3280, 3840, 4690, 5440, 6375, 7690, 9375, 11625, 15375, 20250};
	public List<AmplitudeBin> bins;
  public BinManager(int numBins) {
    this(numBins, 0.0, SharcConstants.ceilingFreq);
  }
  public BinManager(int numBins, double minFreq, double maxFreq) {
    this.numBins = numBins;
    setMinFreq(minFreq);
    setMaxFreq(maxFreq);
    bins = new ArrayList<AmplitudeBin>();
    maxSumAmp = NO_VALUE;
  }
  public void makeBins() {
    double binWidth = (maxFreq - minFreq)/(double)numBins;
    for (double d = minFreq; d < maxFreq; d += binWidth)  {
      bins.add(new AmplitudeBin(d, d + (binWidth - whisker)));
    }
  }
	public void makeCriticalBandBins() {
		bins = new ArrayList<AmplitudeBin>();
		for (int i = 0; i < criticalBandUpperEnds.length; i++) {
			double low = (i == 0) ? 0 : criticalBandUpperEnds[i-1];
			bins.add(new AmplitudeBin(low, criticalBandUpperEnds[i]));
		}
		
	}
	public int getNumBins() {
    return(bins.size());
  }
  public void add(double frequency, double amplitude) throws SharcMathException {
    boolean found = false;
    AmplitudeBin bin;
    for (int i = 0; i < bins.size() && !found; i++) {
      bin = bins.get(i);
      found = bin.addSafe(frequency, amplitude);
    }
    if (!found) {
      // Because the ceiling freq encompasses the highest known frequency in SHARC, we should only get
      // here through a computational error in calculating the bin limits.
      throw new SharcMathException("No bin could be found for frequency [" + frequency + "]; something is wrong with ranges.",
        "A math error in processing frequency information occurred.");
    }
  }
  public double findMaxSumAmp() {
    for (int i = 0; i < bins.size(); i++) {
      double max = bins.get(i).getSum();
      if (max > maxSumAmp) {
        maxSumAmp = max;
      }
    }
    return(maxSumAmp);
  }
  /*
      Not in use.
  public List getSpectrum() throws SharcMathException {
    List result = new ArrayList();
    findMaxSumAmp();
    for (int i = 0; i < bins.size(); i++) {
      AmplitudeBin bin = bins.get(i);
      result.add(new Double(bin.getDbSum(maxSumAmp)));
    }
    return(result);
  }
   */
  public AmplitudeBin getBin(int i) {
    return(bins.get(i));
  }
  public static void main (String[] args) throws Exception {
    BinManager bMan = new BinManager(50);
    AmplitudeBin bin;
    for (int i = 0; i < bMan.bins.size(); i++)  {
      bin = bMan.getBin(i);
      LOGGER.info("Bin " + i + ": " + bin.getLowFreq() + " to " + bin.getHighFreq() + " Hz.");
    }
   }

  public double getMaxSumAmp() {
    // Lazy loader
    if (maxSumAmp == NO_VALUE) {
      maxSumAmp = findMaxSumAmp();
    }
    return maxSumAmp;
  }

	public double getMinFreq() {
		return minFreq;
	}

	public void setMinFreq(double minFreq) {
		this.minFreq = minFreq;
	}

	public double getMaxFreq() {
		return maxFreq;
	}

	public void setMaxFreq(double maxFreq) {
		this.maxFreq = maxFreq;
	}
}
