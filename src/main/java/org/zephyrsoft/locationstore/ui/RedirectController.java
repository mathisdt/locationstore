package org.zephyrsoft.locationstore.ui;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;

/**
 * Redirects to /ui
 */
@Path("/")
public class RedirectController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedirectController.class);
	
	@GET
	@Path("")
	public Response index(@Context HttpServletRequest request) throws URISyntaxException {
		String urlParams = getUrlParams(request);
		String redirectUrl = "/ui" + (StringUtils.isEmpty(urlParams) ? "" : "?" + urlParams);
		
		LOG.debug("redirecting to " + redirectUrl);
		return Response.temporaryRedirect(new URI(redirectUrl)).build();
	}
	
	private static String getUrlParams(HttpServletRequest request) {
		List<String> params = new LinkedList<>();
		for (String key : request.getParameterMap().keySet()) {
			for (String value : request.getParameterMap().get(key)) {
				params.add(key + "=" + value);
			}
		}
		return Joiner.on("&").join(params);
	}
	
}
