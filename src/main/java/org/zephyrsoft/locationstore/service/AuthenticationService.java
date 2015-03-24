package org.zephyrsoft.locationstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zephyrsoft.locationstore.dao.TokenMapper;
import org.zephyrsoft.locationstore.model.Token;

@Service
public class AuthenticationService {
	
	private final TokenMapper tokenMapper;
	
	@Autowired
	public AuthenticationService(TokenMapper tokenMapper) {
		this.tokenMapper = tokenMapper;
	}
	
	public boolean isAuthorized(final String username, final String token) {
		Token fromDB = tokenMapper.readSingle(username, token);
		return fromDB != null;
	}
	
}
