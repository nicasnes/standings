package tech.asnes.standings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;

@SpringBootApplication
@RestController
	public class StandingsApplication {

		private final String dburl = "jdbc:postgresql://localhost:5432/leagueplay";
		private final String user = "username";
		private final String password = "password";

		public static void main(String[] args) {
			SpringApplication.run(StandingsApplication.class, args);
			System.out.println(generateLogMessage());
		}

		/* Basic test endpoint */
		@GetMapping("/test")
		public String test(@RequestParam(value = "name", defaultValue = "world") String name) {
			return String.format("Hello %s!", name);
		}

		/* Basic endpooint to test database count */
		@GetMapping("/count")
		public String count() {
			return String.format("%d", getTestCount());
		}

		/* GET endpoint to return conferences */
		@GetMapping("/conferences")
		public List<Conference> conferences() {
			return getConferences();
		}

		/* GET endpoint to return divisions */
		@GetMapping("/divisions")
		public List<Division> divisions() {
			return getDivisions();
		}
		
		/* GET endpoint to return teams */
		@GetMapping("/teams")
		public List<Team> teams() {
			return getTeams();
		}
		
		/* GET endpoint to return matches */
		@GetMapping("/matches")
		public List<Match> matches() {
			return getMatches();
		}

		/* Create a conference and add it to the database with a given ID number and name  */
		@PostMapping(value="/createConference")
		public ResponseEntity<Conference> createConference(@RequestBody Conference conference) {
			createConferenceInDatabase(conference.getConferenceNumber(), conference.getConferenceName());
			System.out.println("Created new conference and added to the database");
			return ResponseEntity
						 .created(URI.create(String.format("/createConference/%d%s", conference.getConferenceNumber(), conference.getConferenceName())))
						 .body(conference);
		}

		/* Create a division and add it to the database with a given ID number and name  */
		@PostMapping(value="/createDivision")
		public ResponseEntity<Division> createDivision(@RequestBody Division division) {
			createDivisionInDatabase(division.getDivisionNumber(), division.getDivisionName());
			System.out.println("Created new division and added to the database");
			return ResponseEntity
						 .created(URI.create(String.format("/createDivision/%d%s", division.getDivisionNumber(), division.getDivisionName())))
						 .body(division);
		}

		/* Create a match and add it to the database with a given ID number and name  */
		@PostMapping(value="/createMatch")
		public ResponseEntity<Match> createMatch(@RequestBody Match match) {
			createMatchInDatabase(match.getTeamIDOne(), match.getTeamIDTwo());
			System.out.println("Created new match and added to the database");
			return ResponseEntity
							.created(URI.create(String.format("/createMatch/%d%d", match.getTeamIDOne(), match.getTeamIDTwo())))
							.body(match);
		}

		private void createMatchInDatabase(int team1id, int team2id) { 
			try (Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO matches (tid1, tid2) VALUES (?, ?)")) {
				pstmt.setInt(1, team1id);
				pstmt.setInt(2, team2id);
				pstmt.executeQuery();
			} catch (SQLException e) { 
				System.out.println(e.getMessage());
			}
		}

		/* Create a team and add it to the database with a given ID number and name  */
		@PostMapping(value="/createTeam")
		public ResponseEntity<Team> createTeam(@RequestBody Team team) {
			createTeamInDatabase(team.getName(), team.getConferenceNum(), team.getDivisionNum());
			System.out.println("Created new team and added to the database");
			return new ResponseEntity<Team>(team, HttpStatus.CREATED);

		}
		
		private void createTeamInDatabase(String name, int conferenceNum, int divisionNum) { 
			try (Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO teams (teamname, conference, division) VALUES (?, ?, ?)")) {
				pstmt.setString(1, name);
				pstmt.setInt(2, conferenceNum);
				pstmt.setInt(3, divisionNum);
				pstmt.executeQuery();
			} catch (SQLException e) { 
				System.out.println(e.getMessage());
			}
		}

		private void createDivisionInDatabase(int num, String name) {
			insertNumNameInTable(num, name, "divisions");
		}

		private void createConferenceInDatabase(int num, String name) {
			insertNumNameInTable(num, name, "conferences");
		}

		private void insertNumNameInTable(int num, String name, String table) { 
			try (Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + table + " (num, name) VALUES (?, ?)")) {
				pstmt.setInt(1, num);
				pstmt.setString(2, name);
				pstmt.executeQuery();
			} catch (SQLException e) { 
				System.out.println(e.getMessage());
			}
		}

		private static String generateLogMessage() { 
			return String.format("Server is running");
		}

		public Connection connect() { 
					Connection conn = null;
					try { 
						conn = DriverManager.getConnection(dburl, user, password);
						System.out.println("Database connection established");
					} catch (SQLException e) { 
						System.out.println(e.getMessage());
					}
					return conn;
		}

		public List<Conference> getConferences() { 
			String SQL = "SELECT * FROM conferences;";
			
			List<Conference> result = new ArrayList<>();
			try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) { 
					while (rs.next()) { 
						int number = rs.getInt("num");
						String name = rs.getString("name");
						result.add(new Conference(number, name));
				}

					
				}
			catch (SQLException e) { 
				System.out.println(e.getMessage());
			}
			return result;
		}

		public List<Division> getDivisions() { 
			String SQL = "SELECT * FROM divisions;";
			
			List<Division> result = new ArrayList<>();
			try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) { 
					while (rs.next()) { 
						int number = rs.getInt("num");
						String name = rs.getString("name");
						result.add(new Division(number, name));
				}

					
				}
			catch (SQLException e) { 
				System.out.println(e.getMessage());
			}
			return result;
		}

		public List<Team> getTeams() { 
			String SQL = "SELECT * FROM teams;";
			
			List<Team> result = new ArrayList<>();
			try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) { 
					while (rs.next()) { 
						int id = rs.getInt("teamid");
						String name = rs.getString("teamname");
						int conference = rs.getInt("conference");
						int division = rs.getInt("division");
						Team team = new Team(id, name, conference, division);
						result.add(team);
					}
				}
			catch (SQLException e) { 
				System.out.println(e.getMessage());
			}
			return result;
		}

		public List<Match> getMatches() { 
			String SQL = "SELECT * FROM matches;";
			
			List<Match> result = new ArrayList<>();
			try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) { 
					while (rs.next()) { 
						int matchid = rs.getInt("matchid");
						int tid1 = rs.getInt("tid1");
						int tid2 = rs.getInt("tid2");
						Match match = new Match(matchid, tid1, tid2);
						result.add(match);
					}
				}
			catch (SQLException e) { 
				System.out.println(e.getMessage());
			}
			return result;
		}

		

		public int getTestCount() { 
			String SQL = "SELECT count(*) from test;";
			int count = 0;

			try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) { 
					rs.next();
					count = rs.getInt(1);
				}
			catch (SQLException e) { 
				System.out.println(e.getMessage());
			}

			return count;
		}
}
