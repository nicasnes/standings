package tech.asnes.standings;

public class Team {
  private int id;
  private String name;
  private int conferenceNum;
  private int divisionNum;

  public Team(int id, String name, int conferenceNum, int divisionNum) {
    this.id = id;
    this.name = name;
    this.conferenceNum = conferenceNum;
    this.divisionNum = divisionNum;
  }

  public String getName() {
    return this.name;
  }

  public int getID() {
    return this.id;
  }

  public int getConferenceNum() {
    return this.conferenceNum;
  }

  public int getDivisionNum() {
    return this.divisionNum;
  }

}
