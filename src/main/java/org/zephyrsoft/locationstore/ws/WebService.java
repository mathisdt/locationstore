package org.zephyrsoft.locationstore.ws;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.zephyrsoft.locationstore.dao.LocationMapper;
import org.zephyrsoft.locationstore.model.Location;
import org.zephyrsoft.locationstore.service.AuthenticationService;

@Path("/ws")
public class WebService {
	
	private final AuthenticationService authenticationService;
	private final LocationMapper locationMapper;
	
	@Autowired
	public WebService(AuthenticationService authenticationService, LocationMapper locationMapper) {
		this.authenticationService = authenticationService;
		this.locationMapper = locationMapper;
	}
	
	@GET
	@Path("")
	@Produces(MediaType.TEXT_HTML)
	public String info(@Context HttpServletRequest request) {
		String basePath = request.getRequestURL().toString().replaceAll("/ws/?$", "");
		return "<strong>This is the web service</strong> - perhaps you want to <a href=\"" +
			basePath + "\">go to web application</a>?";
	}
	
	@GET
	@Path(value = "/{username}/{token}/locations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getUser(@PathParam("username") final String username,
		@PathParam("token") final String token) {
		
		if (authenticationService.isAuthorized(username, token)) {
			return locationMapper.read(username);
		} else {
			throw new InvalidAuthenticationException();
		}
	}
	
	@GET
	@Path(value = "/{username}/{token}/add-location/{latitude:[\\d\\.]+}/{longitude:[\\d\\.]+}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addLocation(@PathParam("username") final String username,
		@PathParam("token") final String token, @PathParam("latitude") final BigDecimal latitude,
		@PathParam("longitude") final BigDecimal longitude) {
		
		if (authenticationService.isAuthorized(username, token)) {
			locationMapper.insert(username, new Location(latitude, longitude));
			return "OK";
		} else {
			throw new InvalidAuthenticationException();
		}
	}
}
