package net.street18.util;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.apache.xpath.XPathAPI;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import net.street18.exception.l8thStreetException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 14, 2006
 * Time: 9:40:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DOMxpathPruner {
  /**
   *  Very basic utility for applying an XPath epxression to an xml file and printing information
   /  about the execution of the XPath object and the nodes it finds.
   *  Takes 2 arguments:
   *     (1) an xml filename
   *     (2) an XPath expression to apply to the file
   *  Examples:
   *     java ApplyXPath foo.xml /
   *     java ApplyXPath foo.xml /doc/name[1]/@last
   * @see org.apache.xpath.XPathAPI
   */
    protected String filename = null;
    protected String xpath = null;

    /** Process input args and execute the XPath.  */
    public void doMain(String[] args)  {
      filename = args[0];
      xpath = args[1];

  if ((filename != null) && (filename.length() > 0)
      && (xpath != null) && (xpath.length() > 0))  {
      // Tell that we're loading classes and parsing, so the time it
      // takes to do this doesn't get confused with the time to do
      // the actual query and serialization.
      System.out.println("Loading classes, parsing "+filename+", and setting up serializer");
    StringBuffer sb = null;
    try {
      InputSource in = new InputSource(new FileInputStream(filename));
      sb = theMainWork(in, xpath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (l8thStreetException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    System.out.println(sb.toString());

  }
else
{
System.out.println("Bad input args: " + filename + ", " + xpath);
}
    }

  public StringBuffer theMainWork(InputSource in, String xpath) throws l8thStreetException {
    // Set up a DOM tree to query.
    StringBuffer sb = new StringBuffer();
    org.jdom.transform.JDOMResult jdr;
    DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
    dfactory.setNamespaceAware(true);
    try {
        Document doc = dfactory.newDocumentBuilder().parse(in);

        // Set up an identity transformer to use as transformer.
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");


        // Use the simple XPath API to select a nodeIterator.
        //System.out.println("Querying DOM using "+xpath);
        NodeIterator nl = XPathAPI.selectNodeIterator(doc, xpath);

        // Serialize the found nodes to System.out.
        //System.out.println("<output>");

        Node n;
        while ((n = nl.nextNode())!= null)  {
          if (isTextNode(n)) {
              // DOM may have more than one node corresponding to a
              // single XPath text node.  Coalesce all contiguous text nodes
              // at this level
              sb.append(n.getNodeValue());
              for (Node nn = n.getNextSibling(); isTextNode(nn); nn = nn.getNextSibling()) {
                sb.append(nn.getNodeValue());
              }
              // System.out.print(sb);
          }
            else {
              jdr = new org.jdom.transform.JDOMResult();
              // System.out.println("node name = " + n.getNodeName());
              transformer.transform(new DOMSource(n), jdr);
              org.jdom.Document jdoc = jdr.getDocument();
              String temp = JdomUtils.docToXmlString(jdoc);
              sb.append(temp.replaceAll("<\\?xml.*\\?>", ""));
              // transformer.transform(new DOMSource(n), new StreamResult(new OutputStreamWriter(System.out)));
            }
          //System.out.println();
        }
    }
      catch (SAXException e) {
        e.printStackTrace();
        throw new l8thStreetException("SAXException, message = " + e.getMessage());
      }
      catch (IOException e) {
        e.printStackTrace();
        throw new l8thStreetException("IOException, message = " + e.getMessage());
      }
      catch (ParserConfigurationException e) {
        e.printStackTrace();
        throw new l8thStreetException("ParserConfigurationException, message = " + e.getMessage());
      }
      catch (TransformerException e) {
        e.printStackTrace();
        throw new l8thStreetException("TransformerException, message = " + e.getMessage());
      }
      finally {
        return(sb);
      }
    //System.out.println("</output>");
  }

  /** Decide if the node is text, and so must be handled specially */
    static boolean isTextNode(Node n) {
      if (n == null)
        return false;
      short nodeType = n.getNodeType();
      return nodeType == Node.CDATA_SECTION_NODE || nodeType == Node.TEXT_NODE;
    }

    /** Main method to run from the command line.    */
    public static void main (String[] args)
      throws Exception
    {
      if (args.length != 2)
      {
        System.out.println("java ApplyXPath filename.xml xpath\n"
                           + "Reads filename.xml and applies the xpath; prints the nodelist found.");
        return;
      }

      DOMxpathPruner app = new DOMxpathPruner();
      app.doMain(args);
    }



}
