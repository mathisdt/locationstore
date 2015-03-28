package org.zephyrsoft.locationstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zephyrsoft.locationstore.dao.TokenMapper;
import org.zephyrsoft.locationstore.dao.UserMapper;
import org.zephyrsoft.locationstore.model.Token;
import org.zephyrsoft.locationstore.model.User;

@Service
public class AuthenticationService implements UserDetailsService {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);
	
	private final TokenMapper tokenMapper;
	private final UserMapper userMapper;
	
	@Autowired
	public AuthenticationService(TokenMapper tokenMapper, UserMapper userMapper) {
		this.tokenMapper = tokenMapper;
		this.userMapper = userMapper;
	}
	
	public boolean isAuthorized(final String username, final String token) {
		Token fromDB = tokenMapper.readSingle(username, token);
		return fromDB != null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.read(username);
		if (user == null) {
			throw new UsernameNotFoundException("username not found");
		} else {
			LOG.debug("authenticated {} (admin={})", user.getUsername(), user.isAdmin());
			return user;
		}
	}
	
}
