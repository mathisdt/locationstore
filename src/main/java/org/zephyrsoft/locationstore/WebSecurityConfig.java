package org.zephyrsoft.locationstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zephyrsoft.locationstore.service.AuthenticationService;
import org.zephyrsoft.locationstore.ui.ViewSecurity;

import com.vaadin.navigator.ViewChangeListener;

/**
 * Note that {@code @}{@link EnableGlobalMethodSecurity} is NOT present here!
 * 
 * {@code @}{@link Secured} on Vaadin views is interpreted by {@link ViewSecurity} as {@link ViewChangeListener}.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/ui/login", "/VAADIN/**", "/ui/UIDL/**").permitAll()
			.anyRequest().fullyAuthenticated()
			.and()
			.exceptionHandling()
			.defaultAuthenticationEntryPointFor(basicEntryPoint(), new AntPathRequestMatcher("/ws/**"))
			.defaultAuthenticationEntryPointFor(formEntryPoint(), new AntPathRequestMatcher("/**"))
			.and()
			.userDetailsService(userDetailsService())
			.csrf().disable()
			.headers().frameOptions().disable();
	}
	
	@Bean
	public LoginUrlAuthenticationEntryPoint formEntryPoint() {
		return new LoginUrlAuthenticationEntryPoint("/ui/login");
	}
	
	@Bean
	public BasicAuthenticationEntryPoint basicEntryPoint() {
		BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
		basicAuthenticationEntryPoint.setRealmName("Location Store - Web Service");
		return basicAuthenticationEntryPoint;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(authenticationService);
		return authenticationProvider;
	}
	
}
