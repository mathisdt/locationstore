package org.zephyrsoft.locationstore.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.zephyrsoft.locationstore.Application;
import org.zephyrsoft.locationstore.dao.LocationMapper;
import org.zephyrsoft.locationstore.model.Location;
import org.zephyrsoft.locationstore.util.DateTimeUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({ "server.port=0", "management.port=0" })
public class WebServiceIT {
	
	@Value("${local.server.port}")
	private int serverPort;
	@Autowired
	private LocationMapper locationMapper;
	
	private RestTemplate restClient = new TestRestTemplate();
	
	@Test
	public void infoMessage() {
		String message = restClient.getForEntity("http://localhost:" + serverPort + "/ws/", String.class).getBody();
		assertNotNull(message);
		assertTrue(message.contains("go to web application"));
	}
	
	@Test
	public void readLocations() {
		List<Map<String, Object>> locationsTest1Token1 = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test1/TEST1-TOKEN1/locations", List.class).getBody();
		List<Map<String, Object>> locationsTest1Token2 = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test1/TEST1-TOKEN2/locations", List.class).getBody();
		assertNotNull(locationsTest1Token1);
		assertNotNull(locationsTest1Token2);
		assertEquals(3, locationsTest1Token1.size());
		assertThat(locationsTest1Token1, IsIterableContainingInOrder.contains(locationsTest1Token2.toArray()));
		
		List<Map<String, Object>> locationsTest2Token1 = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test2/TEST2-TOKEN1/locations", List.class).getBody();
		assertNotNull(locationsTest2Token1);
		assertEquals(4, locationsTest2Token1.size());
		
		assertEquals(40.49, locationsTest2Token1.get(0).get("latitude"));
		assertEquals(1.123, locationsTest2Token1.get(0).get("longitude"));
		assertTrue(DateTimeUtil.fromString((String) locationsTest2Token1.get(0).get("instant"))
			.isBefore(LocalDateTime.now().minus(10, ChronoUnit.DAYS)));
	}
	
	@Test
	public void addLocation() {
		List<Map<String, Object>> locationsTest1Token1 = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test1/TEST1-TOKEN1/locations", List.class).getBody();
		assertNotNull(locationsTest1Token1);
		assertEquals(3, locationsTest1Token1.size());
		
		String returnValue = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test1/TEST1-TOKEN1/add-location/99.123/88.234", String.class)
			.getBody();
		
		assertEquals("OK", returnValue);
		
		locationsTest1Token1 = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test1/TEST1-TOKEN1/locations", List.class).getBody();
		assertNotNull(locationsTest1Token1);
		assertEquals(4, locationsTest1Token1.size());
		
		// cleanup
		List<Location> list = locationMapper.read("test1");
		Location locationToRemove = list.get(list.size() - 1);
		locationMapper.delete(locationToRemove);
	}
	
	@Test
	public void unauthorizedAccess() throws Exception {
		HttpStatus status = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test1/TEST1-TOKEN-INVALID/locations", String.class).getStatusCode();
		assertEquals(HttpStatus.UNAUTHORIZED, status);
		
		status = restClient.getForEntity(
			"http://localhost:" + serverPort + "/ws/test1/TEST1-TOKEN-INVALID/add-location/1.0/2.0", String.class)
			.getStatusCode();
		assertEquals(HttpStatus.UNAUTHORIZED, status);
	}
}
