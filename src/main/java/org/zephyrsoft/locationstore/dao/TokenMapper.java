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
		"select * from token",
		"where username=#{username} and token=#{token}"
	})
	Token readSingle(@Param("username") String username, @Param("token") String token);

	@Insert({
		"insert into token (username, token)",
		"values (#{token.username}, #{token.token})"
	})
	@Options(useGeneratedKeys = true, keyProperty = "token.id")
	void insert(@Param("token") Token token);

	@Delete({
		"delete from token",
		"where id=#{token.id}"
	})
	void delete(@Param("token") Token token);

}
