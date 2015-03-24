package org.zephyrsoft.locationstore.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirects from / to /ui
 */
@Controller
public class RedirectController {

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/ui";
	}

}