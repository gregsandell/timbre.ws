<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String cpath = request.getContextPath();
%>
<html>
  <head>
		<title>Simple jsp page</title>
		<script type="text/javascript" src="<%= cpath %>/dwr/interface/JsAllPitches.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/engine.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/util.js"></script>
		<script type="text/javascript">
			window.onload = function() {
				setupSelect();
			};
			function setupSelect() {
				JsAllPitches.getPitches(populate);
			}
			function populate(list) {
				DWRUtil.removeAllOptions("allpitches");
				DWRUtil.addOptions("allpitches", list);
			}
		</script>
	</head>
  <body>
	<form action="#" 	>
		<select id="allpitches"></select>
	</form>

	</body>
</html>