package org.zephyrsoft.locationstore.ws;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;
import org.zephyrsoft.locationstore.ui.LoginController;
import org.zephyrsoft.locationstore.ui.RedirectController;

@Component
public class WebServiceConfig extends ResourceConfig {
	
	@Autowired
	public WebServiceConfig(AuthenticationProvider authenticationProvider) {
		register(WebService.class);
		register(RedirectController.class);
		register(new LoginController(authenticationProvider));
	}
	
}
