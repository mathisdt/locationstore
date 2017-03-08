package org.zephyrsoft.locationstore.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Handles user login requests.
 */
@Path("/login")
public class LoginController {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	private AuthenticationProvider authenticationProvider;
	
	public LoginController(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}
	
	@GET
	@Path("")
	public Response get(@Context HttpServletRequest request) throws IOException {
		LOG.debug("output login page as HTML");
		InputStream htmlStream = getClass().getResourceAsStream("/login/login.html");
		String html = IOUtils.toString(htmlStream, StandardCharsets.UTF_8);
		if (request.getParameterMap().keySet().contains("error")) {
			html = html.replaceAll("<!-- message -->", "username / password not correct");
		} else if (request.getParameterMap().keySet().contains("logout")) {
			html = html.replaceAll("<!-- message -->", "successfully logged out");
		}
		return Response.ok()
			.encoding(StandardCharsets.UTF_8.displayName())
			.type(MediaType.TEXT_HTML)
			.entity(html)
			.build();
	}
	
	@POST
	@Path("")
	public Response post(@Context HttpServletRequest request) throws URISyntaxException {
		String username = getParam(request, "username");
		String password = getParam(request, "password");
		LOG.debug("trying to authenticate user {}", username);
		
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, DigestUtils
				.sha256Hex(password));
			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authentication = authenticationProvider.authenticate(token);
			LOG.debug("authenticating user {}", authentication.getPrincipal());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return Response.seeOther(new URI("/ui/")).build();
		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			LOG.error("failure while authenticating user", e);
			return Response.seeOther(new URI("/login?error")).build();
		}
	}
	
	private String getParam(HttpServletRequest request, String name) {
		return request == null
			|| request.getParameterMap() == null
			|| request.getParameterMap().get(name) == null
			|| request.getParameterMap().get(name).length == 0
				? null
				: request.getParameterMap().get(name)[0];
	}
	
}
