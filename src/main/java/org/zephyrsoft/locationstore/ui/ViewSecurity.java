package org.zephyrsoft.locationstore.ui;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zephyrsoft.locationstore.WebSecurityConfig;
import org.zephyrsoft.locationstore.model.User;

import com.vaadin.navigator.View;
import com.vaadin.spring.access.ViewInstanceAccessControl;
import com.vaadin.ui.UI;

/**
 * This is used by Spring Security automatically because it is instantiated as a bean in {@link WebSecurityConfig}.
 */
public final class ViewSecurity implements ViewInstanceAccessControl {
	
	private static final Logger LOG = LoggerFactory.getLogger(ViewSecurity.class);
	
	@Override
	public boolean isAccessGranted(UI ui, String beanName, View view) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Class<? extends View> viewClass = view.getClass();
		boolean granted = isAccessGranted(viewClass);
		if (granted) {
			LOG.info("user {} is granted access to {}", user.getUsername(), viewClass.getCanonicalName());
			return true;
		} else {
			LOG.info("user {} is not allowed to access {}", user.getUsername(), viewClass.getCanonicalName());
			return false;
		}
	}
	
	public static boolean isAccessGranted(Class<? extends View> viewClass) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Secured secured = viewClass.getAnnotation(Secured.class);
		return secured == null || !Collections.disjoint(Arrays.asList(secured.value()), user.getRoles());
	}
	
}
