package net.street18.util;

/**
 * Created by IntelliJ IDEA.
 * User: gsandell
 * Date: Jan 16, 2005
 * Time: 10:36:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class SaxGlobals {
	/** Namespaces feature id (http://xml.org/sax/features/namespaces). */
	 public static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";

	 /** Namespace prefixes feature id (http://xml.org/sax/features/namespace-prefixes). */
	 public static final String NAMESPACE_PREFIXES_FEATURE_ID = "http://xml.org/sax/features/namespace-prefixes";

	 /** Validation feature id (http://xml.org/sax/features/validation). */
	 public static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";

	 /** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
	 public static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";

	 /** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
	 public static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";

	 /** Dynamic validation feature id (http://apache.org/xml/features/validation/dynamic). */
	 public static final String DYNAMIC_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/dynamic";

	 /** Load external DTD feature id (http://apache.org/xml/features/nonvalidating/load-external-dtd). */
	 public static final String LOAD_EXTERNAL_DTD_FEATURE_ID = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	public static final String APACHE_XERCES_SAX_PARSER = "org.apache.xerces.parsers.SAXParser";
	public static final String APACHE_XERCES_DOM_PARSER = "org.apache.xerces.parsers.DOMParser";
	public static final String JAXP_XERCES_SAX_PARSER = "javax.xml.parsers.SAXParser";
}
