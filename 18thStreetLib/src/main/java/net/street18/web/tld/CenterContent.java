package net.street18.web.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CenterContent extends BodyTagSupport {
  JspWriter out;
  private String width = null;

  public void init() {
    out = pageContext.getOut();
  }

  public int doStartTag() throws JspException {
    init();
    try {
        out.write(makeStartHtml());
    }
      catch (IOException e)  {
        throw new JspTagException("JSP Kit JavaScriptTag Error:" + e.toString());
     }
    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws JspTagException {
    try {
        out.write(makeEndHtml());
    }
      catch (IOException e)  {
        throw new JspTagException("JSP Kit JavaScriptTag Error:" + e.toString());
     } 

    // Have the JSP Container continue processing the JSP page as normal.

    return EVAL_PAGE;

  }
  private String makeStartHtml() {
    StringBuffer s = new StringBuffer();
    s.append("<table height='100%' width='100%' border='0'>")
      .append("<tr valign='middle'>")
         .append("<td align='center'>")
          	.append("<table width='" + getWidth() + "' border='0'>")
            	.append("<tr>")
              	.append("<td valign='middle'>");
		return(s.toString());
	}
  private String makeEndHtml() {
    StringBuffer s = new StringBuffer();
					    s.append("</td>")
 		   			.append("</tr>")
 	      	.append("</table>")
 	     .append("</td>")
 			.append("</tr>")
 	 .append("</table>");
	 return(s.toString());
  }
  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }


}


