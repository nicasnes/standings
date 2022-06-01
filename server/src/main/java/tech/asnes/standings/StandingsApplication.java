package tech.asnes.standings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
	public class StandingsApplication {

		public static void main(String[] args) {
			SpringApplication.run(StandingsApplication.class, args);
			System.out.println(generateLogMessage());
		}

		@GetMapping("/test")
		public String test(@RequestParam(value = "name", defaultValue = "world") String name) {
			return String.format("Hello %s!", name);
		}

		private static String generateLogMessage() { 
			return String.format("Server is running");
		}
}
