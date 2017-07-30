package net.l8thStreet.sharc;

import org.jdom.Element;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.XYPlot;

import java.util.List;
import java.util.ArrayList;
import net.l8thStreet.sharc.exceptions.SharcCastException;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.xmlObject.*;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 22, 2006
 * Time: 10:16:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CentroidSeries {
  private String inst;
  private List<CentroidPoint> centroids;
  public CentroidSeries(String inst) {
    this.inst = inst;
  }
  public void make()  throws SharcException {
    InstrumentInf instrument = new Instrument(this.inst);
    List<Note> notes = instrument.getSharcNotes();
    centroids = new ArrayList<CentroidPoint>();
    for (int i = 0; i < notes.size(); i++) {
      NoteInf note = notes.get(i);
      String keynum = note.getKeyNum();
      String pitch = note.getPitch();
      double fundHz = note.getFundHz();
      List<Harmonic> harmonics = note.getSharcHarmonics();
      CentroidAccumulator centroidAccumulator = new CentroidAccumulator();
      centroidAccumulator.add(harmonics, fundHz);
      centroids.add(new CentroidPoint(SharcUtils.toDouble(keynum),
        centroidAccumulator.getCentroid(), pitch));
    }
  }
  // TODO Eliminate dependence of addToPlot() on make() being called first
  public void addToPlot(XYPlot plot) throws SharcException {
    SharcValidate.notNullClassVar(centroids, "addToPlot() called before make(), apparently");
    for (int i = 0; i < this.getNumCentroids(); i++) {
      CentroidPoint p = this.getCentroidPoint(i);
      XYTextAnnotation xyt = new XYTextAnnotation(p.pitch, p.keynum, p.centroid);
      plot.addAnnotation(xyt);
    }
  }
  public CentroidPoint getCentroidPoint(int i) throws SharcException {
    CentroidPoint cp = null;
        cp = centroids.get(i);
        return(cp);

  }
  public int getNumCentroids() throws SharcException {
    SharcValidate.notNullClassVar(centroids, "centroids");
    return(centroids.size());
  }
}
