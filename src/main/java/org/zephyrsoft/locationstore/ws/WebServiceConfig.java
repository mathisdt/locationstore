package org.zephyrsoft.locationstore.ws;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import org.zephyrsoft.locationstore.ui.RedirectController;

@Component
public class WebServiceConfig extends ResourceConfig {
	
	public WebServiceConfig() {
		register(WebService.class);
		register(RedirectController.class);
	}
	
}
