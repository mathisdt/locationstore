package org.zephyrsoft.locationstore.ui;

import org.zephyrsoft.locationstore.ui.pages.AdminPage;
import org.zephyrsoft.locationstore.ui.pages.HomePage;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@Title("Location Store")
@Theme("valo")
@SpringUI
public class VaadinUI extends UI {
	
	private static final long serialVersionUID = 2105911238435641317L;
	
	private Navigator navigator;
	
	@Override
	protected void init(final VaadinRequest request) {
		navigator = new Navigator(this, this);
		
		// create and register the sub-pages
		navigator.addView(Pages.HOME.getNavigationTarget(), new HomePage(navigator));
		navigator.addView(Pages.ADMINISTRATION.getNavigationTarget(), new AdminPage(navigator));
	}
	
}
