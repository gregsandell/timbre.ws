package net.street18.util;

import net.street18.exception.l8thStreetException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;

/**
 * Various utility methods for working with the javax.xml library (also known as TrAX).
 * All methods write log errors.  The log4j library is required.
 */
public class TrAXutils {
	private static Logger logger = Logger.getLogger(TrAXutils.class.getName());

	/**
	 * Convert  a string of characters to a JAXP Stream Source
	 * @param s Presumably some XML or XSL
	 * @return  Object will be null if input String is null or empty, or there is any other
	 * problem in conversion.  Check log output.
	 */
	public static javax.xml.transform.Source stringToStreamSource(String s) {
		javax.xml.transform.Source result = null;
		StringReader stringReader;
		if (s == null) {
			logger.error("Cannot accept null arg");
			return(result);
		}
		if (s.length() == 0) {
			logger.error("Cannot accept empty string");
			return(result);
		}
		stringReader = new StringReader(s);
		if (stringReader == null) {
			logger.error("StringReader object was null");
			return(result);
		}
		result = new javax.xml.transform.stream.StreamSource(stringReader);
		return(result);
	}
	/**
	 * Convert a File object to a JAXP Stream Source.
	 * The File object is assumed to be valid (references a readable file).
	 * @param file_Obj
	 * @return Object will be null if input is null or does not reference a readable file.  See log output.
	 */
	public static javax.xml.transform.stream.StreamSource fileObjToStreamSource(File file_Obj) throws l8thStreetException {
		javax.xml.transform.stream.StreamSource result = null;
		if (file_Obj == null) {
			logger.error("Null file object");
			return(result);
		}
		if (!FileSystemUtils.isReadable(file_Obj)) {
			logger.error("The file referenced by the input file object is not readable");
      return(result);
		}
		result = new javax.xml.transform.stream.StreamSource(file_Obj);
		if (result == null) {
			logger.error("File Object could not be converted to a StreamSource object");
		}
		return(result);
	}
  /**
	 * Read a file from disk, return it as a JAXP Stream Source
	 * @param filePath  A fully-qualified path to a file on the local machine.
	 * @return  Object will be null if input is null, empty, or does not reference a readable file.
	 * See log output.
	 */
	public static javax.xml.transform.stream.StreamSource filePathToStreamSource(String filePath) throws l8thStreetException  {
		javax.xml.transform.stream.StreamSource result = null;
		if (filePath == null) {
			logger.error("Cannot accept null arg");
			return(result);
		}
		if (filePath.length() == 0) {
			logger.error("Cannot accept empty string");
			return(result);
		}
		File fileObj = new File(filePath);
		if (fileObj == null) {
			logger.error("Null file object");
			return(result);
		}
		if (FileSystemUtils.isReadable(fileObj))   {
				result = fileObjToStreamSource(fileObj);
		}
			else {
				logger.error("File [" + filePath + "] could not be read");
			}
		return(result);
	}
	/*
	public static boolean validator(org.xml.sax.InputSource inputSource) {
		boolean result = true;
		final String DEFAULT_PARSER_NAME = "org.apache.crimson.parser.XMLReaderImpl";
		final String DTD_VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
		final String SCHEMA_VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation/schema";
		final String PERFORM_NAMESPACE_PROCESSING_ID = "http://xml.org/sax/features/namespaces";
		final String JAXP_SCHEMA_LANGUAGE =
				"http://java.sun.com/xml/jaxp/properties/schemaLanguage";

		final String W3C_XML_SCHEMA =
				"http://www.w3.org/2001/XMLSchema";
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);

			SAXParser saxParser = factory.newSAXParser();
			saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

				factory.setNamespaceAware(false);
				factory.setValidating(true);
			saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
				//org.xml.sax.XMLReader reader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
			org.xml.sax.XMLReader reader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
				reader.setFeature(SCHEMA_VALIDATION_FEATURE_ID, true);
				reader.setFeature(PERFORM_NAMESPACE_PROCESSING_ID, false);
				reader.parse(inputSource);
		return(result);
	}
  */
	public static String foo() {
		return("foo");
	}
	public static boolean validator(String filePath) {
		Document doc;
		boolean result = true;
		try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				docBuilderFactory.setValidating(true);
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse (new File (filePath));
				 // normalize text representation
				doc.getDocumentElement ().normalize();
				logger.info("got to end of try");
		}
			catch (SAXParseException err) {
				logger.error("** Parsing error"
					 + ", line " + err.getLineNumber ()
					 + ", uri " + err.getSystemId ());
				logger.error("   " + err.getMessage ());
				result = false;
			}
			catch (SAXException e) {
				Exception        x = e.getException ();
				((x == null) ? e : x).printStackTrace ();
				result = false;
			}
			catch (Throwable t) {
				t.printStackTrace ();
				result = false;
			}
    return(result);
}



 }

