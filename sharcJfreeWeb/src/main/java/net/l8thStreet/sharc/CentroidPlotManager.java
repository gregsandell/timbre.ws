package net.l8thStreet.sharc;

import net.l8thStreet.sharc.exceptions.*;

import java.util.List;
import java.util.ArrayList;
import org.jfree.chart.plot.XYPlot;
import org.apache.commons.lang.Validate;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 22, 2006
 * Time: 10:16:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class  CentroidPlotManager {
  private List<CentroidSeries> centroidPlots;
  private double maxCentroid = Double.MIN_VALUE;
  private double minCentroid = Double.MAX_VALUE;
  private double minKeynum = Double.MAX_VALUE;
  private double maxKeynum = Double.MIN_VALUE;
  public  CentroidPlotManager() {
    centroidPlots = new ArrayList<CentroidSeries>();
  }
  public void add(String inst) throws SharcException {
    Validate.notEmpty(inst, "Argument 'inst' (" + String.class.getName() + ") must be non-null and non-zero-length");
    CentroidSeries centroidSeries = new CentroidSeries(inst);
    centroidSeries.make();
    centroidPlots.add(centroidSeries);
  }
  public void plotAll(XYPlot plot) throws SharcException {
    SharcValidate.notNullArg(plot, "plot");
    for (int i = 0; i < centroidPlots.size(); i++)  {                                              
      centroidPlots.get(i).addToPlot(plot);
    }
  }
  public void getCentroidRanges() throws SharcException {
    SharcValidate.notNullClassVar(centroidPlots, "centroidPlots");
    for (int i = 0; i < centroidPlots.size(); i++)  {
      CentroidSeries cs = null;
      cs = centroidPlots.get(i);
      for (int j = 0; j < cs.getNumCentroids(); j++)  {
        CentroidPoint cp = cs.getCentroidPoint(j);
        if (cp.centroid > maxCentroid) maxCentroid = cp.centroid;
        if (cp.centroid < minCentroid) minCentroid = cp.centroid;
        if (cp.keynum > maxKeynum) maxKeynum = cp.keynum;
        if (cp.keynum < minKeynum) minKeynum = cp.keynum;
      }
    }
  }

  public CentroidSeries get(int i) {
    return(centroidPlots.get(i));
  }
  public double getMaxCentroid() throws SharcException {
    if (maxCentroid == Double.MIN_VALUE) getCentroidRanges();
    return maxCentroid;
  }

  public double getMinCentroid() throws SharcException {
    if (minCentroid == Double.MAX_VALUE) getCentroidRanges();
    return minCentroid;
  }

  public double getMinKeynum() throws SharcException {
    if (minKeynum == Double.MAX_VALUE) getCentroidRanges();
    return minKeynum;
  }

  public double getMaxKeynum() throws SharcException {
    if (maxKeynum == Double.MIN_VALUE) getCentroidRanges();
    return maxKeynum;
  }
}
