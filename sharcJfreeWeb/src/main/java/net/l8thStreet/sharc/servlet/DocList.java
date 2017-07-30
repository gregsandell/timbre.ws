package net.l8thStreet.sharc.servlet;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jdom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcXmlException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.*;
import net.l8thStreet.sharc.singleton.SessionManager;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.singleton.UserManager;
import net.l8thStreet.sharc.audit.Session;
import net.l8thStreet.sharc.audit.User;
import net.l8thStreet.sharc.service.Noteplot;
import net.l8thStreet.sharc.service.TwoNoteplot;
import net.l8thStreet.sharc.service.LongtimePlot;
import net.l8thStreet.sharc.service.ServiceFunctions;
import net.street18.util.XpathPruner;
import net.street18.exception.l8thStreetException;

public class DocList extends SharcBaseServlet {
  private static Logger LOGGER = Logger.getLogger(DocList.class);
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      try {
	          doService(request, response);
	      }
	        catch (ServletException e) {
	          LOGGER.error("SHARC EXCEPTION; see stack trace");
	          e.printStackTrace();
	          request.setAttribute(SharcConstants.KEY_SHARC_EXCEPTION, e);
	          RequestDispatcher dispatcher =
	            request.getRequestDispatcher("/jsp/sharcException.jsp");
	          if (dispatcher != null ) {
	            dispatcher.forward(request, response);
	         }
	       }
	    }
	    private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/docList.jsp");
				if (dispatcher != null ) {
					dispatcher.forward(request, response);
				}
	    }
}
