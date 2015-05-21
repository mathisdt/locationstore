package org.zephyrsoft.locationstore.ui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;

/**
 * Redirects from / to /ui
 */
@Path("/")
public class RedirectController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedirectController.class);
	
	@GET
	@Path("")
	public String index(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		List<String> params = new LinkedList<>();
		for (String key : request.getParameterMap().keySet()) {
			for (String value : request.getParameterMap().get(key)) {
				params.add(key + "=" + value);
			}
		}
		String queryString = Joiner.on("&").join(params);
		String redirectUrl = "/ui" + (StringUtils.isEmpty(queryString) ? "" : "?" + queryString);
		
		LOG.debug("redirecting to " + redirectUrl);
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			LOG.error("error while redirecting to " + redirectUrl, e);
		}
		return "";
	}
	
}
