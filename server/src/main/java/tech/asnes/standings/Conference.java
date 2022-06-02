package tech.asnes.standings;

public class Conference {
  private int conferenceNumber;
  private String conferenceName;

  public Conference(int conferenceNumber, String conferenceName) {
    this.conferenceNumber = conferenceNumber;
    this.conferenceName = conferenceName;
  }

  public int getConferenceNumber() { 
    return this.conferenceNumber;
  }

  public String getConferenceName() { 
    return this.conferenceName;
  }
}
