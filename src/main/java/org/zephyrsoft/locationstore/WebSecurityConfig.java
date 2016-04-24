package org.zephyrsoft.locationstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
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
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/ws/**", "/ui/login", "/VAADIN/**", "/ui/UIDL/**")
			.permitAll()
			.antMatchers("/**").fullyAuthenticated()
			.and()
			.userDetailsService(userDetailsService())
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/ui/login"));
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		return authenticationProvider;
	}
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return userDetailsService();
	}
	
	@Override
	protected UserDetailsService userDetailsService() {
		return authenticationService;
	}
}
