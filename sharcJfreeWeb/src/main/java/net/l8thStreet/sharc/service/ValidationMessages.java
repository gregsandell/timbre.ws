package net.l8thStreet.sharc.service;

import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.SharcConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ValidationMessages {
	public static String acceptablePitchesText() {
		StringBuffer sb = new StringBuffer();
		sb.append("Pitches must be specified by their pitch class (a, b, c, etc.) followed by their octave ")
		.append("number (with no intervening space), for example 'c4' and 'f5'. ")
		.append("Octave numbers are based on the standard of 'middle c' falling in octave '4' ")
		.append("(i.e. c4 = middle c). ")
		.append("'Black key notes' (i.e. sharps and flats) are specified only ")
		.append("as sharps.  (i.e. a-sharp instead of b-flat.)  Also, we do not use the hashmark (#) to specify sharps because the web has a special ")
		.append("meaning for this symbol (it denotes anchors).  So we use a lower case 's' to stand for ")
		.append("the sharp: as4 for a#4, fs6 for f#6.   There ")
		.append("is only one spelling for each chromatic note, so the only allowed pitches are: ")
		.append("a, as, b, c, cs, d, ds, e, f, fs, g and gs.  ('bs' cannot be used as an equivalent to 'c'). ")
		.append("The pitch must be followed by ")
		.append("an octave number in the range 0 to 9, with no space inbetween.  These pitches ")
		.append("are valid:  'c4', 'ds8', 'g6', 'f0'.  These pitches are not valid: ")
		.append("'cb3', 'es4', 'd#3', 'd10', 'f 9', 'as 9'.");
		return(sb.toString());
	}

	public static String acceptableInstrumentsText() {
		return("The allowed instruments are " + SharcXmlSingleton.getInstance().getInstrumentList().toString());
	}

	public static String availablePitchesText() {
		StringBuffer sb = new StringBuffer();
		List insts = SharcXmlSingleton.getInstance().getInstrumentList();
		sb.append("<ul>");
		for (int i = 0; i < insts.size(); i++) {
			String instId = (String)insts.get(i);
			if (instId.equals("sharc")) {
				continue;
			}
			try {
					sb.append("<li><b>").append(instId).append("</b>: ")
				 .append(SharcXmlSingleton.getInstance().instPitchList(instId)).append("</li>");
			}
				catch (SharcException e) {
					/* It won't happen :-) */
				}
		}
		sb.append("</ul>");
		return(sb.toString());
	}

	public static void longtimeValidation(HttpServletRequest request, String action) throws SharcException {
		String docUrl = "";
		try {
				docUrl = ServiceFunctions.getDocUrl(request, action);
		} catch (SharcException e1) {
			/* It won't happen :-)  */
		}

		try {
			int bins = Integer.parseInt(request.getParameter(SharcConstants.PARAM_LONGTIME_BINS));
		}
		catch (NumberFormatException e) {
			String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_BINS +
				"' must be an integer, and the supplied value of '" + request.getParameter(SharcConstants.PARAM_LONGTIME_BINS)
				+ "' cannot be interpreted as an integer.  Documentation for this service: " +
				docUrl;
			throw new SharcHttpQueryException(err, err);
		}
		double lowFreq = 0.0;
		double highFreq = SharcConstants.ceilingFreq;
		if (!ServiceFunctions.isEmptyParam(request, SharcConstants.PARAM_LONGTIME_LOWFREQ))  {
			try {
				lowFreq = Double.parseDouble(request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ));
			}
			catch(NumberFormatException e) {
				String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_LOWFREQ +
					"' must be an floating point (e.g. 30.0, 72.7), and the supplied value of '" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ)
					+ "' cannot be interpreted as a floating point. Documentation for this service: " +
					docUrl;
				throw new SharcHttpQueryException(err,err);
			}
			if (lowFreq < 0.0 || lowFreq >= SharcConstants.ceilingFreq) {
				String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_LOWFREQ +
					"' must be greater than or equal to 0.0 and less than " + SharcConstants.ceilingFreq +
					" (the value '" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ) + "' was supplied). " +
					"Documentation for this service: " + docUrl;
				throw new SharcHttpQueryException(err,err);
			}
		}
		if (!ServiceFunctions.isEmptyParam(request, SharcConstants.PARAM_LONGTIME_HIGHFREQ))  {
			try {
				highFreq = Double.parseDouble(request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ));
			}
			catch(NumberFormatException e) {
				String err = "The value for parameter '" + SharcConstants.PARAM_LONGTIME_HIGHFREQ +
					"' must be an floating point (e.g. 30.0, 72.7), and the supplied value of '" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ)
					+ "' cannot be interpreted as a floating point. " +
					"Documentation for this service: " + docUrl;
				throw new SharcHttpQueryException(err,err);
			}
			if (highFreq < 0.0 || highFreq >= SharcConstants.ceilingFreq) {
				String err =  "The value for parameter '" + SharcConstants.PARAM_LONGTIME_HIGHFREQ +
					"' must be greater than or equal to 0.0 and less than " + SharcConstants.ceilingFreq +
					" (the value '" + request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ) + "' was supplied). " +
					"Documentation for this service: " + docUrl;
				throw new SharcHttpQueryException(err,err);
			}
		}
		if (lowFreq >= highFreq) {
			String err =  "The value for parameter '" + SharcConstants.PARAM_LONGTIME_LOWFREQ +
				"' (" + request.getParameter(SharcConstants.PARAM_LONGTIME_LOWFREQ) + " was supplied) " +
				"must be lower than the value for parameter '" + SharcConstants.PARAM_LONGTIME_HIGHFREQ +
				"' (" + request.getParameter(SharcConstants.PARAM_LONGTIME_HIGHFREQ) + " was supplied). " +
				"Documentation for this service: " + docUrl;
			throw new SharcHttpQueryException(err,err);
		}
	}
}
