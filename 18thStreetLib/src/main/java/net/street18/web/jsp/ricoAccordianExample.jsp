<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/centerContent.tld" prefix="ct" %>
<%

%>
<html>
  <head><title>Project page for SS-DAR Webapp</title></head>
  <base href="<%= request.getContextPath() %>" />
  <script type="text/javascript" src="js/prototype1.5.0.js"></script>
  <script type="text/javascript" src="js/rico-1.1.2.js"></script>
  <style type="text/css">
    fieldset {
      font-family:Verdana, Arial, Helvetica, sans-serif;
      font-size: 12px;
      padding: 12px;
      border: medium double #666;
      width: 450px;
    }
    fieldset.outer {
      font-family:Verdana, Arial, Helvetica, sans-serif;
      font-size: 12px;
      padding: 24px;
      border: heavy single #666;
      width: 450px;
    }
    input.myButton {
      font-family:Verdana, Arial, Helvetica, sans-serif;
      font-size: 11px;
    }
    body {
          background-color: #cccccc;
      font-family:Verdana, Arial, Helvetica, sans-serif;

    }
.accordion {
   background-color : #ddd;
   width      : 430;
   margin     : 10px;
}

.accordionPanel {
}

.accordionPanelHeader {
   font-size           : 14px;
   padding             : 2px 3px 2px 3px;
   border-style        : solid none solid none;
   border-top-color    : #BDC7E7;
   border-bottom-color : #182052;
   border-width        : 1px 0px 1px 0px;
   border-top-width    : 1px;
   border-top-style    : solid;
   font-family         : Arial;
padding: 3px 10px 3px 10px;
}

.accordionPanelContent {
   background-color       : #ddd;
   font-size        : 14px;
   border           : 1px solid #1f669b;
padding: 0px 10px 0px 10px;
}

.accordionFiller {
    padding: 10px;
}
  </style>
  <body>
    <ct:centerContent width="60%">  
      <fieldset class="outer">
        <legend><big><b>TITLE OF FIELDSET HERE</b></big></legend>
				<div class="accordion" id="accordionExample">
       		<div id="panel1" class="accordionPanel">
						<div id="panel1Header" class="accordionPanelHeader">
					  	HEADER TEXT FOR PANEL ONE HERE
					  </div>
					  <div id="panel1Content"  class="accordionPanelContent">
							        PANEL 1 CONTENT HERE
					  </div>
					</div>
       		<div id="panel2" class="accordionPanel">
						<div id="panel1Header" class="accordionPanelHeader">
					  	HEADER TEXT FOR PANEL TWO HERE
					  </div>
					  <div id="panel1Content"  class="accordionPanelContent">
							        PANEL 2 CONTENT HERE
					  </div>
					</div>
				</div>
      </fieldset>
    </ct:centerContent>
    <script type="text/javascript">
    	new Rico.Accordion( $('accordionExample') );
    </script>	
  </body>
</html>
