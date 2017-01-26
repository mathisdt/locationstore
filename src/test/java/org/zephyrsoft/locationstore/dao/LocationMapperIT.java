package org.zephyrsoft.locationstore.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zephyrsoft.locationstore.Application;
import org.zephyrsoft.locationstore.model.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LocationMapperIT {
	
	@Autowired
	private LocationMapper locationMapper;
	
	@Test
	public void read() {
		List<Location> locationsTest1 = locationMapper.read("test1");
		assertEquals(3, locationsTest1.size());
	}
	
}
