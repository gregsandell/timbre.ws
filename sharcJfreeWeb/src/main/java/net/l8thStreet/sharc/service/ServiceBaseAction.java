package net.l8thStreet.sharc.service;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import net.l8thStreet.sharc.SharcConstants;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.exceptions.SharcDocRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

public abstract class ServiceBaseAction implements ServiceInterface {
  private static Logger LOGGER = Logger.getLogger(ServiceBaseAction.class);
  public void plot(HttpServletRequest request, HttpServletResponse response) throws SharcException {
    validate(request, response);
    doPlot(request, response); 
  }
  public void validate(HttpServletRequest request, HttpServletResponse response) throws SharcException {
		catchDocRequest(request, response);
		approveParameterList(request);
		validateGenericParameters(request);
		validateService(request);
	}
	public String baseErrorMessage(HttpServletRequest request) throws SharcException {
		return("The requested URL was: " + StringEscapeUtils.escapeHtml(ServiceFunctions.getRequestedUrl(request)) +
				". Documentation on this service: " + ServiceFunctions.getDocUrl(request, this.getAction()));
	}
	private void validateGenericParameters(HttpServletRequest request) throws SharcException {
			/* ANY PITCH SUPPLIED MUST BE A VALID PITCH */
		if (getRequiredParamList().contains(SharcConstants.PARAM_PITCH)) {
	      String pitch = request.getParameter(SharcConstants.PARAM_PITCH);
				if (!SharcXmlSingleton.getInstance().isPitch(pitch))  {
					String err = "The value [" + pitch + "] supplied for the parameter '" + SharcConstants.PARAM_PITCH +
							"' is not a valid pitch. " + baseErrorMessage(request);
    	    throw new SharcHttpQueryException(err, err);
    	  }
    }
    /* ANY INSTRUMENT SUPPLIED MUST BE A VALID INSTRUMENT */
		if (getRequiredParamList().contains(SharcConstants.PARAM_INSTRUMENT)) {
      String inst = request.getParameter(SharcConstants.PARAM_INSTRUMENT);
			if (!SharcXmlSingleton.getInstance().isInstrument(inst)) {
				String err = "Error in Url for service '" + this.getAction() + "': " +
						"The instrument [" + inst + "] does not exist. Available instruments are: " +
						ValidationMessages.acceptableInstrumentsText() + ". " + baseErrorMessage(request);
        throw new SharcHttpQueryException(err, err);
      }
    }
		if (getRequiredParamList().contains(SharcConstants.PARAM_LONGTIME_BINS)) {
			try {
				int bins = Integer.parseInt(request.getParameter(SharcConstants.PARAM_LONGTIME_BINS));
			}
			catch (NumberFormatException e) {
				String err = "Error in Url for service '" + this.getAction() + "': " +
					"The value for parameter '" + SharcConstants.PARAM_LONGTIME_BINS +
						"' must be an integer, and the supplied value of '" + request.getParameter(SharcConstants.PARAM_LONGTIME_BINS)
						+ "' cannot be interpreted as an integer. " +
						ValidationMessages.acceptableInstrumentsText() + ". " + baseErrorMessage(request);
				throw new SharcHttpQueryException(err, err);
			}
		}
		double lowFreq = 0.0;
		double highFreq = SharcConstants.ceilingFreq;
		if (getAlternateParamList().contains(SharcConstants.PARAM_LONGTIME_LOWFREQ))  {
			if (!ServiceFunctions.isEmptyParam(request, SharcConstants.PARAM_LONGTIME_LOWFREQ))  {
				try {
						lowFreq = Double.parseDouble(request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ));
				}
					catch(NumberFormatException e) {
						String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_LOWFREQ +
								"' must be an floating point (e.g. 30.0, 72.7), and the supplied value of '" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ)
								+ "' cannot be interpreted as a floating point. " +
							ValidationMessages.acceptableInstrumentsText() + ". " + baseErrorMessage(request);
						throw new SharcHttpQueryException(err, err);
					}
				if (lowFreq < 0.0 || lowFreq >= SharcConstants.ceilingFreq) {
					String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_LOWFREQ +
							"' must be greater than or equal to 0.0 and less than " + SharcConstants.ceilingFreq +
							" (the value '" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ) + "' was supplied). " +
							ValidationMessages.acceptableInstrumentsText() + ". " + baseErrorMessage(request);
					throw new SharcHttpQueryException(err, err);
				}
			}
		}
		if (getAlternateParamList().contains(SharcConstants.PARAM_LONGTIME_HIGHFREQ))  {
			if (!ServiceFunctions.isEmptyParam(request, SharcConstants.PARAM_LONGTIME_HIGHFREQ))  {
				try {
						highFreq = Double.parseDouble(request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ));
				}
					catch(NumberFormatException e) {
						String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_HIGHFREQ +
							"' must be an floating point (e.g. 30.0, 72.7), and the supplied value of '" +
								request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ)
							+ "' cannot be interpreted as a floating point. " +
							ValidationMessages.acceptableInstrumentsText() + ". " + baseErrorMessage(request);
						throw new SharcHttpQueryException(err, err);
					}
				if (highFreq < 0.0 || highFreq >= SharcConstants.ceilingFreq) {
					String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_LOWFREQ +
						"' must be greater than or equal to 0.0 and less than " + SharcConstants.ceilingFreq +
						" (the value '" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ) + "' was supplied). " +
						ValidationMessages.acceptableInstrumentsText() + ". " + baseErrorMessage(request);
					throw new SharcHttpQueryException(err, err);
				}
			}
		}
		if (getAlternateParamList().contains(SharcConstants.PARAM_LONGTIME_LOWFREQ) &&
				getAlternateParamList().contains(SharcConstants.PARAM_LONGTIME_HIGHFREQ))  {
			if (!ServiceFunctions.isEmptyParam(request, SharcConstants.PARAM_LONGTIME_LOWFREQ)  &&
						(!ServiceFunctions.isEmptyParam(request, SharcConstants.PARAM_LONGTIME_HIGHFREQ)))  {
				lowFreq = Double.parseDouble(request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ));
				highFreq = Double.parseDouble(request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ));
				if (lowFreq >= highFreq) {
					String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_LOWFREQ +
							"' (" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ) + " was supplied) " +
							"must be lower than the value for parameter '" + SharcConstants.PARAM_LONGTIME_HIGHFREQ +
							"' (" + request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ) + " was supplied)";
					throw new SharcHttpQueryException(err, err);
				}
			}
		}
	}
	private void approveParameterList(HttpServletRequest request) throws SharcException {
        String action = request.getParameter(SharcConstants.PARAM_ACTION);
        for (String param:getRequiredParamList())		 {
			if (ServiceFunctions.isEmptyParam(request, param)) {
				String err = "Url for service '" + action + "' lacks one or more required parameters. " +
						"Requested URL: " + ServiceFunctions.getRequestedUrl(request) + ". Documentation on this service: " +
						ServiceFunctions.getDocUrl(request, SharcConstants.ACTION_NOTEPLOT );
				throw new SharcHttpQueryException(err, err);
			}
		}
	}
	public void catchDocRequest(HttpServletRequest request, HttpServletResponse response) throws SharcDocRequestException {
		if (request.getParameter(SharcConstants.PARAM_DOCUMENTATION) == null) {
			return;
		}
		String docString = this.requirements(request);
		request.setAttribute(SharcConstants.KEY_DOC_STRING, docString);
    throw new SharcDocRequestException("Documentation request for '" + getAction() + "'","");
	}
}
