package org.zephyrsoft.locationstore.ui;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zephyrsoft.locationstore.model.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

/**
 * This interprets the {@link Secured} annotation on pages.
 */
public final class ViewSecurity implements ViewChangeListener {
	
	private static final long serialVersionUID = 1909324858936424320L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ViewSecurity.class);
	
	public static boolean isAccessGranted(Class<? extends View> viewClass) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Secured secured = viewClass.getAnnotation(Secured.class);
		return secured == null || !Collections.disjoint(Arrays.asList(secured.value()), user.getRoles());
	}
	
	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		boolean accessGranted = isAccessGranted(event.getNewView().getClass());
		if (!accessGranted) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOG.info("user {} tried to access page \"{}\" with insufficient rights",
				user.getUsername(), event.getViewName());
		}
		return accessGranted;
	}
	
	@Override
	public void afterViewChange(ViewChangeEvent event) {
		// nothing to do
	}
	
}
