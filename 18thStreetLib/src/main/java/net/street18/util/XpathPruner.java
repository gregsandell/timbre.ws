package net.street18.util;

import net.street18.exception.l8thStreetException;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * Needs to be fixed to recurse down to lowest node
 */
public class XpathPruner {
	private org.w3c.dom.Document domDoc;
  private InputSource iSource;
	private static Logger logger = Logger.getLogger(XpathPruner.class.getName());
  private static final String decl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private StringBuffer sBuffer;
	private org.jdom.Document jDoc = null;
  private boolean boolEnclose = false;
  private String rootElem = "";
	public XpathPruner(String xpath, String xmlString) throws l8thStreetException {
    iSource = SAXutils.xmlStringToInputSource(xmlString);
    DOMxpathPruner dPruner = new DOMxpathPruner();
    sBuffer = dPruner.theMainWork( iSource, xpath);
    /*
    domDoc = DOMutils.makeDoc(xmlString);
		sBuffer = prune(xpath, domDoc);
    */
	}

	public StringBuffer prune (String xpath, org.w3c.dom.Document doc) throws l8thStreetException 	{
		org.jdom.transform.JDOMResult jdr;
    StringBuffer s = new StringBuffer();
		try {
				javax.xml.transform.TransformerFactory tFactory =
					javax.xml.transform.TransformerFactory.newInstance();
				javax.xml.transform.Transformer transformer = tFactory.newTransformer();

				// Avoids printing <?XML .>
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "true");
				NodeIterator nodeIterator = org.apache.xpath.XPathAPI.selectNodeIterator(doc, xpath);

				org.w3c.dom.Node currentNode;
				javax.xml.transform.dom.DOMSource domSource = new javax.xml.transform.dom.DOMSource();
				while ((currentNode = nodeIterator.nextNode())!= null)	{
          jdr = new org.jdom.transform.JDOMResult();
					domSource.setNode(currentNode);
					transformer.transform(domSource, jdr);
          String temp = JdomUtils.docToXmlString(jdr.getDocument());
          s.append(temp.replaceAll("<\\?xml.*\\?>", ""));

				}
				if (s.length() == 0) {
					throw new l8thStreetException("Error parsing xml with xpath [" + xpath + "] (empty string result)");
				}
		}
			catch (FactoryConfigurationError f) {
				f.printStackTrace();
        throw new l8thStreetException("FactoryConfigurationError:  xpath arg of [" + xpath + "] couldn't be processed");
			}
			catch (TransformerFactoryConfigurationError t) {
				t.printStackTrace();
        throw new l8thStreetException("TransformerFactoryConfigurationError:  xpath arg of [" + xpath + "] couldn't be processed");
			}
			catch (TransformerException e) {
        e.printStackTrace();
				throw new l8thStreetException("TransformerException:  xpath arg of [" + xpath + "] couldn't be processed");
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
        throw new l8thStreetException("IllegalArgumentException:  xpath arg of [" + xpath + "] couldn't be processed");
			}
			catch (DOMException e) {
				e.printStackTrace();
        throw new l8thStreetException("DOMException:  xpath arg of [" + xpath + "] couldn't be processed");
			}
			finally {
				return(s);
			}
	}
  public boolean isEmptyResult() {
    return(sBuffer.length() == 0);
  }
	public String getXmlString() {
    StringBuffer sbLocal = new StringBuffer();
    sbLocal.append(sBuffer);
    if (rootElem.length() > 0) {
      sbLocal.insert(0, "<" + rootElem + ">");
      sbLocal.append("</" + rootElem + ">");
    }
    if (this.boolEnclose)  {
      sbLocal.insert(0, decl + System.getProperty("line.separator"));
    }
		return sbLocal.toString();
	}
  public void enclosingElement(String elem) {
    rootElem = elem;
  }
  public void addDeclaration() {
    boolEnclose = true;
  }
	public org.jdom.Document getjDoc() {
		if (jDoc == null) {
			logger.error("Attempt to retrieve pruned xml before processing has occurred");
		}
		return jDoc;
	}
  /*
	public org.jdom.transform.JDOMSource getJdomSource(String rootName) {
		return(JdomUtils.elementToSource(getsBuffer(), rootName));
	}
   */
}
