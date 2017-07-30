package net.street18.util;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.io.IOException;

import net.street18.exception.l8thStreetException;

/**
	* Utilities for making maniulations with W3c DOM objects.
 * All methods write log errors.  The log4j library is required.
 */
public class DOMutils {
	private static Logger logger = Logger.getLogger(DOMutils.class.getName());

	/**
	 * Takes a string of XML and returns a w3c DOM document object for it.
	 * @param xml
	 * @return
	 */
	public static final Document makeDoc(String xml) {
		Document doc = null;
		try {
				InputSource in = new InputSource(new StringReader(xml));
				DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
				logger.info("is validating = [" + dfactory.isValidating() + "]");
				doc = dfactory.newDocumentBuilder().parse(in);
		}
			catch (FactoryConfigurationError f) {
				logger.error("FactoryConfigurationError exception thrown while converting XML string to DOM");
				f.printStackTrace();
			}
			catch (ParserConfigurationException e) {
				logger.error("ParserConfigurationException thrown while converting XML string to DOM");
					e.printStackTrace();
			}
			catch (IOException e) {
				logger.error("IOException thrown while converting XML string to DOM");
						e.printStackTrace();
			}
			catch (SAXException e) {
				logger.error("SAXException thrown while converting XML string to DOM");
					e.printStackTrace();
			}
			finally {
				return(doc);
			}
	}
	/**
	 * Takes a filepath and converts it to a W3C DOM Document.
	 * @param filePath  A fully-qualified path to a file on the local machine.
	 * @return
	 */
	public static Document  filePathToDoc(String filePath) throws l8thStreetException {
		InputSource in = SAXutils.filePathToInputSource(filePath);
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			doc = dfactory.newDocumentBuilder().parse(in);
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
    return(doc);
	}
	/**
	 * I couldn't find where in the DOM API I would go to take a DOM NodeList and output
	 * XML as a string, so I brute-forced it and wrote it myself.
	 *
	 * WARNING:  Currently this does not handle namespaces, so if (for example) your DOM
	 * data contains "<ns:first_name>Fred</ns:first_name>", it will be output as
	 * <first_name>Fred</first_name>
	 * @param myNodeList
	 * @return
	 */
	public static String xmlStringFromNodeList(NodeList myNodeList) {
		if (myNodeList == null) {
			return("");
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < myNodeList.getLength(); i++) {
			org.w3c.dom.Node myNode = myNodeList.item(i);
			String name = myNode.getLocalName();
			String value = myNode.getNodeValue();
			if (name != null) {
				sb.append("<" + myNode.getNodeName() + " ");
				if (myNode.hasAttributes()) {
					org.w3c.dom.NamedNodeMap nMap = myNode.getAttributes();
					for (int j = 0; j < nMap.getLength(); j++)  {
						org.w3c.dom.Node aNode = nMap.item(j);
						sb.append(aNode.getLocalName() + "=\"" + aNode.getNodeValue() + "\" ");
					}
				}
				sb.append(">");
			}
			if (value != null) {
				sb.append(myNode.getNodeValue());
			}
			sb.append(xmlStringFromNodeList(myNode.getChildNodes()));
			if (name != null) {
				sb.append("</" + myNode.getNodeName() + ">");
			}
		}
		return(sb.toString());
	}
	public static String xmlStringFromDomDoc(Document doc)  {
		String result = "";
		if (doc == null) {
			logger.error("Cannot make xml string from a null DOM Document");
			return(result);
		}
		NodeList nodeList = doc.getChildNodes();
		if (nodeList == null || nodeList.getLength() == 0)  {
			logger.error("Could not retrieve any child nodes from DOM Document");
			return(result);
		}
		result = DOMutils.xmlStringFromNodeList(nodeList);
		if (result == null || result.length() == 0) {
			logger.error("Null or empty XML string returned from DOM document");
		}
		return(result);
	}
	/**
	 * A DOM-pased validating parser that takes a URI (of the xml to be parsed) as input.
	 *
	 * THIS CODE HAS NOT BEEN TESTED.
	 *
	 * @param uri
	 * @return
	 */
  public static boolean validator(String uri) {
		boolean result = true;
		boolean namespaces = true;
		ParserWrapper parser = null;
		final boolean DEFAULT_NAMESPACES = false;
		final boolean DEFAULT_VALIDATION = false;
		final boolean DEFAULT_SCHEMA_VALIDATION = false;
		final boolean DEFAULT_SCHEMA_FULL_CHECKING = false;
		final boolean DEFAULT_CANONICAL = false;


		try {
				parser = (ParserWrapper)Class.forName(SaxGlobals.APACHE_XERCES_SAX_PARSER).newInstance();
		}
			catch (Exception e) {
				logger.error("error: Unable to instantiate parser ("+SaxGlobals.APACHE_XERCES_SAX_PARSER+")");
				result = false;
			}
		if (parser != null) {
			try {
					parser.setFeature(SaxGlobals.NAMESPACE_PREFIXES_FEATURE_ID, namespaces);
			}
				catch (SAXException e) {
					logger.error("warning: Parser does not support feature ("+SaxGlobals.NAMESPACE_PREFIXES_FEATURE_ID+")");
				}
			 try {
					 parser.setFeature(SaxGlobals.VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
			 }
			 	catch (SAXException e) {
					 logger.error("warning: Parser does not support feature ("+SaxGlobals.VALIDATION_FEATURE_ID+")");
			 	}
			 try {
					 parser.setFeature(SaxGlobals.SCHEMA_VALIDATION_FEATURE_ID, DEFAULT_SCHEMA_VALIDATION);
			 }
			 catch (SAXException e) {
					 logger.error("warning: Parser does not support feature ("+SaxGlobals.SCHEMA_VALIDATION_FEATURE_ID+")");
			 }
			 try {
					 parser.setFeature(SaxGlobals.SCHEMA_FULL_CHECKING_FEATURE_ID, DEFAULT_SCHEMA_FULL_CHECKING);
			 }
			 catch (SAXException e) {
					 logger.error("warning: Parser does not support feature ("+SaxGlobals.SCHEMA_FULL_CHECKING_FEATURE_ID+")");
			 }
			try {
					Document document = parser.parse(uri);
			}
				catch (Exception e) {
					logger.error("error: Parse error occurred - "+e.getMessage());
					result = false;
					if (e instanceof SAXException) {
							Exception nested = ((SAXException)e).getException();
							if (nested != null) {
									e = nested;
							}
					}
					e.printStackTrace(System.err);
				}
		}
		return(result);
	}
}
