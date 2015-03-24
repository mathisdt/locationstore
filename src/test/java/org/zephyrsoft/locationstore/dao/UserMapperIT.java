package org.zephyrsoft.locationstore.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zephyrsoft.locationstore.Application;
import org.zephyrsoft.locationstore.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserMapperIT {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void read() {
		User userTest1 = userMapper.read("test1");
		assertEquals("test1pw", userTest1.getPassword());
	}
	
}
