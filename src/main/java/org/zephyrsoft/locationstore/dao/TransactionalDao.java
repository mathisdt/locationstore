package org.zephyrsoft.locationstore.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class TransactionalDao {
	
	private final UserMapper userMapper;
	private final TokenMapper tokenMapper;
	private final LocationMapper locationMapper;
	
	@Autowired
	public TransactionalDao(UserMapper userMapper, TokenMapper tokenMapper, LocationMapper locationMapper) {
		this.userMapper = userMapper;
		this.tokenMapper = tokenMapper;
		this.locationMapper = locationMapper;
	}
	
	// TODO add methods that need transactional behaviour
	
}
