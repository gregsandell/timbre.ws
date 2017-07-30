package net.street18.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;            // xalan
import org.apache.xpath.domapi.XPathEvaluatorImpl;    // xalan
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.xpath.XPathNSResolver;
import org.w3c.dom.xpath.XPathResult;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Dec 8, 2003
 * Time: 5:07:41 PM
 * To change this template use Options | File Templates.
 */
public class XalanXpathPruner {
  private static Logger logger = Logger.getLogger(XalanXpathPruner.class.getName());
  private String prunedXml = "";
  public XalanXpathPruner(String xpath, String xmlString) {
  logger.info("INput string size = " + xmlString.length());
      prunedXml = prune(xpath, xmlString);
  }
  public XalanXpathPruner(String xpath, org.xml.sax.InputSource inputSource) {
      prunedXml = prune(xpath, inputSource);
  }
	public String prune (String xpath, org.xml.sax.InputSource inputSource )  	{
		javax.xml.parsers.DocumentBuilderFactory factory;
		javax.xml.parsers.DocumentBuilder builder;
		Node xmlTopNode;
		org.w3c.dom.NodeList resultNodeList;
		String prunedXmlString = "";
    logger.info("In prune");
		if (StringUtils.isEmpty(xpath))  {
			logger.error("Cannot prune xml with an empty or null xpath string");
			return(prunedXmlString);
		}
		if (inputSource == null)  {
			logger.error("Cannot prune xml with a null input source");
			return(prunedXmlString);
		}
		factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    logger.info("Factory = " + factory.toString());
		if (factory == null) {
			logger.error("Could not obtain a DocumentBuilderFactory instance");
			return(prunedXmlString);
		}
		factory.setNamespaceAware(true);
		factory.setExpandEntityReferences(true);
		try {
				builder = factory.newDocumentBuilder();
        logger.info("Builder = " + builder.toString());
				if (builder == null) {
					logger.error("Could not obtain a DocumentBuilder instance");
					return(prunedXmlString);
				}
				xmlTopNode = builder.parse(inputSource).getFirstChild();
        logger.info("xmlTopNode name is " + xmlTopNode.getLocalName());
				if (xmlTopNode == null) {
					logger.error("Could not parse the input");
					return(prunedXmlString);
				}
				resultNodeList = XPathAPI.selectNodeList(xmlTopNode, xpath);
        logger.info("result has " + resultNodeList.getLength() + " nodes");
				if (resultNodeList == null) {
					logger.error("Could not obtain a NodeList");
					return(prunedXmlString);
				}
				if (resultNodeList.getLength() == 0)   {
					logger.warn("Obtained a NodeList with zero nodes");
				}
				prunedXmlString = DOMutils.xmlStringFromNodeList(resultNodeList);
		}
			catch (javax.xml.parsers.ParserConfigurationException e) {
				logger.error("Exception thrown trying to parse xml String with an xpath");
				e.printStackTrace();
			}
			catch (org.xml.sax.SAXException e) {
				logger.error("Exception thrown trying to parse xml String with an xpath");
				e.printStackTrace();
			}
			catch (IOException e) {
				logger.error("Exception thrown trying to parse xml String with an xpath");
				e.printStackTrace();
			}
			catch (javax.xml.transform.TransformerException e) {
				logger.error("Exception thrown trying to parse xml String with an xpath");
				e.printStackTrace();
			}
			finally {
				factory  = null;
				builder  = null;
				xmlTopNode  = null;
				resultNodeList  = null;
      logger.info("xml string size = " + prunedXmlString.length());
				return(prunedXmlString);
			}
	}
  public String prune (String xpath, String inputFilePath)  	{
    javax.xml.parsers.DocumentBuilderFactory factory;
    javax.xml.parsers.DocumentBuilder builder;
    org.w3c.dom.NodeList resultNodeList;
    String prunedXmlString = "";
    logger.info("In prune");
    if (StringUtils.isEmpty(xpath))  {
        logger.error("Cannot prune xml with an empty or null xpath string");
        return(prunedXmlString);
    }
    if (StringUtils.isEmpty(inputFilePath))  {
        logger.error("Cannot prune xml with an empty input file path");
        return(prunedXmlString);
    }
    factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    logger.info("Factory = " + factory.toString());
    if (factory == null) {
        logger.error("Could not obtain a DocumentBuilderFactory instance");
        return(prunedXmlString);
    }
    factory.setNamespaceAware(true);
    factory.setExpandEntityReferences(true);
    try {
        builder = factory.newDocumentBuilder();
        logger.info("Builder = " + builder.toString());
        if (builder == null) {
            logger.error("Could not obtain a DocumentBuilder instance");
            return(prunedXmlString);
        }
        Document doc = builder.parse(inputFilePath);
        logger.info("first node name is " + doc.getFirstChild().getLocalName());
        if (doc == null) {
            logger.error("Could not parse the input");
            return(prunedXmlString);
        }
        XPathEvaluatorImpl xpe = new XPathEvaluatorImpl(doc);
        XPathNSResolver resolver = xpe.createNSResolver(doc);
        XPathResult result = (XPathResult)
          xpe.evaluate(xpath, doc, resolver, XPathResult.FIRST_ORDERED_NODE_TYPE, null);
      Node resultNode = result.getSingleNodeValue();
        prunedXmlString = resultNode.toString();
    }
      catch (javax.xml.parsers.ParserConfigurationException e) {
        logger.error("Exception thrown trying to parse xml String with an xpath");
        e.printStackTrace();
      }
      catch (org.xml.sax.SAXException e) {
        logger.error("Exception thrown trying to parse xml String with an xpath");
        e.printStackTrace();
      }
      catch (IOException e) {
        logger.error("Exception thrown trying to parse xml String with an xpath");
        e.printStackTrace();
      }
      finally {
        factory  = null;
        builder  = null;
        resultNodeList  = null;

        logger.info("xml string size = " + prunedXmlString.length());
        return(prunedXmlString);
      }
  }
	public String getPrunedXml() {
		return prunedXml;
	}
}
