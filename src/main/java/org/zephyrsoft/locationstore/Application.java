package org.zephyrsoft.locationstore;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zephyrsoft.locationstore.dao.MapperInterface;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "org.zephyrsoft.locationstore.dao", annotationClass = MapperInterface.class)
public class Application {
	
	@Value("${db.driver}")
	private String dbDriver;
	@Value("${db.url}")
	private String dbUrl;
	@Value("${db.user}")
	private String dbUser;
	@Value("${db.password}")
	private String dbPassword;
	
	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(dbDriver);
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUser(dbUser);
		dataSource.setPassword(dbPassword);
		dataSource.setInitialPoolSize(1);
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(4);
		dataSource.setTestConnectionOnCheckin(false);
		dataSource.setMaxIdleTime(300);
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws PropertyVetoException {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		try {
			return sessionFactory.getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
