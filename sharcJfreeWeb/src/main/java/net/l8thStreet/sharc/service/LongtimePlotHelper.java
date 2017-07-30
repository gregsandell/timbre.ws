package net.l8thStreet.sharc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.apache.commons.lang.StringUtils;
import net.l8thStreet.sharc.exceptions.*;
import net.l8thStreet.sharc.*;
import net.l8thStreet.sharc.xmlObject.*;


public class LongtimePlotHelper
{
  private String inst;
  private int bins;
  private double minFreq;
  private double maxFreq;
  private String weirdFreqsMsg;
  private BinManager binManager;

  public LongtimePlotHelper(HttpServletRequest request)  throws SharcException   {
    inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
    bins = Integer.parseInt(request.getParameter(SharcConstants.PARAM_LONGTIME_BINS));
    minFreq = 0.0;
    maxFreq = SharcConstants.ceilingFreq;
    if (!StringUtils.isEmpty(request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ)))      {
      minFreq =
        Double.valueOf(request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ)).doubleValue();
    }
    if (!StringUtils.isEmpty(request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ)))     {
      maxFreq =
        Double.valueOf(request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ)).doubleValue();
    }
    binManager = new BinManager(bins, minFreq, maxFreq);
    binManager.makeBins();
    weirdFreqsMsg = " either (1) the values for the " +
                           "minimum and maximum frequencies to measure over (" + minFreq + " and " +
                           maxFreq + ") " +
                           "are too close, or (2) no harmonics for the instrument(s) in question fall in that range";
  }



  public XYSeriesCollection makeLongtimeDataset() throws SharcException
  {
    XYSeries series1 = makeLongtimeXYseries();
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    return (dataset);

  }

  public JFreeChart makeLongtimeChart()
    throws SharcException
  {
    XYSeriesCollection dataset = makeLongtimeDataset();
    return (SharcChartUtils.standardSharcChart("Longtime spectrum",
                                               "Center Frequency (Hz)", "amplitude (dB)", dataset));
  }



  public XYSeries makeLongtimeXYseries()
    throws SharcException
  {
    // binManager.makeCriticalBandBins();
    if (binManager.getNumBins() == 0)
    {
      throw new SharcMathException("Zero bins were calculated, probably because " + weirdFreqsMsg);
    }
    addHarmonicEnergyToBins();
    XYSeries series1 = new XYSeries(inst);
    if (binManager.getMaxSumAmp() == 0.0)
    {
      throw new SharcMathException("The maximum amplitude of the " + binManager.getNumBins() +
                                   " bins measured was zero, leaving nothing to calculate.  This may be because " +
                                   weirdFreqsMsg);
    }
    // Convert amplitudes to dbs, add to series
    for (int i = 0; i < binManager.bins.size(); i++)
    {
      AmplitudeBin bin = binManager.getBin(i);
      double centerFreq = (bin.getLowFreq() + bin.getHighFreq()) / 2.0;
      double dbAmp = bin.getDbSum(binManager.getMaxSumAmp());
      if (dbAmp != Double.NEGATIVE_INFINITY)
      {
        /*  Important note.  If a bin at a frequency gets no contributions, it will have its initial
            value of Double.NEGATIVE_INFINITY, and will not be added to the plot.  Therefore a user could
            request a certain number of bins but actually far fewer will be plotted.
        */
        series1.add(centerFreq, dbAmp);
      }
    }
    return series1;
  }
  public void addHarmonicEnergyToBins() throws SharcException
  {
    if (binManager.getNumBins() == 0)
    {
      throw new SharcMathException("Zero bins were calculated, probably because " + weirdFreqsMsg);
    }
    InstrumentInf instrument = new Instrument(inst);
    List<Note> notes = instrument.getSharcNotes();
    // Add harmonic energy to bins
    for (int i = 0; i < notes.size(); i++)
    {
      NoteInf note = notes.get(i);
      List<Harmonic> harmonics = note.getSharcHarmonics();
      for (int j = 0; j < harmonics.size(); j++)
      {
        HarmonicInf harmonic = harmonics.get(j);
        double freq = harmonic.getFrequency();
        if (freq >= minFreq && freq <= maxFreq)
        {
          binManager.add(freq, harmonic.getAmplitude());
        }
      }
    }
  }

  public void setInst(String inst)
  {
    this.inst = inst;
  }
}