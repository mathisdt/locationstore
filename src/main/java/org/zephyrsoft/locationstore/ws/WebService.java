package org.zephyrsoft.locationstore.ws;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zephyrsoft.locationstore.dao.LocationMapper;
import org.zephyrsoft.locationstore.model.Location;
import org.zephyrsoft.locationstore.service.AuthenticationService;

/**
 * No {@code @RequestMapping(produces = "...")} because it breaks some tests!
 */
@RestController
@RequestMapping("/ws")
public class WebService {
	
	private final AuthenticationService authenticationService;
	private final LocationMapper locationMapper;
	
	@Autowired
	public WebService(AuthenticationService authenticationService, LocationMapper locationMapper) {
		this.authenticationService = authenticationService;
		this.locationMapper = locationMapper;
	}
	
	@RequestMapping(value = "",
		method = RequestMethod.GET)
	public String info(HttpServletRequest request) {
		String basePath = request.getRequestURL().toString().replaceAll("/ws/?$", "");
		return "<strong>This is the web service</strong> - perhaps you want to <a href=\"" +
			basePath + "\">go to web application</a>?";
	}
	
	@RequestMapping(value = "/{username}/{token}/locations",
		method = RequestMethod.GET)
	public List<Location> getUser(@PathVariable("username") final String username,
		@PathVariable("token") final String token) {
		
		if (authenticationService.isAuthorized(username, token)) {
			return locationMapper.read(username);
		} else {
			throw new InvalidAuthenticationException();
		}
	}
	
	@RequestMapping(value = "/{username}/{token}/add-location/{latitude:[\\d\\.]+}/{longitude:[\\d\\.]+}",
		method = RequestMethod.GET)
	public String addLocation(@PathVariable("username") final String username,
		@PathVariable("token") final String token, @PathVariable("latitude") final BigDecimal latitude,
		@PathVariable("longitude") final BigDecimal longitude) {
		
		if (authenticationService.isAuthorized(username, token)) {
			locationMapper.insert(username, new Location(latitude, longitude));
			return "OK";
		} else {
			throw new InvalidAuthenticationException();
		}
	}
}
