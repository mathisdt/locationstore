package org.zephyrsoft.locationstore.model;

public class Token {
	
	public static final class TokenProperties {
		public static final String ID = "id";
		public static final String TOKEN = "token";
	}
	
	private Long id;
	private String token;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
