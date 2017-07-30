<%@ page import="net.l8thStreet.sharc.exceptions.SharcException,
                 java.io.PrintWriter,
                 java.io.StringWriter,
                 org.apache.commons.lang.StringUtils,
                 java.util.List,
                 java.util.ArrayList"%>
<%@ page import="net.l8thStreet.sharc.SharcConstants" %>
<%--
	Created by IntelliJ IDEA.
	User: gsandell
	Date: Aug 8, 2006
	Time: 2:58:55 PM
	To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	SharcException e = (SharcException) request.getAttribute(SharcConstants.KEY_SHARC_EXCEPTION);
	StackTraceElement[] stackArray = e.getStackTrace();
	StringWriter sw = new StringWriter();
	e.printStackTrace(new PrintWriter(sw));
	String stackHtml = sw.toString().replaceAll("at ", "<br/>at ");
  Class c = null;
  Throwable t = e.getCause();
  String rootClassName = "";
  if (t != null) {
    c = t.getClass();
  }
  if (c != null) {
    rootClassName = c.getName();
  }

%>
<html>
  <head>
    <title>Error</title>
    <style type="text/css">
      TD.normal {
         background-color: #BFAF80;
         color: black;
       }
      TD.sharc {
        background-color: #503FA3;
        color: white;
         border: 1px solid #7971A3;
      }
      TD.catalina {
        background-color: 	#544B2F;
        color: black;
        border: 1px solid #A9975F;
      }
    </style>
  </head>
  <body>We got ourselves a little old error!
  <table border="1">
    <tr><th>Name</th><td><%= e.getClass().getName() %></td></tr>
    <tr><th>Message</th><td><%= e.getMessage() %></td></tr>
    <% if (!rootClassName.equals("")) { %>
      <tr><th>Root Cause</th><td><%= rootClassName %></td></tr> <%
    } %>
    <tr><th>Code Description</th><td><%= e.codeDesc %></td></tr>
    <tr><th>User Description</th><td><%= e.userDesc %></td></tr>
    <tr>
      <th>Stacktrace</th>
        <td>
          <table border="1" cellpadding="3" cellspacing="0">
            <tr><th>Package</th><th>Class</th><th>Method</th><th>File</th><th>Line</th></tr>
            <%
              for (int i = 0; i < stackArray.length; i++) {
                String css = "normal";
                String[] catalina = {"catalina", "tomcat", "coyote"};

                if (StringUtils.indexOfAny(stackArray[i].getClassName(), catalina) != -1)   {
                    css = "catalina";
                }
                  else if (stackArray[i].getClassName().toLowerCase().indexOf("sharc") != -1)   {
                    css = "sharc";
                  }
                out.println("<tr>");
                out.println("<td class='" + css + "'>" + StringUtils.substringBeforeLast(stackArray[i].getClassName(),".") + "</td>");
                out.println("<td class='" + css + "'>" + StringUtils.substringAfterLast(stackArray[i].getClassName(),".") + "</td>");
                out.println("<td class='" + css + "'>" + stackArray[i].getMethodName() + "</td>");
                out.println("<td class='" + css + "'>" + stackArray[i].getFileName() + "</td>");
                out.println("<td class='" + css + "'>" + stackArray[i].getLineNumber() + "</td>");
                out.println("</tr>");
              }
            %>
          </table>
        </td>
      </tr>  <%
    if(e.getCause() != null) {
      stackArray = e.getCause().getStackTrace();  %>
      <tr>
        <th>Cause<br/>Stacktrace</th>
        <td>
          <table border="1" cellpadding="3" cellspacing="0">
            <tr><th>Package</th><th>Class</th><th>Method</th><th>File</th><th>Line</th></tr>
            <%
             for (int i = 0; i < stackArray.length; i++) {
               String css = "normal";
               String[] catalina = {"catalina", "tomcat", "coyote"};

               if (StringUtils.indexOfAny(stackArray[i].getClassName(), catalina) != -1)   {
                   css = "catalina";
               }
                 else if (stackArray[i].getClassName().toLowerCase().indexOf("sharc") != -1)   {
                   css = "sharc";
                 }
               out.println("<tr>");
               out.println("<td class='" + css + "'>" + StringUtils.substringBeforeLast(stackArray[i].getClassName(),".") + "</td>");
               out.println("<td class='" + css + "'>" + StringUtils.substringAfterLast(stackArray[i].getClassName(),".") + "</td>");
               out.println("<td class='" + css + "'>" + stackArray[i].getMethodName() + "</td>");
               out.println("<td class='" + css + "'>" + stackArray[i].getFileName() + "</td>");
               out.println("<td class='" + css + "'>" + stackArray[i].getLineNumber() + "</td>");
               out.println("</tr>");
             } %>
            </td>
          </table>
        </td>
      </tr> <%
    }  %>
    </table>
  </td>
</tr>
</table>
</body>
</html>