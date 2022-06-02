package tech.asnes.standings;

public class Division {
  private int divisionNumber;
  private String divisionName;

  public Division(int divisionNumber, String divisionName) {
    this.divisionNumber = divisionNumber;
    this.divisionName = divisionName;
  }

  public int getDivisionNumber() { 
    return this.divisionNumber;
  }

  public String getDivisionName() { 
    return this.divisionName;
  }
}
