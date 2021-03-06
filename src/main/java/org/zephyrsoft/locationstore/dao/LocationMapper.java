package org.zephyrsoft.locationstore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.zephyrsoft.locationstore.model.Location;

@MapperInterface
public interface LocationMapper {
	
	@Select({
		"select * from location",
		"where username=lower(#{username})",
		"order by instant"
	})
	@Results({
		@Result(column = "instant",
			property = "instant",
			jdbcType = JdbcType.TIMESTAMP)
	})
	List<Location> read(@Param("username") String username);
	
	@Insert({
		"insert into location (username, instant, latitude, longitude) values (",
		"lower(#{username}),",
		"#{location.instant,javaType=java.time.LocalDateTime,jdbcType=TIMESTAMP},",
		"#{location.latitude},",
		"#{location.longitude}",
		")"
	})
	@Options(useGeneratedKeys = true, keyProperty = "location.id")
	void insert(@Param("username") String username, @Param("location") Location location);
	
	@Delete({
		"delete from location",
		"where username=lower(#{username})"
	})
	void deleteForUser(@Param("username") String username);
	
	@Delete({
		"delete from location",
		"where id=#{location.id}"
	})
	void delete(@Param("location") Location location);
	
}
