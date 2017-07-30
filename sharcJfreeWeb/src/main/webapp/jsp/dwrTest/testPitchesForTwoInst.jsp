<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String cpath = request.getContextPath();
%>
<html>
  <head>
		<title>Simple jsp page</title>
		<script type="text/javascript" src="<%= cpath %>/dwr/interface/JsPitchesForTwoInst.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/engine.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/util.js"></script>
		<script type="text/javascript">
			window.onload = function() {
				// setupSelect();
				// setupMap();
			};
			function setupSelect() {
				//alert("in setupSelect()");
        JsPitchesForTwoInst.getPitches(document.myform.inst1.value, document.myform.inst2.value, populate);
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
    <input name="inst1" id="inst1" type="text" value="violin_vibrato" />
		<input name="inst2" id="inst2" type="text" value="flute_vibrato" />
    <select id="pitches"></select>
    <input type="Submit">
  </form>

	</body>
</html>