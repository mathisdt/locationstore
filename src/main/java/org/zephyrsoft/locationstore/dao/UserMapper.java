package org.zephyrsoft.locationstore.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zephyrsoft.locationstore.model.User;

public interface UserMapper {
	
	@Select({
		"select from user",
		"where username=#{username}" })
	User read(@Param("username") String username);
	
}
