package org.zephyrsoft.locationstore.ui;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Joiner;

/**
 * Redirects from / to /ui
 */
@Controller
public class RedirectController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedirectController.class);
	
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(HttpServletRequest request) {
		List<String> params = new LinkedList<>();
		for (String key : request.getParameterMap().keySet()) {
			for (String value : request.getParameterMap().get(key)) {
				params.add(key + "=" + value);
			}
		}
		String queryString = Joiner.on("&").join(params);
		LOG.debug("redirecting to /ui"
			+ (StringUtils.isEmpty(queryString) ? "" : "?" + queryString));
		return "redirect:/ui"
			+ (StringUtils.isEmpty(queryString) ? "" : "?" + queryString);
	}
	
}
