package net.l8thStreet.sharc.service;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.xmlObject.InstrumentInf;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.waveform.SynthFuncs;
import net.l8thStreet.sharc.exceptions.SharcException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PitchRanges extends ServiceBaseAction implements ServiceInterface  {
  private static Logger LOGGER = Logger.getLogger(PitchRanges.class);
  public static List<String> requiredParamList;
  public static List<String> alternateParamList;
  static {
		requiredParamList = new ArrayList<String>();
		alternateParamList = new ArrayList<String>();
  }
  public String getAction() {
    return(SharcConstants.ACTION_PITCHRANGES);
  }
  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException {

		// create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<String> instNames = SharcXmlSingleton.getInstance().getInstrumentList(false);
		for (String inst:instNames) {
      InstrumentInf instrument = new Instrument(inst);
      dataset.addValue(instrument.getLowKeyNum(),
					"Lowest Pitch", inst);
			dataset.addValue(instrument.getHighKeyNum(),
					"Highest Pitch", inst);
		}
		JFreeChart chart = ChartFactory.createBarChart(
						"Pitch Ranges of All Sharc Instruments",
            "Instrument",                  // domain axis label
            "Pitch Number",                 // range axis label
            dataset,                     // data
            PlotOrientation.HORIZONTAL,  // orientation
            true,                        // include legend
            true,
            false
        );
		// String title = fullInstName + " all (1000 samples per note)";
		// JFreeChart chart = SynthFuncs.makeAllNoteWavePlot(inst, title, 100, 22050);
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		info.setChartArea(new Rectangle(455, 600));
		ServiceFunctions.plotToBrowser(request, response, chart, info, 455, 600);
  }
  public String requirements(HttpServletRequest request) {
    StringBuffer sb = new StringBuffer();
    sb.append("<h1>Sharc Web Service '").append(SharcConstants.ACTION_WAVEPLOT).append("'</h1>")
    .append("<h2>Basics</h2>")
    .append("The service is specified with '").append(SharcConstants.PARAM_ACTION).append("=")
    .append(SharcConstants.ACTION_PITCHRANGES + "' on the querystring<br/>")
    .append("There are no required parameters.")
    .append("<br/><br/>Sample URLs:<br/>");
    String url = ServiceFunctions.makeUrlLink(request,
      SharcConstants.PARAM_ACTION + "=" + SharcConstants.ACTION_PITCHRANGES);
		sb.append(url);
    return(sb.toString());
  }
  public void validateService(HttpServletRequest request) throws SharcException {
  }
  public List<String> getRequiredParamList() {
    return requiredParamList;
  }
  public List<String> getAlternateParamList() {
    return alternateParamList;
  }
}
