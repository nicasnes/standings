package tech.asnes.standings;

public class Match {
  private int matchid;
  private int team1ID;
  private int team2ID;

  public Match(int matchid, int team1ID, int team2ID) { 
    this.matchid = matchid;
    this.team1ID = team1ID;
    this.team2ID = team2ID;
  }

  public int getMatchID() {
    return this.matchid;
  }
  public int getTeamIDOne() {
    return this.team1ID;
  }
  public int getTeamIDTwo() {
    return this.team2ID;
  }

}
