package net.l8thStreet.sharc.waveform;

import org.jdom.Element;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.io.*;

import net.l8thStreet.sharc.SharcUtils;
import net.l8thStreet.sharc.xmlObject.*;
import net.l8thStreet.sharc.exceptions.SharcException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: greg
 * Date: Sep 24, 2007
 * Time: 10:08:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class SynthFuncs {
  private static double twoPi;
  static {
    twoPi = 2.0 * Math.PI;
  }
  public static List<Double> makeWave(double fund, int numHarms, List<Harmonic> harmonics, int sampRate) throws SharcException {
    List<Double> f = new ArrayList<Double>();
    int numSamps = (int)((1.0/fund) * (double)sampRate);
    double w = twoPi/(double)numSamps;
    for (int t = 0; t < numSamps; t++) {
      double s = 0.0;
      for (int k = 0; k < numHarms; k++) {
        double amp = harmonics.get(k).getAmplitude();
        double phase = harmonics.get(k).getPhase()/Math.PI;
        s += amp * Math.sin(((double)(k+1) * w * (double)t) + phase);
      }
      f.add(t, s);
    }
    return(f);
  }
	public static List<Double> makeWave(String inst, String pitch, int sampRate) throws SharcException {
    InstrumentInf instrument = new Instrument(inst);
    NoteInf note = instrument.getNote(pitch);
		double fund = note.getFundHz();
		int numHarms = note.getNumHarms();
    List<Harmonic> harmonics = note.getSharcHarmonics();
		return(SynthFuncs.makeWave(fund, numHarms, harmonics, sampRate));
	}
	public static JFreeChart makeWavePlot(String inst, String pitch, String title, int sampRate) throws SharcException {
		XYSeries xySeries = new XYSeries("Samples");
		List<Double> samples = makeWave(inst, pitch, sampRate);
		int sampCount = 0;
		for (int i = 0; i < samples.size(); i++, sampCount++) {
			double scale = (double)i/(double)samples.size();
			xySeries.add(sampCount, samples.get(i) * scale);
		}
		for (int i = 0; i < samples.size(); i++, sampCount++) {
			xySeries.add(sampCount, samples.get(i));
		}
		for (int i = 0; i < samples.size(); i++, sampCount++) {
			xySeries.add(sampCount, samples.get(i));
		}
		for (int i = 0; i < samples.size(); i++, sampCount++) {
			double scale = 1.0 - ((double)i/(double)samples.size());
			xySeries.add(sampCount, samples.get(i) * scale);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(xySeries);
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Sample", "Pressure",
				dataset, PlotOrientation.VERTICAL, false, false, false);
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		return(chart);

	}
	public static List<Double> getNSamples(List<Double> samples, int n) {
		List<Double> result = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			result.add(samples.get(i % samples.size()));
		}
		return(result);
	}

	/**
	 * This goofy method will show the waveforms of all of an instrument's pitches in succession in one
	 * plot.  Because the number of samples in a period varies with the F0, a "sampsPerWave" arg lets
	 * you equalize the number of samples shown per pitch (so high pitches will end up having more
	 * periods than low pitches).
	 * @param inst
	 * @param title
	 * @param sampsPerWave
	 * @param sampRate
	 * @return
	 * @throws SharcException
	 */
	public static JFreeChart makeAllNoteWavePlot(String inst, String title, int sampsPerWave, int sampRate) throws SharcException {
		XYSeries xySeries = new XYSeries("Samples");
    InstrumentInf instrument = new Instrument(inst);
    List<Note> notes = instrument.getSharcNotes();
		int sampCount = 0;
		for (int i = 0; i < notes.size(); i++) {
      NoteInf note = notes.get(i);
			String pitch = note.getPitch();
			List<Double> samples = makeWave(inst, pitch, sampRate);
			List<Double> nSamples = getNSamples(samples, sampsPerWave);
			for (int j = 0; j < nSamples.size(); j++, sampCount++) {
				xySeries.add(sampCount, nSamples.get(j));
			}
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(xySeries);
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Sample", "Pressure",
				dataset, PlotOrientation.VERTICAL, false, false, false);
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		return(chart);

	}

	/**
	 * work in progress...
	 */
	public static void makeAudioFile() {
		AudioFormat audioFormat =
				new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 22050F, 16, 1, 2, 22050F, true);
		AudioFileFormat audioFileFormat =
				new AudioFileFormat(AudioFileFormat.Type.WAVE, audioFormat, AudioSystem.NOT_SPECIFIED);
		byte[] myBytes = new byte[1000];
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(myBytes);
		AudioInputStream audioInputStream =
				new AudioInputStream(byteArrayInputStream, audioFormat, 500);
	}

	/**
	 * Work in progress...
	 * @param sample
	 * @return
	 */
	public static byte[] sampleToBytes(final Double sample) {
		byte[] bytes = new byte[2];
		int samp = sample.intValue();
		bytes[1] = (byte)(samp >>> 8);
		bytes[0] = (byte)(samp >>> 16);
		return(bytes);
	}

}
