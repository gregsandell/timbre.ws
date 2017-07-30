<%@ page import="net.street18.sofa.SofaConstants"%>
<%@ page language="java" %>
<%
    String cpath = request.getContextPath();
%>
<style type="text/css">
#<%= SofaConstants.TIMER_CLOCK_CONTAINER_DIV_NAME %> {
  display: none;
}
</style>
<div id="<%= SofaConstants.TIMER_CLOCK_CONTAINER_DIV_NAME %>">
  <img src="<%= cpath %>/image/icon_alert.gif"/><span id="<%= SofaConstants.TIMER_CLOCK_DIV_NAME %>"></span>
</div>