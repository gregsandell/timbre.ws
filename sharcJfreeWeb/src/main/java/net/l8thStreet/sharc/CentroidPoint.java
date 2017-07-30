package net.l8thStreet.sharc;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Aug 22, 2006
 * Time: 10:22:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class CentroidPoint {
  public double keynum, centroid;
  public String pitch;
  public CentroidPoint(double x, double y, String pitch) {
    this.keynum = x;
    this.centroid = y;
    this.pitch = pitch;
  }
}
