<%@ page import="net.l8thStreet.sharc.SharcConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String docHtml = (String) request.getAttribute(SharcConstants.KEY_DOC_STRING);

%>
<html>
  <head>
    <title>Sharc Documentation</title>
    <style type="text/css">
    </style>
  </head>
  <body>
  <%= docHtml %>
  </body>
</html>