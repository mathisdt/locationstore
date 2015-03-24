package org.zephyrsoft.locationstore.ws;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidAuthenticationException extends RuntimeException {
	
	private static final long serialVersionUID = -4520950997046460132L;
	
	public InvalidAuthenticationException() {
		super("invalid username or token");
	}
	
}
