<html>
	<head>
		<title>SHARC</title>
		<link rel="StyleSheet" href="../includes/css/sharcBasic.css" TYPE="text/css" />
	</head>
	<body>
		<center><h1>SHARC</h1><h3><i>Sandell Harmonic Archive</i>
		<br/><a style="font-size: 11px" href="http://www.gregsandell.com">Greg Sandell</a>
		</h3></center>
		<br/>
		<h2>SHARC Browser</h2>
<p>
There is now a page for browsing spectra interactively at <a href="http://www.timbre.ws/browser">www.timbre.ws/browser</a>.
</p>
        <h2>SHARC Git Repo</h2>
<p>
SHARC can now be downloaded from a git repository at <a href="https://github.com/gregsandell/sharc-angular">https://github.com/gregsandell/sharc-angular</a>.  The file format for SHARC is now JSON, with an individual .json file per note.  The repository also contains the source code for the  <a href="http://www.timbre.ws/browser">SHARC Browser</a>, written in AngularJS.
</p>
       <h2>What is SHARC?</h2>
<p>

   SHARC is a database of musical timbre information by Gregory Sandell.
   It stands for "Sandell Harmonic Archive." People for whom this dataset
   may be useful are Acousticians, Psychoacousticians, researchers in
   Music Percepion and Cognition, researchers in Digital Signal
   Processing, Music Theorists, and Musicologists.
</p><p>   
   Over 1300 different notes have been analysed. Complete chromatic runs
   from the standard playing range of essentially all the non-percussive
   instruments of the modern orchestra have been included; for example,
   individual analyses of 32 different oboe notes (the chromatic scale
   from the pitches a#3 to f6) are available.
</p><p>   
   For each note, a short portion corresponding to the sustain or "steady
   state" portion of the tone was selected and analysed with a Fourier
   analysis. Each analysis consists of a list of amplitudes and phases
   for all the note's harmonics in the range 0-10,000 Hz. 
</p><p>   
   The source of the musical notes were the orchestral tones from the
   McGill University Master Samples (MUMS) Compact Discs. These are
   digital recordings of live musical performers.
</p>
<h2>The XML distribution is being retired</h2>
<p>The new JSON format is replacing the previous XML format.  The XML format may still be found below.</p>
<table border="0" cellpadding="4">
<thead >
				<tr><td colspan="4" align="center" style="font-size: 14px; font-weight: bold">One instrument per file</td></tr>
</thead>
<tbody>
<tr>
<td><a href="files/Bach_trumpet.xml">Bach_trumpet.xml</a></td>
<td><a href="files/Bb_clarinet.xml">Bb_clarinet.xml</a></td>
<td><a href="files/CB.xml">CB.xml</a></td>
<td><a href="files/CB_martele.xml">CB_martele.xml</a></td>
</tr><tr>
<td><a href="files/CB_muted.xml">CB_muted.xml</a></td>
<td><a href="files/CB_pizz.xml">CB_pizz.xml</a></td>
<td><a href="files/C_trumpet.xml">C_trumpet.xml</a></td>
<td><a href="files/C_trumpet_muted.xml">C_trumpet_muted.xml</a></td>
</tr><tr>
<td><a href="files/Eb_clarinet.xml">Eb_clarinet.xml</a></td>
<td><a href="files/English_horn.xml">English_horn.xml</a></td>
<td><a href="files/French_horn.xml">French_horn.xml</a></td>
<td><a href="files/French_horn_muted.xml">French_horn_muted.xml</a></td>
</tr><tr>
<td><a href="files/alto_trombone.xml">alto_trombone.xml</a></td>
<td><a href="files/altoflute_vibrato.xml">altoflute_vibrato.xml</a></td>
<td><a href="files/bass_clarinet.xml">bass_clarinet.xml</a></td>
<td><a href="files/bass_trombone.xml">bass_trombone.xml</a></td>
</tr><tr>
<td><a href="files/bassflute_vibrato.xml">bassflute_vibrato.xml</a></td>
<td><a href="files/bassoon.xml">bassoon.xml</a></td>
<td><a href="files/cello_martele.xml">cello_martele.xml</a></td>
<td><a href="files/cello_muted_vibrato.xml">cello_muted_vibrato.xml</a></td>
</tr><tr>
<td><a href="files/cello_pizzicato.xml">cello_pizzicato.xml</a></td>
<td><a href="files/cello_vibrato.xml">cello_vibrato.xml</a></td>
<td><a href="files/contrabass_clarinet.xml">contrabass_clarinet.xml</a></td>
<td><a href="files/contrabassoon.xml">contrabassoon.xml</a></td>
</tr><tr>
<td><a href="files/flute_vibrato.xml">flute_vibrato.xml</a></td>
<td><a href="files/oboe.xml">oboe.xml</a></td>
<td><a href="files/piccolo.xml">piccolo.xml</a></td>
<td><a href="files/trombone.xml">trombone.xml</a></td>
</tr><tr>
<td><a href="files/trombone_muted.xml">trombone_muted.xml</a></td>
<td><a href="files/tuba.xml">tuba.xml</a></td>
<td><a href="files/viola_martele.xml">viola_martele.xml</a></td>
<td><a href="files/viola_muted_vibrato.xml">viola_muted_vibrato.xml</a></td>
</tr><tr>
<td><a href="files/viola_pizzicato.xml">viola_pizzicato.xml</a></td>
<td><a href="files/viola_vibrato.xml">viola_vibrato.xml</a></td>
<td><a href="files/violin_martele.xml">violin_martele.xml</a></td>
<td><a href="files/violin_muted_vibrato.xml">violin_muted_vibrato.xml</a></td>
</tr><tr>
<td><a href="files/violin_pizzicato.xml">violin_pizzicato.xml</a></td>
<td><a href="files/violin_vibrato.xml">violin_vibrato.xml</a></td>
<td><a href="files/violinensemb.xml">violinensemb.xml</a></td>
<td>&nbsp;</td>
</tr>
<tr><td colspan="4">
								<center><span style="font-size: 14px; font-weight: bold">All instruments in one file</span></center><br/>
		<a href="files/sharc.zip">sharc.zip</a>
		<br/>
		<span class="smallFontComment">Because of the large size of the XML file (2.5 meg), it is given here only in compressed .zip format</span>  
		</td>
				
				</tr>
</tbody>
</table>
<h2>The Original Distribution</h2>
<p>
Read the <a href="files/README.txt">original documentation</a>.  
<span class="smallFontComment">
Note: The description of SHARC data is accurate in this file, but many of
the details about how the data appears in the file applies only to the 
original distribution. The XML format (above) renders the data in a different
manner.
</span>
</p>
<p>
Download the <a href="files/sharc.tar.gz">original distribution</a>, a 
g-zipped tar file (which WinZip can unpack).
</p>
</body>
</html>


