package net.l8thStreet.sharc.dwr;

import java.util.List;


public class InstrumentsAndPitch {
  private List<String> instruments;
  private List<String> pitches;
  private String selectedPitch;
  private String selectedInstrument;

  public void setInstrumentsFromPitch(String pitch, String instrumentPreSelect) {
    setInstruments(InstrumentsForPitch.getInstruments(pitch));
    setSelectedInstrument(instrumentPreSelect);
  }
  public void setPitchFromInstruments(String pitch, String pitchPreselect) {
    setInstruments(InstrumentsForPitch.getInstruments(pitch));
    setSelectedPitch(pitchPreselect);
  }

  public List<String> getInstruments() {
    return instruments;
  }

  public void setInstruments(List<String> instruments) {
    this.instruments = instruments;
  }

  public List<String> getPitches() {
    return pitches;
  }

  public void setPitches(List<String> pitches) {
    this.pitches = pitches;
  }

  public String getSelectedPitch() {
    return selectedPitch;
  }

  public void setSelectedPitch(String selectedPitch) {
    this.selectedPitch = selectedPitch;
  }

  public String getSelectedInstrument() {
    return selectedInstrument;
  }

  public void setSelectedInstrument(String selectedInstrument) {
    this.selectedInstrument = selectedInstrument;
  }
}
