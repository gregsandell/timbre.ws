<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String cpath = request.getContextPath();
%>
<html>
  <head>
		<title>Simple jsp page</title>
		<script type="text/javascript" src="<%= cpath %>/dwr/interface/JsAllPitches.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/interface/JsInstrumentsForPitch.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/engine.js"></script>
		<script type="text/javascript" src="<%= cpath %>/dwr/util.js"></script>
    <script type="text/javascript" src="../../js/prototype.js"></script>
    <script type="text/javascript">
      Event.observe(window, 'load',
          function() {
            setupPitchesSelect();
            Event.observe('pitches', 'change', setupInstrumentsSelect);
						Event.observe('myform', 'submit', function() {
							// s = "http://127.0.0.1:8080/sharcJfreeWeb/servlet/SharcService?action=Noteplot&inst=";
							s = "http://127.0.0.1:8080/sharcJfreeWeb/servlet/SharcService?action=Noteplot&inst=";
							s += $('instruments').value + "&pitch=" + $('pitches').value;
							$('plotimgId').src = s;
							s = "http://127.0.0.1:8080/sharcJfreeWeb/servlet/SharcService?action=WavePlot&inst=";
							s += $('instruments').value + "&pitch=" + $('pitches').value;
							$('waveimgId').src = s;
						});
					});
      function setupPitchesSelect() {
        JsAllPitches.getPitches(populatePitches);
      }
      function populatePitches(list) {
        // alert(list);
        DWRUtil.removeAllOptions("pitches");
        DWRUtil.addOptions("pitches", list);
      }
      function setupInstrumentsSelect() {
        JsInstrumentsForPitch.getInstruments(document.myform.pitches.options[document.myform.pitches.selectedIndex].value,
            populateInstruments);
      }
      function populateInstruments(list) {
        DWRUtil.removeAllOptions("instruments");
        DWRUtil.addOptions("instruments", list);
      }
		</script>
  </head>
  <body>
  <form action="#" name="myform" id="myform" >
    <select id="instruments"></select>
    <select id="pitches"></select>
    <input type="Submit" value="Plot">
  </form>
	<img src="" id="plotimgId" alt=""/>
	<br/><img src="" id="waveimgId" alt=""/>

	</body>
</html>