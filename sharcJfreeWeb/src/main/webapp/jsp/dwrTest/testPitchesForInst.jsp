<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String cpath = request.getContextPath();
%>
<html>
  <head>
		<title>Simple jsp page</title>
		<script type="text/javascript" src="<%= cpath %>/dwr/interface/JsPitchesForInst.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/engine.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/util.js"></script>
		<script type="text/javascript">
			window.onload = function() {
				// setupSelect();
				// setupMap();
			};
			function setupSelect() {
				//alert("in setupSelect()");
        JsPitchesForInst.getPitches(document.myform.inst.value, populate);
				//alert("finished setupSelect()");
			}
			function populate(list) {
				//alert("in populate");
				DWRUtil.removeAllOptions("pitches");
				DWRUtil.addOptions("pitches", list);
			}
		</script>
	</head>
  <body>
	<form action="#" name="myform" onsubmit="setupSelect(); return false"	>
    <input name="inst" id="inst" type="text" value="violin_vibrato" />
    <select id="pitches"></select>
    <input type="Submit">
  </form>

	</body>
</html>