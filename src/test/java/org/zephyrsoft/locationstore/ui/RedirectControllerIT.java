package org.zephyrsoft.locationstore.ui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.zephyrsoft.locationstore.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({ "server.port=0", "management.port=0" })
public class RedirectControllerIT {
	
	@Value("${local.server.port}")
	private int serverPort;
	
	private RestTemplate template = new TestRestTemplate();
	
	@Test
	public void redirect() {
		HttpHeaders headers = template.getForEntity("http://localhost:" + serverPort + "/", String.class).getHeaders();
		assertNotNull(headers);
		assertNotNull(headers.getLocation());
		assertTrue(headers.getLocation().toString().startsWith("http://localhost:" + serverPort + "/ui"));
	}
	
}
