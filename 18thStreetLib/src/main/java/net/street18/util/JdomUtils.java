package net.street18.util;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.transform.JDOMSource;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.InputSource;
import net.street18.exception.l8thStreetException;
/**
 * Set of static methods for basic JDom operations.
 * Copyright ©2005 ABN AMRO Services Company, Inc.  All rights reserved.
 * 
 * author Greg Sandell
 */
public final class JdomUtils
{
    private static Logger LOGGER = Logger.getLogger(JdomUtils.class);
    /**
     * Private constructor to prevent instantion of this utility class.
     *
     * author Greg Sandell
     */
    private JdomUtils()
    {
    }
    /**
     * Converts a String (presumably of XML) to a JDOMSource object.
     * @param s String of XML.
     * @return  Object will be null if input is null or empty, or if there is any problem
     * in conversion.  Check log output.
     */
    public static org.jdom.transform.JDOMSource stringToSource(String s)
    {
        SAXBuilder builder = new SAXBuilder();
        JDOMSource result = null;
        Document doc;
        if (s == null || s.length() == 0)
        {
            LOGGER.error("Cannot create JDOMSource object from a null or empty String");
            return (result);
        }
        try
        {
            doc = builder.build(new StringReader(s));
            result = new JDOMSource(doc);
        }
        catch (JDOMException j)
        {
            LOGGER.error("Exception converting an xml string to a jDom source");
            j.printStackTrace();
        }
        finally
        {
            if (result == null)
            {
                LOGGER.error(
                    "Conversion of xml string to jDom source resulted in a null object");
            }
            builder = null;
            doc = null;
            return (result);
        }
    }
    /**
     * Converts the contents of a file (presumably consisting of XML) to a JDOMSource object.
     * Does not check for validity of the file Object.
     * @param fileObj The file of XML
     * @return JDOMSource object.
     * author Greg Sandell
     */
    public static JDOMSource fileObjToSource(File fileObj)
    {
        return (new JDOMSource(fileObjToDoc(fileObj)));
    }
  /*
      This code doesn't work because the JDOMSource.setInputSource() is not implemented.
    public static JDOMSource saxInputSourceToSource(InputSource in) {
      JDOMSource j = new JDOMSource(new Document());
      j.setInputSource(in);
      return(j);
    }
    public static Element saxInputSourceToRootElement(InputSource in)   {
      return(saxInputSourceToSource(in).getDocument().getRootElement());
    }
    */
    /**
       * Converts the contents of a file (presumably consisting of XML) to a JDOMSource object.
       * @param filePath Fully-qualified path to a file.
       * @return  Object will be null if input String is null or empty, or there is any problem
       * in conversion.  Check log output.
       * author Greg Sandell
       */
    public static JDOMSource filePathToSource(String filePath) throws l8thStreetException {
        org.jdom.transform.JDOMSource result = null;
        if (filePath == null || filePath.length() == 0)  {
          throw new l8thStreetException("Cannot produce a JDOMSource from a null or empty input String");
        }
        File fileObj = new File(filePath);
        if (!net.street18.util.FileSystemUtils.isReadable(fileObj))  {
            throw new l8thStreetException("Cannot build a Jdom source from an invalid file");
        }
        result = fileObjToSource(fileObj);
        if (result == null)  {
            throw new l8thStreetException("Conversion of an Xml file to jDom source resulted in a null object");
        }
        return (result);
    }
    /**
     * Converts the contents of a fully-qualified path to a file (presumably of XML) on the local machine to a jDom Document.
     * @param filePath The path to the file, including the file name.
     * @return JDom Document object.  This will be null if there was any problem reading the file or producing
     * an object from it.  Check log output.  The File object is checked for validity first.
     * author Greg Sandell
     */
    public static org.jdom.Document filePathToDoc(String filePath) throws l8thStreetException {
        File myFile;
        myFile = new File(filePath);
        if (!FileSystemUtils.isReadable(myFile)) {
            throw new l8thStreetException("Cannot build a Jdom Doc from an invalid file");
        }
        org.jdom.Document result = fileObjToDoc(myFile);
        if (result == null)  {
            throw new l8thStreetException("Creating a jDom Document from a file resulted in a null object");
        }
        return (result);
    }
    /**
     * Converts the contents of a file (presumably of XML) referenced by a file object.
     * @param fileObj File containing XML data.
     * @return  A JDom Document object.  This will be null if there was any problem reading the 
     * file or producing
     * an object from it.  Check log output.  The File object is not checked for validity.
     * author Greg Sandell
     */
    public static org.jdom.Document fileObjToDoc(File fileObj)
    {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try
        {
            doc = builder.build(fileObj);
        }
        catch (JDOMException e)
        {
            LOGGER.error(
                "Exception thrown while creating a jDom Document from a file object");
            e.printStackTrace();
        }
        finally
        {
            if (doc == null)
            {
                LOGGER.error(
                    "Creating a jDom Document from a file object resulted in a null object");
            }
            builder = null;
            return (doc);
        }
    }
    /**
     * Converts a Jdom JDOMSource object to a JDom Element object.
     * @param elem A JDom Element object.
     * @param rootName The name of the root node.
     * @return JDOMSource object.
     * author Greg Sandell
     */
    public static org.jdom.transform.JDOMSource elementToSource(
        org.jdom.Element elem,
        String rootName)
    {
        org.jdom.transform.JDOMSource result = null;
        if (elem == null)
        {
            LOGGER.error("Cannot create a JDOMSource with a null jdom element");
            return (result);
        }
        if (rootName == null || rootName.length() == 0)
        {
            rootName = "tree";
        }
        String xml = elementToString(elem, rootName);
        if (xml == null || xml.length() == 0)
        {
            LOGGER.error("Cannot create a JDOMSource with an ");
            return (result);

        }
        return (stringToSource(xml));
    }
    /**
     * Converts a JDom Element object containing XML to an XML String.
     * @param elem JDom Element object.
     * @param rootName The name of the root node.
     * @return String of XML.
     * 
     * author Greg Sandell
     */
    public static String elementToString(
        final org.jdom.Element elem,
        final String rootName)
    {
        String result = "";
        /* org.jdom.Document     doc                 = new org.jdom.Document();
        org.jdom.Element         rootElem        = new org.jdom.Element(rootName);
        doc.setRootElement(elem);     */
        String startElem = "";
        String endElem = "";
        if (!StringUtils.isEmpty(rootName))  {
          startElem = "<" + rootName + ">";
          endElem = "</" + rootName + ">";
        }
        StringBuffer s = new StringBuffer();
        s.append(startElem).append(docToXmlString(elem.getDocument())).append(endElem);
        return (s.toString());
    }
    /**
     * Converts an input string of XML to a jDom Document object.
     * @param xmlString String of XML.
     * @return Jdom Document object.  This will be null if the string was empty or null or any other problem producing
     * an object from it.  Check log output.  The String is not checked for XML validity.
     * author Greg Sandell
     */
    public static Document xmlStringToDoc(final String xmlString)
    {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        if (xmlString == null || xmlString.length() == 0)
        {
            LOGGER.error("Cannot create a jDom object from null or empty input");
            return (doc);
        }
        try
        {
            doc = builder.build(new StringReader(xmlString));
        }
        catch (JDOMException e)
        {
            LOGGER.error("Exception thrown creating a jDom Document from an xml string");
            e.printStackTrace();
        }
        finally
        {
            if (doc == null)
            {
                LOGGER.error(
                    "Creating a jDom Document from an xml string in a null object");
            }
            builder = null;
            return (doc);
        }
    }
    /**
     * Takes a jDom Document object and converts it to an XML string.
     * @param doc JDom Document object.
     * @return  XML String.  Output will be an empty String if the input was null or there was any other
     * problem in the conversion.  See log output.
     */
    public static String docToXmlString(final org.jdom.Document doc)
    {
        org.jdom.output.XMLOutputter xmlOutputter;
        String result = "";
        if (doc == null)
        {
            LOGGER.error(
                "Cannot create an XML string from a null org.jdom.Document object");
            return (result);
        }
        // xmlOutputter = new org.jdom.output.XMLOutputter(Format.getPrettyFormat());
        xmlOutputter = new org.jdom.output.XMLOutputter();
        result = xmlOutputter.outputString(doc);
        if (result == null)
        {
            LOGGER.error("Creating a xml String from a jDom Document resulted in a null");
            result = "";
        }
        else if (result.length() == 0)
        {
            LOGGER.error(
                "Creating a xml String from a jDom Document resulted in an empty string");
        }
        xmlOutputter = null;
        return (result);
    }
    /**
     * Converts a JDom JDOMSource object of XML to an XML String.
     * @param source JDom JDOMSource object.
     * @return String of XML.
     * 
     * author Greg Sandell
     */
    public static String sourceToXmlString(org.jdom.transform.JDOMSource source)
    {
        org.jdom.Document doc = source.getDocument();
        return (docToXmlString(doc));
    }
    /**
     * Runs validation on a String of XML.
     * @param xml The XML String.
     * @return	True if valid, false otherwise.
     * author Greg Sandell
     */
    public static boolean validator(String xml)
    {
        boolean result = false;
        org.jdom.input.SAXBuilder builder;
        StringReader xmlStringReader;
        final String saxParser = "org.apache.xerces.parsers.SAXParser";
        final String DTD_VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
        final String SCHEMA_VALIDATION_FEATURE_ID =
            "http://apache.org/xml/features/validation/schema";
        final String PERFORM_NAMESPACE_PROCESSING_ID =
            "http://xml.org/sax/features/namespaces";
        builder = new org.jdom.input.SAXBuilder(saxParser, true);
        builder.setFeature(SCHEMA_VALIDATION_FEATURE_ID, true);
        builder.setFeature(PERFORM_NAMESPACE_PROCESSING_ID, true);
        xmlStringReader = new StringReader(xml);
        try
        {
            builder.build(xmlStringReader);
            LOGGER.info("validator(): Parsing suceeded");
            result = true;
        }
        catch (JDOMException e)
        {
            LOGGER.error("validator(): Parsing failed.  Reason: " + e.getMessage());
            result = false;
        }
        finally
        {
            builder = null;
            xmlStringReader = null;
            return (result);
        }
        /*
            catch (IOException e) {
                LOGGER.error("validator(): problem getting xml");
                return(false);
            }
            */
    }

}
