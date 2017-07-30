<%@ page import="net.l8thStreet.sharc.SharcConstants" %>
<%@ page import="net.l8thStreet.sharc.service.ServiceFunctions" %>
<%@ page import="net.l8thStreet.sharc.exceptions.SharcException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	StringBuffer sb = new StringBuffer();
	sb.append("<ul>");
	for (int i = 0; i < SharcConstants.knownActions.size(); i++) {
		String action = (String) SharcConstants.knownActions.get(i);
		try {
			sb.append("<li>").append(ServiceFunctions.getDocLink(request, action));
		} catch (SharcException e) {
			  sb.append("Unexpected error");
		}
	}
	sb.append("</ul>");
%>
<html>
  <head>
    <title>Sharc Documentation</title>
    <style type="text/css">
    </style>
  </head>
  <body>
  <%= sb.toString() %>
  </body>
</html>