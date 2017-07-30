package net.l8thStreet.sharc.service;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcImageException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.*;
import net.l8thStreet.sharc.xmlObject.*;
import net.street18.util.FileSystemUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.xy.XYSeries;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;

public class ServiceFunctions {
  private static Logger LOGGER = Logger.getLogger(ServiceFunctions.class);

  public static void plotToBrowser(HttpServletRequest request, HttpServletResponse response, JFreeChart chart, ChartRenderingInfo info, int width, int height) throws SharcException {
		SharcValidate.notNullArg(response, "response");
		SharcValidate.notNullArg(chart, "chart");
		SharcValidate.notNullArg(info, "info");
		response.setContentType("image/png");
		try {
			OutputStream os = response.getOutputStream();
      // TODO Decide if we're going to manage byte cost
      // File file1 = new File("c:\\temp\\temp.png");
			// ChartUtilities.saveChartAsJPEG(file1, chart, width, height, info);
			// int numBytes = FileSystemUtils.getStringFromFileObj(file1).length();
			// UserManager.getInstance().get(request).addByteCost(numBytes);
			ChartUtilities.writeChartAsPNG(os, chart, width, height, info);
			os.flush();
		}
		catch (IOException e) {
			throw new SharcImageException("Unknown failure during call to getOutputStream(), writeChartASPNG() or flush()",
					"System Error");
		}
	}
   public static void plotToBrowser(HttpServletRequest request, HttpServletResponse response, JFreeChart chart, ChartRenderingInfo info) throws SharcException {
		 plotToBrowser(request, response, chart, info, 600, 400);
   }
     public static XYSeries makeSharcNoteXYseries(String legendText, NoteInf note) throws SharcException  {
      double fundHz = note.getFundHz();
      double maxAmp = note.getMaxAmp();
			double minAmp = note.getMinAmp();
      XYSeries series1 = new XYSeries(legendText);
      List<Harmonic> harmonics = note.getSharcHarmonics();
      LOGGER.info("Preparing a plot: fundHz = " + fundHz + ", maxAmp = " + maxAmp +
        ", number of harmonics = " + harmonics.size());
      for (HarmonicInf h:harmonics) {
        series1.add(h.getFrequency(),  h.getDb() - SharcUtils.linToDb(minAmp, maxAmp));
      }
      return(series1);
    }

	public static double toDouble(String d) throws SharcException {
    return(SharcUtils.toDouble(d));
  }

  public static String notePlotLegend(String numHarms, String fund)    {
    return("Harmonics 1-" +numHarms + " (F0 = " + fund + " Hz.)");
  }
  public static boolean isEmptyParam(HttpServletRequest request, String param) {
    return(StringUtils.isEmpty(request.getParameter(param)));
  }
  public static String getUrlPre(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    String remoteHost = request.getRemoteHost();
    remoteHost = "localhost";
    int serverPort = request.getServerPort();
    serverPort = 8080;
    String contextPath = request.getContextPath();
    contextPath = "/sharcJfreeWeb";
    String result = sb.append("http://").append(remoteHost)
      .append(":").append(serverPort)
      .append(contextPath)
      .append("/servlet/SharcService?").toString();
    return(result);
  }
  public static String prependRootUrl(HttpServletRequest request,
                                  String queryString) {
    return(getUrlPre(request) + queryString);
  }
  public static String makeUrlLink(HttpServletRequest request, String queryString) {
    String url = prependRootUrl(request, queryString);
    return("<a href='" + url + "'>" + StringEscapeUtils.escapeHtml(url) + "</a>");
  }
  public static String getDocUrl(HttpServletRequest request, String action) throws SharcException {
    if (!ServiceFunctions.isKnownAction(action)) {
      throw new SharcHttpQueryException("'" + action + "' is not a registered service",
        SharcConstants.USER_SYSTEMERR_MSG);
    }
    return(prependRootUrl(request, "action=" + action + "&doc="));
  }
	public static String getDocLink(HttpServletRequest request, String action) throws SharcException {
		String url = getDocUrl(request, action);
		return("<a href='" + url + "'>" + action + "</a>");
	}
	public static boolean isKnownAction(String action) {
    return(SharcConstants.knownActions.contains(action));
  }

	public static String getRequestedUrl(HttpServletRequest request) {
		 return(request.getRequestURI());
	}
}
