package net.street18.util;

import org.apache.log4j.Logger;
import org.jdom.Document;

import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import java.util.Properties;
import java.util.Enumeration;
import java.io.StringWriter;
import java.io.File;

import net.street18.exception.l8thStreetException;

/**
 * Transforms some XML by XSL into a string (presumably HTML).  Additionally allows you to
 * define some key-value pairs that will be exported into the XML as global parameters.
 * Setting the XML and XSL is required, the global parameters are optional.  An example usage
 * would be:
 *
 * 	XSLtransformer trans = new XSLtransformer();
 * 	trans.setXmlString(myXmlString);
 *  trans.setXslFilePath("c:\\java\\projects\\myProj\\xsl\\myXsl.xsl");
 * 	trans.addGlobalVariable("ContextPath", "/whydah");
 * 	String html = trans.run();
 *
 * That example is for the case where the user had a dynamically generated XML string, and
 * an XSL file on the local machine.  The XML and XSL can be entered by any number of other
 * methods (as a File Object or a javax.xml.transform.Source object, using the appropriate
 * methods.
 *
 * Most of the methods write log errors.  The log4j library is required.

 */
public class XSLtransformer {
  private static Logger logger = Logger.getLogger(XSLtransformer.class.getName());
  private javax.xml.transform.Source xmlSource;
  private javax.xml.transform.Source xsltSource;
  private Properties globalVariables;

  public XSLtransformer() {
    xmlSource = null;
    xsltSource = null;
    globalVariables = null;
  }

  /**
   * Performs the transformation.  Assumptions:  the user has already called methods for setting
   * the XML and XSL.
   * @return  The transformed XML (presumably some HTML).  An empty String will result if any
   * problems occur (see log output).
   */
  public String execute() {
    String result = "";
    if (getXmlSource() == null) {
      logger.error("Input XML was never set");
      return (result);
    }
    if (getXsltSource() == null) {
      logger.error("Input XSL was never set");
      return (result);
    }
    result = XSLtransform(getXmlSource(), getXsltSource(), getGlobalVariables());
    if (result == null) {
      logger.error("Transformation produced a null string as result");
      return ("");
    }
    if (result.length() == 0) {
      logger.error("Transformation produced an empty string as result");
      return (result);
    }
    return (result);
  }

  public String run() {     // Deprecated!
    return (execute());
  }

  /**
   * Quick version of the run method that allows setting of the XML, XSL and global parameters
   * in a single line.
   * @param xmlSource
   * @param xsltSource
   * @param globalVariables
   * @return The transformed XML (presumably some HTML).
   */
  public String run(javax.xml.transform.Source xmlSource,
                    javax.xml.transform.Source xsltSource,
                    Properties globalVariables) {
    setXmlSource(xmlSource);
    setXsltSource(xsltSource);
    setGlobalVariables(globalVariables);
    return (run());
  }

  /**
   * Quick version of the run method that allows setting of the XML and XSL.
   * @param xmlSource
   * @param xsltSource
   * @return The transformed XML (presumably some HTML).
   */
  public String run(javax.xml.transform.Source xmlSource,
                    javax.xml.transform.Source xsltSource) {
    return (run(xmlSource, xsltSource, null));
  }

  /**
   * Sets the required XML as a javax.xml.transform.Source object.
   * @param xmlSource
   */
  public void setXmlSource(javax.xml.transform.Source xmlSource) {
		if (xmlSource == null) {
			logger.error("Null xmlSource");
		}
    this.xmlSource = xmlSource;
  }

  /**
   * Sets the required XSL as a javax.xml.transform.Source object.
   * @param xsltSource
   */
  public void setXsltSource(javax.xml.transform.Source xsltSource) {
		if (xsltSource == null) {
			logger.error("Null xsltSource");
		}
    this.xsltSource = xsltSource;
  }

