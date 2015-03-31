package org.zephyrsoft.locationstore.ui;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zephyrsoft.locationstore.model.User;

import com.vaadin.navigator.View;

/**
 * This interprets the {@link Secured} annotation on pages manually
 * (opposed to Spring Security which interprets it automatically in the background)
 * so controls can be disabled if a specific page is not available to a user.
 */
public final class ViewSecurity {
	
	public static boolean isAccessGranted(Class<? extends View> viewClass) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Secured secured = viewClass.getAnnotation(Secured.class);
		return secured == null || !Collections.disjoint(Arrays.asList(secured.value()), user.getRoles());
	}
	
}
