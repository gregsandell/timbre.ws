package net.street18.util;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import net.street18.exception.l8thStreetException;

/**
 * Methods for mainuplations with org.xml.sax objects.
 * All methods write log errors.  The log4j library is required.

 */
public class SAXutils {
	private static Logger logger = Logger.getLogger(SAXutils.class.getName());
	/**
	 * Converts the contents of a file (presumably of XML) referenced by the filePath
	 * to a org.xml.sax.InputSource object.
	 * @param filePath  Fully-qualified path to a file on the local machine.
	 * @return  Will return null if the filePath is null or empty, the file is non-existent
	 * or can't be read, or if there is any other problem.  Check log output.
	 * in conversion.
	 */
	public static org.xml.sax.InputSource filePathToInputSource(String filePath) throws l8thStreetException {
		org.xml.sax.InputSource inputSource = null;
		if (filePath == null || filePath.length() == 0)  {
			throw new l8thStreetException("Cannot make a SAX InputSource from an empty or null filepath string");
		}
		try {
				FileInputStream fis = new FileInputStream(filePath);
				if (fis == null) {
          throw new l8thStreetException("Could not build a FileInputStream from file [" + filePath + "]");
				}
				inputSource = new org.xml.sax.InputSource(fis);
		}
			catch (FileNotFoundException e) {
        throw new l8thStreetException("File [" + filePath + "] not found. Message: " + e.getMessage());
			}
			finally {
			  return(inputSource);
			}
	}
	/**
	 * Converts an input string (presumably XML) to a org.xml.sax.InputSource object.
	 * @param xmlString
	 * @return  Object will be null if the xmlString is empty or null, or if there is any
	 * problem in conversion.  Check log output.
	 */
  public static org.xml.sax.InputSource xmlStringToInputSource(String xmlString) {
		if (xmlString == null || xmlString.length() == 0)  {
			logger.error("Cannot make a SAX InputSource from an empty or null xml String");
			return(null);
		}
		return(new org.xml.sax.InputSource(new StringReader(xmlString)));
	}
	/**
	 * Not really tested yet.
	 * @param inputSource
	 * @return
	 */
  public static boolean validator(org.xml.sax.InputSource inputSource) {
		boolean result = true;
		try {
				//org.xml.sax.XMLReader reader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
			org.xml.sax.XMLReader reader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
				reader.setFeature(SaxGlobals.SCHEMA_VALIDATION_FEATURE_ID, true);
				reader.setFeature(SaxGlobals.NAMESPACES_FEATURE_ID, false);
				reader.parse(inputSource);
		}
			catch (SAXException e) {
				logger.error("The XML is invalid");
				e.printStackTrace();
				result = false;
			}
			catch (IOException e) {
				logger.error("The XML to be validated could not be read");
				e.printStackTrace();
				result = false;
			}
		return(result);
	}

	/*  From http://javaalmanac.com/egs/javax.xml.parsers/BasicSax.html,
	"The Quintessential Program to Parse an XML File Using SAX" */
	public class BasicSax {
		/*  Synopsis of call:  */
		/*
						BasicSax mySax = new BasicSax();
					 DefaultHandler handler = new BasicSax.MyHandler();
					 mySax.parseXmlFile("infilename.xml", handler, false);
			*/

			 // DefaultHandler contain no-op implementations for all SAX events.
			 // This class should override methods to capture the events of interest.
			 class MyHandler extends DefaultHandler {
			 }

			 // Parses an XML file using a SAX parser.
			 // If validating is true, the contents is validated against the DTD
			 // specified in the file.
			 public void parseXmlFile(String filename, DefaultHandler handler, boolean validating) {
					 try {
							 // Create a builder factory
							 SAXParserFactory factory = SAXParserFactory.newInstance();
							 factory.setValidating(validating);

							 // Create the builder and parse the file
							 factory.newSAXParser().parse(new File(filename), handler);
					 } catch (SAXException e) {
							 // A parsing error occurred; the xml input is not valid
					 } catch (ParserConfigurationException e) {
					 } catch (IOException e) {
					 }
			 }
	 }



}
