<html>
	<head>
		<title>SHARC Web Services</title>
		<link rel="StyleSheet" href="../../includes/css/sharcBasic.css" TYPE="text/css" />
	</head>
<body>
<center><h1>SHARC Web Services</h1></center>
Unfortunately, the SHARC web services formerly located at this page (http://www.timbre.ws/sharc/service) have gone away.  I have a long-term plan to replace them with a REST-based service (as well as switch from an XML to a JSON representation).  If you were dependent on these services for your work, please drop me a line at greg.sandell&lt;at&gt; gmail.com.
<!--
<h3>Queries returning XML</h3>
<dl>
<dt><a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=xpathXml&inst=flute_vibrato&xpath=/tree/instrument[@id='flute_vibrato']/note[@pitch='c4']">
	Flute c4 (middle c)	
</a></dt>
<dd><b>Url:</b>  http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=xpathXml&amp;inst=flute_vibrato&amp;xpath=/tree/instrument[@id='flute_vibrato']/note[@pitch='c4']
<b>Syntax:</b><i>http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=xpathXml&amp;inst=<b>&lt;name of xml collection&gt;</b>&amp;xpath=<b>&lt;xpath statement&gt;</b></i>
</dd>
<br/>
<dt><a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=xpathXml&inst=sharc&xpath=/tree/instrument/note[@pitch='c4']">
	Show all c4 notes in collection
</a></dt>
<dd><b>Url:</b>  http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=xpathXml&amp;inst=sharc&amp;xpath=/tree/instrument/note[@pitch='c4']
</dd>
</dl>
<h3>Queries producing graphic plots</h3>
<span style="font-size: 10px">Realtime plots produced using jFreeChart</span>
<dl>
<dt><a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=noteplot&inst=flute_vibrato&pitch=c4">
		Harmonic spectrum of flute playing c4 (middle c)
	</a></dt>
<dd><b>Url:</b>  http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=noteplot&amp;inst=flute_vibrato&amp;pitch=c4
<br/><b>Syntax:</b><i>http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=noteplot&amp;inst=<b>&lt;name of xml collection&gt;</b>&amp;pitch=<b>&lt;pitch&gt;</b></i>
</dd>
<br/>
<dt>
	<a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=twonoteplot&inst1=flute_vibrato&inst2=violin_vibrato&pitch=c4">
		Comparison of spectra for flute and violin, same note (c4)
	</a>
	</dt>
	<dd>
	<b>Url: </b>http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=twonoteplot&amp;inst1=flute_vibrato&amp;inst2=violin_vibrato&amp;pitch=c4
</dd>
	<br/>
<dt>
	<a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=longtimetwoplot&inst1=violin_vibrato&bins=50&inst2=C_trumpet_muted">
		Combined longtime spectra for violin and muted trumpet
	</a>
</dt>
<dd>
	<b>Url: </b>http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=longtimetwoplot&amp;inst1=violin_vibrato&amp;bins=50&amp;inst2=C_trumpet_muted
	</dd>
	<br/>
<dt>
	<a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=longtimeplot&inst=flute_vibrato&bins=50">
		Longtime spectrum for flute (all notes)
	</a>
</dt>
<dd>
	<b>Url: </b>http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=longtimeplot&amp;inst=flute_vibrato&amp;bins=50
	</dd>
	<br/>
<dt>
	<a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=centroidplot&inst=cello_vibrato">
		Brightness plot for a cello (all notes)
	</a>
</dt>
<dd>
<b>Url: </b>http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=centroidplot&amp;inst=cello_vibrato
</dd>
<br/>
<dt>
	<a href="http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=centroidtwoplot&inst1=cello_vibrato&inst2=flute_vibrato">
		Combined brightness plot for cello and flute
	</a>
</dt>
<dd>
	<b>Url: </b>http://www.timbre.ws:8080/sharcJfreeWeb/servlet/SharcService?action=centroidtwoplot&amp;inst1=cello_vibrato&amp;inst2=flute_vibrato
	</dd>
</dl>
-->
</body>
</html>