  /**
   * Set the Properties object.  For use when the user already has the key/value pairs to be
   * exported into the XML as global parameters entered in a Properties object.  Alternatively,
   * they can be set one at a time with the AddGlobalVariable() method.
   * @param globalVariables
   */
  public void setGlobalVariables(Properties globalVariables) {
    this.globalVariables = globalVariables;
  }

  private void initGlobalVariables() {
    this.globalVariables = new Properties();
  }

  /**
   * Add a key/value pair to be exported into the XML as a global parameter.
   * @param key
   * @param value
   */
  public void addGlobalVariable(String key, String value) {
    if (getGlobalVariables() == null) {
      initGlobalVariables();
    }
    getGlobalVariables().setProperty(key, value);
  }

  /**
   * Sets the XML as a fully-qualified path to a file on the local machine.
   * @param filePath
   * @return  True if succeeds.  Check log output if failure occurs.
   */
  public boolean setXmlFilePath(String filePath) throws l8thStreetException {
    javax.xml.transform.Source xmlSrc;
    if (filePath == null || filePath.length() == 0) {
      logger.error("Cannot make transformation if input XML file path is null or empty String");
      return (false);
    }
    xmlSrc = TrAXutils.filePathToStreamSource(filePath);
    if (xmlSrc == null) {
      logger.error("Could not convert Xml file path [" + filePath + "] to stream source");
      return (false);
    } else {
      setXmlSource(xmlSrc);
      return (true);
    }
  }

  /**
   * Sets the XSL as a fully-qualified path to a file on the local machine.
   * @param filePath
   * @return True if succeeds.  Check log output if failure occurs.
   */
  public boolean setXsltFilePath(String filePath)  throws l8thStreetException  {
    javax.xml.transform.Source xsltSrc;
    if (filePath == null || filePath.length() == 0) {
      logger.error("Cannot make transformation if input XML file path is null or empty String");
      return (false);
    }
    xsltSrc = TrAXutils.filePathToStreamSource(filePath);
    if (xsltSrc == null) {
      logger.error("Could not convert XSL file path [" + filePath + "] to stream source");
      return (false);
    } else {
      setXsltSource(xsltSrc);
      return (true);
    }
  }

  /**
   * Sets the XML as a string of XML.
   * @param xmlString
   * @return True if succeeds.  Check log output if failure occurs.
   */
  public boolean setXmlString(String xmlString) {
    javax.xml.transform.Source xmlSrc;
    if (xmlString == null || xmlString.length() == 0) {
      logger.error("Null or empty XML string is not allowed");
      return (false);
    }
    xmlSrc = TrAXutils.stringToStreamSource(xmlString);
    if (xmlSrc == null) {
      logger.error("Could not convert Xml string to stream source");
      return (false);
    } else {
      setXmlSource(xmlSrc);
      return (true);
    }
  }

  /**
   * Sets the XSL as a string of XSL.   (This should be tested!)
   * @param xsltString
   * @return  True if succeeds.  Check log output if failure occurs.
   */
  public boolean setXsltString(String xsltString) {
    javax.xml.transform.Source xsltSrc;
    if (xsltString == null || xsltString.length() == 0) {
      logger.error("Null or empty XSL string is not allowed");
      return (false);
    }
    xsltSrc = TrAXutils.stringToStreamSource(xsltString);
    if (xsltSrc == null) {
      logger.error("Could not convert Xsl string to stream source");
      return (false);
    } else {
      setXsltSource(xsltSrc);
      return (true);
    }
  }

  /**
   * Sets the XML as a file object referencing a file on the local machine.
   * @param xmlFileObj
   * @return  True if succeeds.  Check log output if failure occurs.
   */
  public boolean setXmlFileObj(File xmlFileObj)  throws l8thStreetException {
    javax.xml.transform.Source xmlSrc;
    if (xmlFileObj == null) {
      logger.error("Null or empty XML File object is not allowed");
      return (false);
    }
    if (!FileSystemUtils.isReadable(xmlFileObj)) {
      logger.error("XML file object could not be read");
      return (false);
    }
    xmlSrc = TrAXutils.fileObjToStreamSource(xmlFileObj);
    if (xmlSrc == null) {
      logger.error("Could not convert Xml file Object to stream source");
      return (false);
    } else {
      setXmlSource(xmlSrc);
      return (true);
    }
  }

