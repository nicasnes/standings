package tech.asnes.standings;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

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