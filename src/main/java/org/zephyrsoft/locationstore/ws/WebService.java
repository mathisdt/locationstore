package org.zephyrsoft.locationstore.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.zephyrsoft.locationstore.dao.LocationMapper;
import org.zephyrsoft.locationstore.model.Location;

@Path("/ws")
public class WebService {
	
	private final LocationMapper locationMapper;
	
	@Autowired
	public WebService(LocationMapper locationMapper) {
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
	@Path(value = "/{username}/locations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getUser(@PathParam("username") final String username) {
		return locationMapper.read(username);
	}
	
	@POST
	@Path(value = "/{username}/add-location")
	@Produces(MediaType.TEXT_PLAIN)
	public String addLocation(@PathParam("username") final String username) {
		// TODO
		// locationMapper.insert(username, new Location(latitude, longitude));
		return "OK";
	}
}
