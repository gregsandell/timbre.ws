<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String cpath = request.getContextPath();
%>
<html>
  <head>
		<title>Simple jsp page</title>
		<script type="text/javascript" src="<%= cpath %>/dwr/interface/JsAllInstruments.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/engine.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/util.js"></script>
		<script type="text/javascript">
			window.onload = function() {
				setupSelect();
				// setupMap();
			};
			function setupSelect() {
				JsAllInstruments.getInstruments(populate);
			}
			function populate(list) {
				DWRUtil.removeAllOptions("allinstruments");
				DWRUtil.addOptions("allinstruments", list);
			}
		</script>
	</head>
  <body>
	<form action="#" 	>
		<select id="allinstruments"></select>
	</form>

	</body>
</html>