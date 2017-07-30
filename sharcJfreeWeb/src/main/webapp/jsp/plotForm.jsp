<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String cpath = request.getContextPath();
%>
<html>
  <head>
      <title>Simple jsp page</title>
    <style type="text/css">
      #instrumentChoiceDiv #instrument2Div {
        display: none;
      }
      #longtimeChoicesDiv {
        display: none;
      }
    </style>
    <script type="text/javascript" src="<%= cpath %>/js/prototype.js" ></script>
    <script type="text/javascript">
      String.prototype.trim = function() { return this.replace(/^\s+|\s+$/, ''); };
      function plotTypeChange() {
        //alert("plotTypeChange(), this.id = " + this.id);
				alert(getPlotType());
				switch(this.id) {
          case 'longtimetwoplot':
            $("longtimeChoicesDiv").style.display = "none";
            $("pitchChoiceDiv").style.display = "none";
            $("instrumentChoiceDiv").style.display = "block";
            $("instrument1Div").style.display = "block";
            $("instrument2Div").style.display = "inline";
            break;
          case 'centroidtwoplot':
            $("longtimeChoicesDiv").style.display = "none";
            $("pitchChoiceDiv").style.display = "none";
            $("instrumentChoiceDiv").style.display = "block";
            $("instrument1Div").style.display = "block";
            $("instrument2Div").style.display = "inline";
            break;
          case 'twonoteplot':
            $("pitchChoiceDiv").style.display = "block";
            $("pitch1Div").style.display = "block";
            $("instrumentChoiceDiv").style.display = "block";
            $("instrument1Div").style.display = "block";
            $("instrument2Div").style.display = "inline";
            $("inst1").disabled = true;
            $("inst2").disabled = true;
            allPitchAvailability();
            break;
          case 'longtimeplot':
            $("longtimeChoicesDiv").style.display = "block";
            $("pitchChoiceDiv").style.display = "none";
            $("instrumentChoiceDiv").style.display = "block";
            $("instrument1Div").style.display = "block";
            $("instrument2Div").style.display = "none";
            break;
          case 'centroidplot':
            $("pitchChoiceDiv").style.display = "none";
            $("instrumentChoiceDiv").style.display = "block";
            $("instrument1Div").style.display = "block";
            $("instrument2Div").style.display = "none";
            break;
          case 'noteplot':
            $("pitchChoiceDiv").style.display = "block";
            $("pitch1Div").style.display = "block";
            $("instrumentChoiceDiv").style.display = "block";
            $("instrument1Div").style.display = "block";
            $("instrument2Div").style.display = "none";
            break;
        }
      }
      function instrument1Change() {
        // alert(document.plotForm.inst1.options[document.plotForm.inst1.selectedIndex].value);
				if (getPlotType() == 'twonoteplot') return;
				new Ajax.Request("<%= cpath %>/servlet/AjaxPitchListGet",
            {
              method: 'get',
              parameters: {'inst': document.plotForm.inst1.options[document.plotForm.inst1.selectedIndex].value},
              onComplete: pitchListGetResponse
             });

      }
      function pitch1Change() {
        var selection = document.plotForm.pitch1.options[document.plotForm.pitch1.selectedIndex].value;
        alert("selected pitch is " + selection);
        new Ajax.Request("<%= cpath %>/servlet/AjaxInstrumentListGet",
            {
              method: 'get',
              parameters: {'pitch': selection},
              onComplete: instListGetResponse
             });

      }
      function allPitchAvailability() {
				$('allPitchesCheckbox').checked = true;
				new Ajax.Request("<%= cpath %>/servlet/AjaxAllPitchesGet",
            {
              method: 'get',
              parameters: {},
              onComplete: pitchListGetResponse
             });

      }
      var pitchParamData = "";
      function init() {
        // alert("init()");
        new Ajax.Request("<%= cpath %>/servlet/AjaxInstrumentListGet",
            {
              method: 'get',
              parameters: "",
              onComplete: instListGetResponse
             });
      }
      function instListGetResponse(originalRequest) {
        var result = originalRequest.responseText.trim();
        eval(result);
        document.plotForm.inst1.options.length = 0;
        document.plotForm.inst1.options[0] = new Option("Select One", "-1", false, false);
        document.plotForm.inst2.options[0] = new Option("Select One", "-1", false, false);
        for (i = 0; i < foo.instlist.length; i++) {
          document.plotForm.inst1.options[i+1] = new Option(foo.instlist[i].long, foo.instlist[i].short, false, false);
          document.plotForm.inst2.options[i+1] = new Option(foo.instlist[i].long, foo.instlist[i].short, false, false);
        }
				$("inst1").disabled = false;
				$("inst2").disabled = false;
      }
		function pitchListGetResponse(originalRequest) {
        var result = originalRequest.responseText.trim();
        eval(result);
        // alert(foo);
        document.plotForm.pitch1.options.length = 0;
        document.plotForm.pitch1.options[0] = new Option("Select One", "-1", false, false);
        for (i = 0; i < foo.length; i++) {
          document.plotForm.pitch1.options[i+1] = new Option(foo[i], foo[i], false, false);
        }
        // alert("done");
      }
			function getPlotType() {
				if ($('noteplot').checked) return("noteplot");
				if ($('twonoteplot').checked) return("twonoteplot");
				if ($('longtimeplot').checked) return("longtimeplot");
				if ($('longtimetwoplot').checked) return("longtimetwoplot");
				if ($('centroidplot').checked) return("centroidplot");
				if ($('centroidtwoplot').checked) return("centroidtwoplot");
			}
	    </script>
  </head>
  <body>
    <div id="formDiv">
      <form action="#" name="plotForm">
        <div id="plotTypeDiv">
          <fieldset>
            <legend>Plot Type</legend>
            <table border="0">
              <tr>
                <td><label for="noteplot">Note Plot</label></td>
                <td><input type="radio" id="noteplot" checked="true" name="plotChoice"/></td>
              </tr>
              <tr>
                <td><label for="twonoteplot">Two Note Plot</label></td>
                <td><input type="radio" id="twonoteplot" name="plotChoice"/></td>
              </tr>
              <tr>
                <td><label for="longtimeplot">Longtime Plot</label></td>
                <td><input type="radio" id="longtimeplot" name="plotChoice"/></td>
              </tr>
              <tr>
                <td><label for="longtimetwoplot">Longtime Plot, two instruments</label></td>
                <td><input type="radio" id="longtimetwoplot" name="plotChoice"/></td>
              </tr>
              <tr>
                <td><label for="centroidplot">Centroid Plot</label></td>
                <td><input type="radio" id="centroidplot" name="plotChoice"/></td>
              </tr>
              <tr>
                <td><label for="centroidtwoplot">Centroid Plot, two instruments</label></td>
                <td><input type="radio" id="centroidtwoplot" name="plotChoice"/></td>
              </tr>
            </table>
          </fieldset>
        </div>
        <div id="pitchChoiceDiv">
          <fieldset>
            <legend>Pitch choice(s)</legend>
            <div id="pitch1Div">
              <table border="0">
                <tr>
                  <td><label for="pitch1">Pitch 1</label></td>
                  <td><select id="pitch1" name="pitch1" /></td>
                </tr>
              </table>
            </div>
            <div id="allPitchDiv">
              <table border="0">
                <tr>
                  <td><label for="allPitchesCheckbox">Show All Pitches</label></td>
                  <td><input type="checkbox" id="allPitchesCheckbox"/></td>
                </tr>
              </table>
            </div>
          </fieldset>
        </div>
        <div id="instrumentChoiceDiv">
          <fieldset>
            <legend>Instrument choice(s)</legend>
            <div id="instrument1Div">
              <table border="0">
                <tr>
                  <td><label for="inst1">Instrument 1</label></td>
                  <td><select id="inst1" name="inst1" /></td>
                </tr>
              </table>
            </div>
            <div id="instrument2Div">
              <table border="0">
                <tr>
                  <td><label for="inst2">Instrument 2</label></td>
                  <td><select id="inst2" name="inst2" /></td>
                </tr>
              </table>
            </div>
          </fieldset>
        </div>
        <div id="longtimeChoicesDiv">
          <fieldset>
            <legend>Frequency Ranges</legend>
            <table border="0">
              <tr>
                <td><label for="minfreq">Minimum Freq (Hz.)</label></td>
                <td><input type="text" id="minfreq" size="5" maxlength="7" /> </td>
              </tr>
              <tr>
                <td><label for="maxfreq">Maximum Freq (Hz.)</label></td>
                <td><input type="text" id="maxfreq" size="5" maxlength="7" /> </td>
              </tr>
              <tr>
                <td><label for="bins">Analysis Bins</label></td>
                <td><input type="text" id="bins" size="3" maxlength="4" /> </td>
              </tr>
            </table>
          </fieldset>
        </div>
      </form>
    </div>
    <div id="imgDiv">
      <img src="" id="plotImg" alt="" border="0" />
    </div>
    <script type="text/javascript">
      Event.observe(window, 'load',
          function() {
            init();
            Event.observe('noteplot', 'click', plotTypeChange);
            Event.observe('twonoteplot', 'click', plotTypeChange);
            Event.observe('longtimeplot', 'click', plotTypeChange);
            Event.observe('longtimetwoplot', 'click', plotTypeChange);
            Event.observe('centroidplot', 'click', plotTypeChange);
            Event.observe('centroidtwoplot', 'click', plotTypeChange);
            // Event.observe('instrument1Div', 'change', instrument1Change);
            Event.observe('inst1', 'change', instrument1Change);
            Event.observe('pitch1', 'change', pitch1Change);
          })
    </script>
  </body>
</html>