  /**
   * Sets the XSL as a file object referencing a file on the local machine.
   * @param xsltFileObj
   * @return  True if succeeds.  Check log output if failure occurs.
   */
  public boolean setXsltFileObj(File xsltFileObj)  throws l8thStreetException {
    javax.xml.transform.Source xsltSrc;
    if (xsltFileObj == null) {
      logger.error("Null or empty Xsl File object is not allowed");
      return (false);
    }
    if (!FileSystemUtils.isReadable(xsltFileObj)) {
      logger.error("Xsl file object could not be read");
      return (false);
    }
    xsltSrc = TrAXutils.fileObjToStreamSource(xsltFileObj);
    if (xsltSrc == null) {
      logger.error("Could not convert Xslt file Object to stream source");
      return (false);
    } else {
      setXsltSource(xsltSrc);
      return (true);
    }
  }

  /**
   * Transform an XML file (provided as a JAXP Stream Source) by an XSLT file (provided as a JAXP Stream Source).
   * A properties object, consisting of key/value pairs can be used to import XSL variables into
   * the XSLT sheet.  If this object is null, then no globals are set.
   * @param xmlSource
   * @param xsltSource
   * @param globalVariables
   * @return  Transformed XML/XSL, presumably HTML.  Will be an empty String if the input XML or
   * XSL objects were null, or if there was any other problem in transformation.  See log output.
   */
  private String XSLtransform(javax.xml.transform.Source xmlSource,
                              javax.xml.transform.Source xsltSource,
                              Properties globalVariables) {
    String logOutput;
    String xmlSysId, xslSysId;
    String result = "";
    javax.xml.transform.TransformerFactory oTransFact;
    javax.xml.transform.stream.StreamResult oStreamResult;
    javax.xml.transform.Transformer oTrans;

    xmlSysId = xmlSource.getSystemId();
    xslSysId = xsltSource.getSystemId();
    logOutput = "Going to transform " +
      ((xmlSysId == null) ?
      ("input XML string ") :
      ("xml [" + xmlSysId + "] ")) +
      "by xsl [" + xslSysId + "]";
    logger.info(logOutput);
    StringWriter oStringWriter = new StringWriter();
    if (oStringWriter == null) {
      logger.error("Failure to create StringWriter object");
      return (result);
    }
    oStreamResult = new javax.xml.transform.stream.StreamResult(oStringWriter);
    if (oStreamResult == null) {
      logger.error("Failure to create StreamResult object");
      return (result);
    }
    oTransFact = javax.xml.transform.TransformerFactory.newInstance();
    if (oTransFact == null) {
      logger.error("Failure to create TransformerFactory object");
      return (result);
    }
    oTrans = null;
    try {
      oTrans = oTransFact.newTransformer(xsltSource);
			if (oTrans == null) {
				logger.error("Failure to create Transformer object");
				return(result);
			}
			// Note:  WROX "Professional Java XML" p. 1030 covers this property
      oTrans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			//oTrans.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "script");
			// oTrans.setOutputProperty(OutputKeys.INDENT, "yes");
      if (globalVariables != null) {
        Enumeration e = globalVariables.keys();
        while (e.hasMoreElements()) {
          String key = (String) e.nextElement();
          oTrans.setParameter(key, globalVariables.getProperty(key));
        }
      }
      oTrans.transform(xmlSource, oStreamResult);
    } catch (TransformerException e) {
      logger.error("TransformerException thrown while trying to perform XSLT transformation");
      e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      return ("");
    } finally {
      result = oStringWriter.toString();
    }
    return (result);
  }

  private javax.xml.transform.Source getXmlSource() {
    return xmlSource;
  }

  private javax.xml.transform.Source getXsltSource() {
    return xsltSource;
  }

  private Properties getGlobalVariables() {
    return globalVariables;
  }

}

