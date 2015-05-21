package org.zephyrsoft.locationstore;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zephyrsoft.locationstore.dao.MapperInterface;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.boot.annotation.EnableVaadinServlet;
import com.vaadin.spring.server.SpringVaadinServlet;

@SpringBootApplication
@EnableTransactionManagement
@EnableVaadinServlet
@MapperScan(basePackages = "org.zephyrsoft.locationstore.dao", annotationClass = MapperInterface.class)
public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	
	@Value("${db.driver}")
	private String dbDriver;
	@Value("${db.url}")
	private String dbUrl;
	@Value("${db.user}")
	private String dbUser;
	@Value("${db.password}")
	private String dbPassword;
	
	@Bean
	public ServletRegistrationBean vaadinServletRegistration() {
		LOG.info("Registering Vaadin servlet");
		
		final String[] urlMappings = new String[] { "/ui/*", "/VAADIN/*", "/UIDL/*", "/PUSH/*" };
		LOG.info("Servlet will be mapped to URLs {}", (Object) urlMappings);
		
		final SpringVaadinServlet servlet = vaadinServlet();
		servlet.setServiceUrlPath("/ui"); // since vaadin-spring-boot 1.0.0.beta3
		
		final ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, urlMappings);
		registrationBean.addInitParameter(VaadinServlet.SERVLET_PARAMETER_PRODUCTION_MODE, Boolean.TRUE.toString());
		
		return registrationBean;
	}
	
	@Bean
	public SpringVaadinServlet vaadinServlet() {
		return new SpringVaadinServlet();
	}
	
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
