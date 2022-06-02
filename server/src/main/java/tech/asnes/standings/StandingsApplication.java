package tech.asnes.standings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

@SpringBootApplication
@RestController
	public class StandingsApplication {

		private final String dburl = "jdbc:postgresql://localhost:5432/leagueplay";
		private final String user = "username";
		private final String password = "password";

		public static void main(String[] args) {
			SpringApplication.run(StandingsApplication.class, args);
			System.out.println(generateLogMessage());
			
			// StandingsApplication standings = new StandingsApplication();
		}

		@GetMapping("/test")
		public String test(@RequestParam(value = "name", defaultValue = "world") String name) {
			return String.format("Hello %s!", name);
		}

		@GetMapping("/count")
		public String count() {
			return String.format("Count is %d!", getTestCount());
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
