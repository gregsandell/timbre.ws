<%@ page import="net.street18.sofa.singleton.PropertyConfigurator,
                 net.street18.sofa.SofaConstants"%>
<%@ taglib uri="/WEB-INF/tld/c-1_0.tld" prefix="c" %>
<%@ page language="java" %>
<%
  String cpath = request.getContextPath();
  String timeOutInterval = PropertyConfigurator.getInstance().get("timeout.interval");
  String warningPeriod = PropertyConfigurator.getInstance().get("timeout.polite.interval");
  String suddenDeathPeriod = PropertyConfigurator.getInstance().get("timeout.suddenDeath.interval");
  String timeoutInstance = "g_timeout";
  String timeOutPopupDivName = "timeOutPopup";
  String timeOutPopupTextDivName = "timeOutPopupText";
%>
<style type="text/css">
#<%= timeOutPopupDivName %> {
  position: absolute;
  left: 50px;
  top: 50px;
  height: 100px;
  width: 300px;
  background-color: black;
  color: white;
  display: none;
  text-align: center;
}
</style>
<div id="<%= timeOutPopupDivName %>">
  <div id="<%= timeOutPopupTextDivName %>"></div>
  <input type="button" value="OK" onclick="<%= timeoutInstance %>.hidePopup()" />
</div>
<script type="text/javascript" src="<%= cpath %>/js/Timeout.js"></script>
<script type="text/javascript">
	var suddenDeathMessage = "The site is about to log you off.  Please save your work immediately.";
	var politeMessage = "Your page has been inactive for several minutes.  For security purposes you will be auto-logged off in <%= warningPeriod %> minutes.  Please save your work or navigate to a new page to prevent being logged off.";
  var <%= timeoutInstance %> = new Timeout("<%= timeoutInstance %>");
  <%= timeoutInstance %>.clockDiv = document.getElementById("<%= SofaConstants.TIMER_CLOCK_DIV_NAME %>");
  <%= timeoutInstance %>.clockContainer = document.getElementById("<%= SofaConstants.TIMER_CLOCK_CONTAINER_DIV_NAME %>");
  <%= timeoutInstance %>.msgCtrl = document.getElementById("timeoutMsg");
  <%= timeoutInstance %>.logoutURL = "<%= cpath %>/tempLogoutSubmit.jsf";
  <%= timeoutInstance %>.interval = <%= timeOutInterval %>;
  <%= timeoutInstance %>.warningPeriod = <%= warningPeriod %>;
	<%= timeoutInstance %>.suddenDeathMessage = suddenDeathMessage;
	<%= timeoutInstance %>.suddenDeathPeriod = <%= suddenDeathPeriod %>;
	<%= timeoutInstance %>.politeMessage = politeMessage;
	<%= timeoutInstance %>.timeOutPopup = document.getElementById("<%= timeOutPopupDivName %>");
	<%= timeoutInstance %>.timeOutPopupText = document.getElementById("<%= timeOutPopupTextDivName %>");
  <%= timeoutInstance %>.init();
</script>
