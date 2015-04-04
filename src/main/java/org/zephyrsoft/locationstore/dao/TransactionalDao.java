package org.zephyrsoft.locationstore.dao;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zephyrsoft.locationstore.model.Token;
import org.zephyrsoft.locationstore.model.User;

import com.google.common.base.Preconditions;

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
	
	public void deleteUserCompletely(User user) {
		Preconditions.checkArgument(user != null);
		Preconditions.checkArgument(user.getUsername() != null);
		Preconditions.checkArgument(user.getId() != null);
		
		locationMapper.deleteForUser(user.getUsername());
		tokenMapper.deleteForUser(user.getUsername());
		userMapper.delete(user);
	}
	
	public void saveUserWithTokens(User user, Set<Token> tokens) {
		Preconditions.checkArgument(user != null);
		Preconditions.checkArgument(user.getUsername() != null);
		Preconditions.checkArgument(tokens != null);
		
		if (user.getId() == null) {
			userMapper.insert(user);
		} else {
			userMapper.update(user);
		}
		
		// TODO very simple approach: delete all and then add (probably re-add) the remaining
		tokenMapper.deleteForUser(user.getUsername());
		for (Token token : tokens) {
			tokenMapper.insertForUser(user.getUsername(), token);
		}
	}
}
