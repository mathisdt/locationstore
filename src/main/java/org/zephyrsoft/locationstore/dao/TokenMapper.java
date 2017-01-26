package org.zephyrsoft.locationstore.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zephyrsoft.locationstore.model.Token;

@MapperInterface
public interface TokenMapper {
	
	@Select({
		"select * from token",
		"where username=#{username}"
	})
	Set<Token> read(@Param("username") String username);
	
	@Select({
		"select count(*) from token",
		"where token=#{token}"
	})
	boolean alreadyTaken(@Param("token") String token);
	
	@Select({
		"select count(*) from token",
		"where username=#{username}"
	})
	Long count(@Param("username") String username);
	
	@Select({
		"select * from token",
		"where username=#{username} and token=#{token}"
	})
	Token readSingle(@Param("username") String username, @Param("token") String token);
	
	@Insert({
		"insert into token (username, token)",
		"values (#{username}, #{token.token})"
	})
	@Options(useGeneratedKeys = true, keyProperty = "token.id")
	void insertForUser(@Param("username") String username, @Param("token") Token token);
	
	@Delete({
		"delete from token",
		"where username=lower(#{username})"
	})
	void deleteForUser(@Param("username") String username);
	
	@Delete({
		"delete from token",
		"where id=#{token.id}"
	})
	void delete(@Param("token") Token token);
	
}
