package org.zephyrsoft.locationstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.zephyrsoft.locationstore.service.AuthenticationService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${vaadin.servlet.urlMapping}")
	private String vaadinBaseMapping;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String vaadinBaseUrl = vaadinBaseMapping.replaceAll("/?+\\*$", "");
		String vaadinLoginUrl = vaadinBaseUrl + "/login";
		
		http.authorizeRequests()
			.antMatchers("/ws/**", "/VAADIN/**", vaadinBaseUrl + "/PUSH/**", vaadinBaseUrl + "/UIDL/**", vaadinLoginUrl)
			.permitAll()
			.antMatchers("/**").fullyAuthenticated()
			.and()
			.userDetailsService(userDetailsService())
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(vaadinLoginUrl));
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
