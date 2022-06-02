package tech.asnes.standings;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StandingsApplicationTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private StandingsApplication controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/test",
				String.class)).contains("Hello world!");
	}

	@Test
	public void greetingWithNameReturnsHelloName() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/test?name=Nic",
				String.class)).contains("Hello Nic!");
	}

	@Test
	public void databaseConnectionAndSimpleQuery() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/count",
				String.class)).contains("4");
	}
}
