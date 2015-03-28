package org.zephyrsoft.locationstore.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zephyrsoft.locationstore.ui.Roles;

public class User implements UserDetails {
	
	private static final long serialVersionUID = 4099609658931426256L;
	
	public static final class UserProperties {
		// normal properties
		public static final String ID = "id";
		public static final String FULLNAME = "fullname";
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
		public static final String ADMIN = "admin";
		// generated properties
		public static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
		public static final String ACCOUNT_NON_LOCKED = "accountNonLocked";
		public static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
		public static final String ENABLED = "enabled";
		public static final String AUTHORITIES = "authorities";
		public static final String ROLES = "roles";
	}
	
	private Long id;
	private String fullname;
	private String username;
	private String password;
	private boolean admin;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (isAdmin()) {
			return Arrays.asList(new SimpleGrantedAuthority(Roles.USER), new SimpleGrantedAuthority(Roles.ADMIN));
		} else {
			return Arrays.asList(new SimpleGrantedAuthority(Roles.USER));
		}
	}
	
	public Collection<String> getRoles() {
		if (isAdmin()) {
			return Arrays.asList(Roles.USER, Roles.ADMIN);
		} else {
			return Arrays.asList(Roles.USER);
		}
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
