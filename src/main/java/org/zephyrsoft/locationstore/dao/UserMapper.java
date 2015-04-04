package org.zephyrsoft.locationstore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.zephyrsoft.locationstore.model.User;

@MapperInterface
public interface UserMapper {
	
	@Select({
		"select * from user",
		"where username=lower(#{username})"
	})
	User read(@Param("username") String username);
	
	@Select({
		"select * from user",
		"order by username"
	})
	List<User> readAll();
	
	@Insert({
		"insert into user (fullname, username, password, admin)",
		"values (#{user.fullname}, lower(#{user.username}), #{user.password}, #{user.admin})"
	})
	@Options(useGeneratedKeys = true, keyProperty = "user.id")
	void insert(@Param("user") User user);
	
	@Update({
		"update user",
		"set fullname=#{user.fullname},",
		"username=lower(#{user.username}),",
		"password=#{user.password},",
		"admin=#{user.admin}",
		"where id=#{user.id}"
	})
	void update(@Param("user") User user);
	
	@Delete({
		"delete from user",
		"where id=#{user.id}"
	})
	void delete(@Param("user") User user);
	
}
