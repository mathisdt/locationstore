package org.zephyrsoft.locationstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zephyrsoft.locationstore.Application;
import org.zephyrsoft.locationstore.model.Token;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TokenMapperIT {
	
	@Autowired
	private TokenMapper tokenMapper;
	
	@Test
	public void read() {
		Set<Token> tokensTest1 = tokenMapper.read("test1");
		assertEquals(2, tokensTest1.size());
	}
	
	@Test
	public void alreadyTaken() {
		assertTrue(tokenMapper.alreadyTaken("TEST1-TOKEN1"));
		assertFalse(tokenMapper.alreadyTaken("TEST1-TOKEN999"));
	}
	
}
