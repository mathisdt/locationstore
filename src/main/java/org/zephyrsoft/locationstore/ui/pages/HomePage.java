package org.zephyrsoft.locationstore.ui.pages;

import org.zephyrsoft.locationstore.ui.Pages;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class HomePage extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 146768140068343010L;
	
	public HomePage(Navigator navigator) {
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		
		Button button = new Button("Go to Admin Page", event -> {
			navigator.navigateTo(Pages.ADMINISTRATION.getNavigationTarget());
		});
		addComponent(button);
		setComponentAlignment(button, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO check permissions
	}
}
